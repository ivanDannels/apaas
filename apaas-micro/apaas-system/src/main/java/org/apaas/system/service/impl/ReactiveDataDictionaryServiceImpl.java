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
import org.apaas.system.service.ReactiveDataDictionaryService;
import org.apaas.system.service.DataDictionaryApplicationService;
import org.springframework.stereotype.Service;

/**
 * 响应式数据字典服务实现适配器
 * 适配旧的接口到新的应用服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryServiceImpl extends AbstractApplicationService<DataDictionaryAggregate, Long, DataDictionaryAggregateRepository> implements ReactiveDataDictionaryService {
    
    private final DataDictionaryApplicationService dataDictionaryApplicationService;
    
    public ReactiveDataDictionaryServiceImpl(
            DataDictionaryAggregateRepository repository,
            DataDictionaryApplicationService dataDictionaryApplicationService) {
        super(repository);
        this.dataDictionaryApplicationService = dataDictionaryApplicationService;
    }
    
    @Override
    public reactor.core.publisher.Flux<DataDictionaryAggregate> selectPage(org.springframework.data.domain.Pageable pageable, DataDictionaryDTO query) {
        return dataDictionaryApplicationService.selectPage(pageable, query);
    }
    
    @Override
    public reactor.core.publisher.Mono<Boolean> create(DataDictionaryAggregate dictionary) {
        return dataDictionaryApplicationService.create(dictionary);
    }
    
    @Override
    public reactor.core.publisher.Mono<Boolean> update(DataDictionaryAggregate dictionary) {
        return dataDictionaryApplicationService.update(dictionary);
    }
    
    @Override
    public reactor.core.publisher.Mono<Boolean> delete(Long id) {
        return dataDictionaryApplicationService.delete(id);
    }
    
    @Override
    public reactor.core.publisher.Mono<Boolean> changeStatus(Long id, Integer status) {
        return dataDictionaryApplicationService.changeStatus(id, status);
    }
    
    @Override
    public reactor.core.publisher.Mono<Void> exportExcel(org.springframework.web.server.ServerWebExchange exchange, DataDictionaryDTO query) {
        return dataDictionaryApplicationService.exportExcel(exchange, query);
    }
    
    @Override
    public reactor.core.publisher.Mono<Boolean> importExcel(byte[] fileData) {
        return dataDictionaryApplicationService.importExcel(fileData);
    }
    
    @Override
    public reactor.core.publisher.Mono<DataDictionaryAggregate> findById(Long id) {
        return dataDictionaryApplicationService.findById(id);
    }
}