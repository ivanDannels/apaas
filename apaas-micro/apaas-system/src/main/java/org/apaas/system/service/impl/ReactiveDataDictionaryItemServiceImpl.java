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
import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.repository.DataDictionaryItemRepository;
import org.apaas.system.service.ReactiveDataDictionaryItemService;
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

import java.time.LocalDateTime;

/**
 * 响应式数据字典项服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryItemServiceImpl extends BaseServiceImpl<DataDictionaryItem, Long, DataDictionaryItemRepository> implements ReactiveDataDictionaryItemService {
    
    public ReactiveDataDictionaryItemServiceImpl(DataDictionaryItemRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<DataDictionaryItem> selectPage(Pageable pageable, DataDictionaryItemDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有数据字典项
        return repository.findAll();
    }
    
    @Override
    public Flux<DataDictionaryItem> selectByDictionaryId(Long dictionaryId) {
        return repository.findByDictionaryId(dictionaryId);
    }
    
    @Override
    public Mono<Boolean> create(DataDictionaryItem dataDictionaryItem) {
        return repository.save(dataDictionaryItem).map(savedDataDictionaryItem -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> update(DataDictionaryItem dataDictionaryItem) {
        return repository.save(dataDictionaryItem).map(updatedDataDictionaryItem -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> delete(Long id) {
        return repository.deleteById(id).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return repository.findById(id).flatMap(dataDictionaryItem -> {
            dataDictionaryItem.setStatus(status);
            dataDictionaryItem.setUpdatedTime(LocalDateTime.now());
            return repository.save(dataDictionaryItem);
        }).map(updatedDataDictionaryItem -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryItemDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dataDictionaryItem.xlsx");
        
        // 这里需要实现Excel导出逻辑
        // 暂时返回空响应
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(new byte[0]);
        return response.writeWith(Mono.just(dataBuffer));
    }
    
    @Override
    public Mono<Boolean> importExcel(Long dictionaryId, byte[] fileData) {
        // 这里需要实现Excel导入逻辑
        // 暂时返回true
        return Mono.just(true);
    }
}