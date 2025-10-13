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
package org.apaas.design.platform.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.design.platform.domain.model.ApiDefinition;
import org.apaas.design.platform.domain.repository.ApiDefinitionRepository;
import org.apaas.design.platform.application.dto.ApiDefinitionDTO;
import org.apaas.design.platform.application.assembler.ApiDefinitionAssembler;
import org.apaas.design.platform.application.service.ReactiveApiDefinitionService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.infrastructure.convert.PageConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式API定义服务实现类
 * @author ivan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveApiDefinitionServiceImpl extends AbstractApplicationService<ApiDefinition, Long, ApiDefinitionRepository> implements ReactiveApiDefinitionService {
    
    private final ApiDefinitionRepository repository;
    
    public ReactiveApiDefinitionServiceImpl(ApiDefinitionRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<ApiDefinitionDTO>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageable(query);
        return repository.findAll(pageable)
                .map(page -> {
                    PageResult<ApiDefinition> pageResult = PageConverter.convertPageResult(page);
                    PageResult<ApiDefinitionDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(ApiDefinitionAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Override
    public Mono<ApiDefinitionDTO> create(ApiDefinitionDTO apiDefinitionDto) {
        ApiDefinition apiDefinition = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(apiDefinitionDto);
        return repository.save(apiDefinition)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<ApiDefinitionDTO> update(Long id, ApiDefinitionDTO apiDefinitionDto) {
        ApiDefinition apiDefinition = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(apiDefinitionDto);
        apiDefinition.setId(id);
        return repository.save(apiDefinition)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<ApiDefinitionDTO> findById(Long id) {
        return repository.findById(id)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<ApiDefinitionDTO> save(ApiDefinitionDTO apiDefinitionDto) {
        ApiDefinition apiDefinition = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(apiDefinitionDto);
        return repository.save(apiDefinition)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
}