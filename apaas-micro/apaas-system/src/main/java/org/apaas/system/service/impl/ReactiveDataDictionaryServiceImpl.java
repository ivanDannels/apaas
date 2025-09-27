/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import org.apaas.system.repository.DataDictionaryRepository;
import org.apaas.system.service.ReactiveDataDictionaryService;
import org.apaas.core.event.DataDictionaryEvent;
import org.apaas.core.event.EventPublisherService;
import org.apaas.core.exception.LockAcquisitionException;
import org.apaas.core.log.LogUtil;
import org.apaas.core.lock.DistributedLockService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 响应式数据字典服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, Long, DataDictionaryRepository> implements ReactiveDataDictionaryService {
    
    private final DistributedLockService distributedLockService;
    private final EventPublisherService eventPublisherService;
    
    public ReactiveDataDictionaryServiceImpl(DataDictionaryRepository repository, DistributedLockService distributedLockService, EventPublisherService eventPublisherService) {
        super(repository);
        this.distributedLockService = distributedLockService;
        this.eventPublisherService = eventPublisherService;
    }
    
    @Override
    public Flux<DataDictionary> selectPage(Pageable pageable, DataDictionaryDTO query) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "分页查询数据字典: name={}, type={}, status={}", query.getName(), query.getType(), query.getStatus());
        // 根据查询条件构建查询
        return repository.findByNameContainingAndTypeAndStatus(query.getName() != null ? query.getName() : "", query.getType(), query.getStatus(), pageable);
    }
    
    @Override
    public Mono<Boolean> create(DataDictionary dataDictionary) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "创建数据字典: name={}", dataDictionary.getName());
        String lockKey = "dataDictionary:create";
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                dataDictionary.setCreatedTime(LocalDateTime.now());
                dataDictionary.setUpdatedTime(LocalDateTime.now());
                return repository.save(dataDictionary).flatMap(savedDataDictionary -> {
                    LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "数据字典创建成功: id={}, name={}", savedDataDictionary.getId(), savedDataDictionary.getName());
                    // 发布数据字典创建事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_CREATED", savedDataDictionary.getId(), savedDataDictionary.getName(), "CREATE", "数据字典创建成功");
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "数据字典创建失败: name={}, error={}", dataDictionary.getName(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#dataDictionary.id")
    public Mono<Boolean> update(DataDictionary dataDictionary) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "更新数据字典: id={}, name={}", dataDictionary.getId(), dataDictionary.getName());
        String lockKey = "dataDictionary:update:" + dataDictionary.getId();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return repository.findById(dataDictionary.getId()).flatMap(existing -> {
                    existing.setName(dataDictionary.getName());
                    existing.setType(dataDictionary.getType());
                    existing.setStatus(dataDictionary.getStatus());
                    existing.setDescription(dataDictionary.getDescription());
                    existing.setUpdatedTime(LocalDateTime.now());
                    return repository.save(existing);
                }).flatMap(updatedDataDictionary -> {
                    LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "数据字典更新成功: id={}, name={}", updatedDataDictionary.getId(), updatedDataDictionary.getName());
                    // 发布数据字典更新事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_UPDATED", updatedDataDictionary.getId(), updatedDataDictionary.getName(), "UPDATE", "数据字典更新成功");
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "数据字典更新失败: id={}, error={}", dataDictionary.getId(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#id")
    public Mono<Boolean> delete(Long id) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "删除数据字典: id={}", id);
        String lockKey = "dataDictionary:delete:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return repository.findById(id).flatMap(dataDictionary -> {
                    return repository.deleteById(id).then(Mono.just(dataDictionary));
                }).flatMap(deletedDataDictionary -> {
                    LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "数据字典删除成功: id={}, name={}", deletedDataDictionary.getId(), deletedDataDictionary.getName());
                    // 发布数据字典删除事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_DELETED", deletedDataDictionary.getId(), deletedDataDictionary.getName(), "DELETE", "数据字典删除成功");
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "数据字典删除失败: id={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#id")
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "修改数据字典状态: id={}, status={}", id, status);
        String lockKey = "dataDictionary:changeStatus:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return repository.findById(id).flatMap(dataDictionary -> {
                    dataDictionary.setStatus(status);
                    dataDictionary.setUpdatedTime(LocalDateTime.now());
                    return repository.save(dataDictionary);
                }).flatMap(updatedDataDictionary -> {
                    String statusDesc = status == 0 ? "启用" : "禁用";
                    LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "数据字典状态修改成功: id={}, name={}, status={}", updatedDataDictionary.getId(), updatedDataDictionary.getName(), statusDesc);
                    // 发布数据字典状态变更事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_STATUS_CHANGED", updatedDataDictionary.getId(), updatedDataDictionary.getName(), "STATUS_CHANGE", "数据字典状态变更为" + statusDesc);
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "数据字典状态修改失败: id={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "导出数据字典Excel: name={}, type={}, status={}", query.getName(), query.getType(), query.getStatus());
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dataDictionary.xlsx");
        
        // 查询数据
        return repository.findByNameContainingAndTypeAndStatus(query.getName() != null ? query.getName() : "", query.getType(), query.getStatus()).collectList().flatMap(dataList -> {
            try {
                // 创建Excel工作簿
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("数据字典");
                
                // 创建表头
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("名称");
                headerRow.createCell(2).setCellValue("类型");
                headerRow.createCell(3).setCellValue("状态");
                headerRow.createCell(4).setCellValue("描述");
                headerRow.createCell(5).setCellValue("创建时间");
                headerRow.createCell(6).setCellValue("更新时间");
                
                // 填充数据
                for (int i = 0; i < dataList.size(); i++) {
                    DataDictionary dict = dataList.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(dict.getId());
                    row.createCell(1).setCellValue(dict.getName());
                    row.createCell(2).setCellValue(dict.getType());
                    row.createCell(3).setCellValue(dict.getStatus());
                    row.createCell(4).setCellValue(dict.getDescription());
                    row.createCell(5).setCellValue(dict.getCreatedTime().toString());
                    row.createCell(6).setCellValue(dict.getUpdatedTime().toString());
                }
                
                // 写入输出流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                workbook.close();
                
                // 返回数据
                DataBufferFactory bufferFactory = response.bufferFactory();
                DataBuffer dataBuffer = bufferFactory.wrap(outputStream.toByteArray());
                return response.writeWith(Mono.just(dataBuffer));
            } catch (IOException e) {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "导出Excel失败: error={}", e.getMessage(), e);
                return Mono.error(e);
            }
        }).then();
    }
    
    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "导入数据字典Excel");
        String lockKey = "dataDictionary:import";
        return distributedLockService.tryLock(lockKey, 3, 30, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                try {
                    Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileData));
                    Sheet sheet = workbook.getSheetAt(0);
                    
                    List<DataDictionary> dataList = new ArrayList<>();
                    
                    // 从第二行开始读取数据（第一行为表头）
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            DataDictionary dict = DataDictionary.builder().name(getCellValueAsString(row.getCell(1))).type(getCellValueAsInteger(row.getCell(2))).status(getCellValueAsInteger(row.getCell(3))).description(getCellValueAsString(row.getCell(4))).createdTime(LocalDateTime.now()).updatedTime(LocalDateTime.now()).build();
                            dataList.add(dict);
                        }
                    }
                    
                    workbook.close();
                    
                    // 批量保存数据并发布事件
                    return Flux.fromIterable(dataList).flatMap(dict -> repository.save(dict).flatMap(savedDict -> {
                        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "数据字典导入成功: id={}, name={}", savedDict.getId(), savedDict.getName());
                        // 发布数据字典创建事件
                        DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_CREATED", savedDict.getId(), savedDict.getName(), "CREATE", "数据字典导入成功");
                        return eventPublisherService.publishEvent("dataDictionary.events", event).thenReturn(savedDict);
                    })).then(distributedLockService.unlock(lockKey)).thenReturn(true).onErrorResume(throwable -> {
                        LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "数据字典导入失败: error={}", throwable.getMessage(), throwable);
                        return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                    });
                } catch (IOException e) {
                    LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "导入Excel失败: error={}", e.getMessage(), e);
                    return distributedLockService.unlock(lockKey).then(Mono.just(false));
                }
            } else {
                LogUtil.error(ReactiveDataDictionaryServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @Cacheable(value = "dataDictionaries", key = "#id")
    public Mono<DataDictionary> findById(Long id) {
        LogUtil.info(ReactiveDataDictionaryServiceImpl.class, "根据ID查询数据字典: id={}", id);
        return repository.findById(id);
    }
    
    /**
     * 获取单元格字符串值
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
    
    /**
     * 获取单元格整数值
     */
    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) {
            return 0;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
    }
}