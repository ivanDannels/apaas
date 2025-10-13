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
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.system.application.dto.DataDictionaryDTO;
import org.apaas.system.domain.model.DataDictionaryAggregate;
import org.apaas.system.domain.repository.DataDictionaryAggregateRepository;
import org.apaas.system.application.service.ReactiveDataDictionaryService;
import org.apaas.system.application.service.DataDictionaryApplicationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

/**
 * 响应式数据字典服务实现适配器
 * 适配旧的接口到新的应用服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryServiceImpl extends AbstractApplicationService<DataDictionaryAggregate, Long, DataDictionaryAggregateRepository> implements ReactiveDataDictionaryService {
    
    private final DataDictionaryApplicationService dataDictionaryApplicationService;
    
    public ReactiveDataDictionaryServiceImpl(DataDictionaryAggregateRepository repository, DataDictionaryApplicationService dataDictionaryApplicationService) {
        super(repository);
        this.dataDictionaryApplicationService = dataDictionaryApplicationService;
    }
    
    @Override
    public Flux<DataDictionaryDTO> selectPage(Pageable pageable, DataDictionaryDTO query) {
        return dataDictionaryApplicationService.selectPage(pageable, query);
    }
    
    @Override
    public Mono<Boolean> create(DataDictionaryDTO dictionary) {
        return dataDictionaryApplicationService.create(dictionary);
    }
    
    @Override
    public Mono<Boolean> update(DataDictionaryDTO dictionary) {
        return dataDictionaryApplicationService.update(dictionary);
    }
    
    @Override
    public Mono<Boolean> delete(Long id) {
        return dataDictionaryApplicationService.delete(id);
    }
    
    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return dataDictionaryApplicationService.changeStatus(id, status);
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        return dataDictionaryApplicationService.exportExcel(exchange, query);
    }
    
    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        return dataDictionaryApplicationService.importExcel(fileData);
    }
    
    @Override
    public Mono<DataDictionaryDTO> findById(Long id) {
        return repository.findById(id).map(DataDictionaryAssembler.INSTANCE::convertEntityToDto);
    }
}