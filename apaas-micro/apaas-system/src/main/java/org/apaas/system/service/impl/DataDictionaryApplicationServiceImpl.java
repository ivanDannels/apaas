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
import org.apaas.domain.service.application.AbstractApplicationService;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionaryAggregate;
import org.apaas.system.repository.DataDictionaryAggregateRepository;
import org.apaas.system.service.DataDictionaryApplicationService;
import org.apaas.system.service.DataDictionaryDomainService;
import org.apaas.domain.event.DataDictionaryEvent;
import org.apaas.domain.event.EventPublisherService;
import org.apaas.domain.exception.LockAcquisitionException;
import org.apaas.domain.log.LogUtil;
import org.apaas.domain.lock.DistributedLockService;
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
 * 数据字典应用服务实现
 * 处理数据字典相关的应用层逻辑，协调领域服务和基础设施层
 *
 * @author ivan
 */
@Slf4j
@Service
public class DataDictionaryApplicationServiceImpl extends AbstractApplicationService<DataDictionaryAggregate, Long, DataDictionaryAggregateRepository> 
        implements DataDictionaryApplicationService {
    
    private final DistributedLockService distributedLockService;
    private final EventPublisherService eventPublisherService;
    private final DataDictionaryDomainService dataDictionaryDomainService;
    
    public DataDictionaryApplicationServiceImpl(
            DataDictionaryAggregateRepository repository, 
            DistributedLockService distributedLockService, 
            EventPublisherService eventPublisherService,
            DataDictionaryDomainService dataDictionaryDomainService) {
        super(repository);
        this.distributedLockService = distributedLockService;
        this.eventPublisherService = eventPublisherService;
        this.dataDictionaryDomainService = dataDictionaryDomainService;
    }
    
    @Override
    public Flux<DataDictionaryAggregate> selectPage(Pageable pageable, DataDictionaryDTO query) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "分页查询数据字典: name={}, type={}, status={}", query.getName(), query.getType(), query.getStatus());
        // 根据查询条件构建查询
        return repository.findByNameContainingAndTypeAndStatus(query.getName() != null ? query.getName() : "", query.getType(), query.getStatus(), pageable);
    }
    
    @Override
    public Mono<Boolean> create(DataDictionaryAggregate dictionary) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "创建数据字典: name={}", dictionary.getName());
        String lockKey = "dataDictionary:create";
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                dictionary.setCreatedTime(LocalDateTime.now());
                dictionary.setUpdatedTime(LocalDateTime.now());
                return dataDictionaryDomainService.createDictionary(dictionary).flatMap(createdDictionary -> {
                    LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典创建成功: id={}, name={}", createdDictionary.getId(), createdDictionary.getName());
                    // 发布数据字典创建事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_CREATED", createdDictionary.getId(), createdDictionary.getName(), "CREATE", "数据字典创建成功");
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(DataDictionaryApplicationServiceImpl.class, "数据字典创建失败: name={}, error={}", dictionary.getName(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#dictionary.id")
    public Mono<Boolean> update(DataDictionaryAggregate dictionary) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "更新数据字典: id={}, name={}", dictionary.getId(), dictionary.getName());
        String lockKey = "dataDictionary:update:" + dictionary.getId();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                dictionary.setUpdatedTime(LocalDateTime.now());
                return dataDictionaryDomainService.updateDictionary(dictionary).flatMap(updatedDictionary -> {
                    LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典更新成功: id={}, name={}", updatedDictionary.getId(), updatedDictionary.getName());
                    // 发布数据字典更新事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_UPDATED", updatedDictionary.getId(), updatedDictionary.getName(), "UPDATE", "数据字典更新成功");
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(DataDictionaryApplicationServiceImpl.class, "数据字典更新失败: id={}, error={}", dictionary.getId(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#id")
    public Mono<Boolean> delete(Long id) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "删除数据字典: id={}", id);
        String lockKey = "dataDictionary:delete:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return dataDictionaryDomainService.deleteDictionary(id).flatMap(deleted -> {
                    if (deleted) {
                        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典删除成功: id={}", id);
                        // 发布数据字典删除事件
                        DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_DELETED", id, "", "DELETE", "数据字典删除成功");
                        return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                    } else {
                        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典删除失败: id={}", id);
                        return distributedLockService.unlock(lockKey).thenReturn(false);
                    }
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(DataDictionaryApplicationServiceImpl.class, "数据字典删除失败: id={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "dataDictionaries", key = "#id")
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "修改数据字典状态: id={}, status={}", id, status);
        String lockKey = "dataDictionary:changeStatus:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return dataDictionaryDomainService.changeStatus(id, status).flatMap(updatedDictionary -> {
                    String statusDesc = status == 0 ? "启用" : "禁用";
                    LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典状态修改成功: id={}, name={}, status={}", updatedDictionary.getId(), updatedDictionary.getName(), statusDesc);
                    // 发布数据字典状态变更事件
                    DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_STATUS_CHANGED", updatedDictionary.getId(), updatedDictionary.getName(), "STATUS_CHANGE", "数据字典状态变更为" + statusDesc);
                    return eventPublisherService.publishEvent("dataDictionary.events", event).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(DataDictionaryApplicationServiceImpl.class, "数据字典状态修改失败: id={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "导出数据字典Excel: name={}, type={}, status={}", query.getName(), query.getType(), query.getStatus());
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
                headerRow.createCell(2).setCellValue("编码");
                headerRow.createCell(3).setCellValue("类型");
                headerRow.createCell(4).setCellValue("状态");
                headerRow.createCell(5).setCellValue("描述");
                headerRow.createCell(6).setCellValue("创建时间");
                headerRow.createCell(7).setCellValue("更新时间");
                
                // 填充数据
                for (int i = 0; i < dataList.size(); i++) {
                    DataDictionaryAggregate dict = dataList.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(dict.getId());
                    row.createCell(1).setCellValue(dict.getName());
                    row.createCell(2).setCellValue(dict.getCode());
                    row.createCell(3).setCellValue(dict.getType());
                    row.createCell(4).setCellValue(dict.getStatus());
                    row.createCell(5).setCellValue(dict.getDescription());
                    row.createCell(6).setCellValue(dict.getCreatedTime().toString());
                    row.createCell(7).setCellValue(dict.getUpdatedTime().toString());
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
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "导出Excel失败: error={}", e.getMessage(), e);
                return Mono.error(e);
            }
        }).then();
    }
    
    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "导入数据字典Excel");
        String lockKey = "dataDictionary:import";
        return distributedLockService.tryLock(lockKey, 3, 30, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                try {
                    Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileData));
                    Sheet sheet = workbook.getSheetAt(0);
                    
                    List<DataDictionaryAggregate> dataList = new ArrayList<>();
                    
                    // 从第二行开始读取数据（第一行为表头）
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            DataDictionaryAggregate dict = DataDictionaryAggregate.builder()
                                    .name(getCellValueAsString(row.getCell(1)))
                                    .code(getCellValueAsString(row.getCell(2)))
                                    .type(getCellValueAsInteger(row.getCell(3)))
                                    .status(getCellValueAsInteger(row.getCell(4)))
                                    .description(getCellValueAsString(row.getCell(5)))
                                    .createdTime(LocalDateTime.now())
                                    .updatedTime(LocalDateTime.now())
                                    .build();
                            dataList.add(dict);
                        }
                    }
                    
                    workbook.close();
                    
                    // 批量保存数据并发布事件
                    return Flux.fromIterable(dataList)
                            .flatMap(dict -> dataDictionaryDomainService.createDictionary(dict).flatMap(createdDict -> {
                                LogUtil.info(DataDictionaryApplicationServiceImpl.class, "数据字典导入成功: id={}, name={}", createdDict.getId(), createdDict.getName());
                                // 发布数据字典创建事件
                                DataDictionaryEvent event = new DataDictionaryEvent("DATA_DICTIONARY_CREATED", createdDict.getId(), createdDict.getName(), "CREATE", "数据字典导入成功");
                                return eventPublisherService.publishEvent("dataDictionary.events", event).thenReturn(createdDict);
                            }))
                            .then(distributedLockService.unlock(lockKey))
                            .thenReturn(true)
                            .onErrorResume(throwable -> {
                                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "数据字典导入失败: error={}", throwable.getMessage(), throwable);
                                return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                            });
                } catch (IOException e) {
                    LogUtil.error(DataDictionaryApplicationServiceImpl.class, "导入Excel失败: error={}", e.getMessage(), e);
                    return distributedLockService.unlock(lockKey).then(Mono.just(false));
                }
            } else {
                LogUtil.error(DataDictionaryApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @Cacheable(value = "dataDictionaries", key = "#id")
    public Mono<DataDictionaryAggregate> findById(Long id) {
        LogUtil.info(DataDictionaryApplicationServiceImpl.class, "根据ID查询数据字典: id={}", id);
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