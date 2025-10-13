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
package org.apaas.system.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.query.Query;
import org.apaas.core.query.PageResult;
import org.apaas.infrastructure.convert.PageConverter;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.system.domain.model.DataDictionaryItem;
import org.apaas.system.domain.repository.DataDictionaryItemRepository;
import org.apaas.system.application.service.ReactiveDataDictionaryItemService;
import org.apaas.system.application.dto.DataDictionaryItemDTO;
import org.apaas.system.application.assembler.DataDictionaryItemAssembler;
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

/**
 * 响应式数据字典项服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryItemServiceImpl extends AbstractApplicationService<DataDictionaryItem, Long, DataDictionaryItemRepository> implements ReactiveDataDictionaryItemService {
    
    private final DataDictionaryItemRepository repository;
    
    public ReactiveDataDictionaryItemServiceImpl(DataDictionaryItemRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<DataDictionaryItem>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageRequest(query);
        DataDictionaryItemDTO dataDictionaryItemDto = (DataDictionaryItemDTO) query.getCondition();
        
        // 根据查询条件构建查询
        return repository.findByDictionaryId(dataDictionaryItemDto.getDictionaryId() != null ? dataDictionaryItemDto.getDictionaryId() : 0L)
                .filter(item -> dataDictionaryItemDto.getName() == null || item.getName().contains(dataDictionaryItemDto.getName()))
                .filter(item -> dataDictionaryItemDto.getCode() == null || item.getCode().contains(dataDictionaryItemDto.getCode()))
                .filter(item -> dataDictionaryItemDto.getStatus() == null || item.getStatus().equals(dataDictionaryItemDto.getStatus()))
                .collectList()
                .map(list -> {
                    // 实现分页逻辑
                    int total = list.size();
                    int start = (int) pageable.getOffset();
                    int end = Math.min(start + pageable.getPageSize(), total);
                    List<DataDictionaryItem> pageList = list.subList(start, end);
                    
                    PageResult<DataDictionaryItem> pageResult = new PageResult<>();
                    pageResult.setCurrent(pageable.getPageNumber() + 1);
                    pageResult.setSize(pageable.getPageSize());
                    pageResult.setTotal(total);
                    pageResult.setPages((total + pageable.getPageSize() - 1) / pageable.getPageSize());
                    pageResult.setRecords(pageList);
                    
                    return pageResult;
                });
    }
    
    @Override
    @Cacheable(value = "dataDictionaryItems", key = "#dictionaryId")
    public Flux<DataDictionaryItemDTO> selectByDictionaryId(Long dictionaryId) {
        return repository.findByDictionaryId(dictionaryId)
                .filter(item -> item.getStatus() == 0)
                .map(DataDictionaryItemAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Boolean> create(DataDictionaryItemDTO dataDictionaryItemDto) {
        DataDictionaryItem dataDictionaryItem = DataDictionaryItemAssembler.INSTANCE.convertDtoToEntity(dataDictionaryItemDto);
        return repository.save(dataDictionaryItem)
                .map(savedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }
    
    @Override
    @CacheEvict(value = "dataDictionaryItems", key = "#dataDictionaryItemDto.id")
    public Mono<Boolean> update(DataDictionaryItemDTO dataDictionaryItemDto) {
        DataDictionaryItem dataDictionaryItem = DataDictionaryItemAssembler.INSTANCE.convertDtoToEntity(dataDictionaryItemDto);
        return repository.save(dataDictionaryItem)
                .map(updatedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }
    
    @Override
    @CacheEvict(value = "dataDictionaryItems", key = "#id")
    public Mono<Boolean> delete(Long id) {
        return repository.deleteById(id)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }
    
    @Override
    @CacheEvict(value = "dataDictionaryItems", key = "#id")
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return repository.findById(id)
                .flatMap(dataDictionaryItem -> {
                    dataDictionaryItem.setStatus(status);
                    dataDictionaryItem.setUpdatedTime(LocalDateTime.now());
                    return repository.save(dataDictionaryItem);
                })
                .map(updatedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryItemDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dataDictionaryItem.xlsx");
        
        // 查询数据
        Flux<DataDictionaryItem> dataFlux = repository.findByDictionaryId(query.getDictionaryId() != null ? query.getDictionaryId() : 0L)
                .filter(item -> query.getName() == null || item.getName().contains(query.getName()))
                .filter(item -> query.getCode() == null || item.getCode().contains(query.getCode()))
                .filter(item -> query.getStatus() == null || item.getStatus().equals(query.getStatus()));
        
        return dataFlux.collectList().flatMap(dataList -> {
            try {
                // 创建Excel工作簿
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("数据字典项");
                
                // 创建表头
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("字典ID");
                headerRow.createCell(2).setCellValue("编码");
                headerRow.createCell(3).setCellValue("名称");
                headerRow.createCell(4).setCellValue("值");
                headerRow.createCell(5).setCellValue("排序");
                headerRow.createCell(6).setCellValue("状态");
                headerRow.createCell(7).setCellValue("描述");
                headerRow.createCell(8).setCellValue("创建时间");
                headerRow.createCell(9).setCellValue("更新时间");
                
                // 填充数据
                for (int i = 0; i < dataList.size(); i++) {
                    DataDictionaryItem item = dataList.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(item.getId());
                    row.createCell(1).setCellValue(item.getDictionaryId());
                    row.createCell(2).setCellValue(item.getCode());
                    row.createCell(3).setCellValue(item.getName());
                    row.createCell(4).setCellValue(item.getValue());
                    row.createCell(5).setCellValue(item.getSequence());
                    row.createCell(6).setCellValue(item.getStatus());
                    row.createCell(7).setCellValue(item.getDescription());
                    row.createCell(8).setCellValue(item.getCreatedTime().toString());
                    row.createCell(9).setCellValue(item.getUpdatedTime().toString());
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
                log.error("导出Excel失败: error={}", e.getMessage(), e);
                return Mono.error(e);
            }
        }).then();
    }
    
    @Override
    public Mono<Boolean> importExcel(Long dictionaryId, byte[] fileData) {
        return Mono.fromCallable(() -> {
            try {
                Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileData));
                Sheet sheet = workbook.getSheetAt(0);
                
                List<DataDictionaryItem> dataList = new ArrayList<>();
                
                // 从第二行开始读取数据（第一行为表头）
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        DataDictionaryItem item = new DataDictionaryItem();
                        item.setDictionaryId(dictionaryId);
                        item.setCode(getCellValueAsString(row.getCell(2)));
                        item.setName(getCellValueAsString(row.getCell(3)));
                        item.setValue(getCellValueAsString(row.getCell(4)));
                        item.setSequence(getCellValueAsInteger(row.getCell(5)));
                        item.setStatus(getCellValueAsInteger(row.getCell(6)));
                        item.setDescription(getCellValueAsString(row.getCell(7)));
                        item.setCreatedTime(LocalDateTime.now());
                        item.setUpdatedTime(LocalDateTime.now());
                        dataList.add(item);
                    }
                }
                
                workbook.close();
                
                // 批量保存数据
                return Flux.fromIterable(dataList)
                        .flatMap(item -> repository.save(item))
                        .then(Mono.just(true))
                        .onErrorReturn(false)
                        .block();
            } catch (IOException e) {
                log.error("导入Excel失败: error={}", e.getMessage(), e);
                return false;
            }
        });
    }
    
    @Override
    public Mono<DataDictionaryItem> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public Flux<DataDictionaryItem> saveBatch(Flux<DataDictionaryItem> items) {
        return items.flatMap(item -> repository.save(item));
    }
    
    @Override
    public Flux<DataDictionaryItem> updateBatch(Flux<DataDictionaryItem> items) {
        return items.flatMap(item -> repository.save(item));
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