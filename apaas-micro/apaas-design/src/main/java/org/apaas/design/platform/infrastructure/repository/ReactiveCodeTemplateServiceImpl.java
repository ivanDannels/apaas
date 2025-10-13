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
import org.apaas.design.platform.domain.model.CodeTemplate;
import org.apaas.design.platform.domain.repository.CodeTemplateRepository;
import org.apaas.design.platform.application.dto.CodeTemplateDTO;
import org.apaas.design.platform.application.assembler.CodeTemplateAssembler;
import org.apaas.design.platform.application.service.ReactiveCodeTemplateService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.infrastructure.convert.PageConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式代码模板服务实现类
 * @author ivan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveCodeTemplateServiceImpl extends AbstractApplicationService<CodeTemplate, Long, CodeTemplateRepository> implements ReactiveCodeTemplateService {
    
    private final CodeTemplateRepository repository;
    
    public ReactiveCodeTemplateServiceImpl(CodeTemplateRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<CodeTemplateDTO>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageable(query);
        return repository.findAll(pageable)
                .map(page -> {
                    PageResult<CodeTemplate> pageResult = PageConverter.convertPageResult(page);
                    PageResult<CodeTemplateDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(CodeTemplateAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Override
    public Mono<CodeTemplateDTO> create(CodeTemplateDTO codeTemplateDto) {
        CodeTemplate codeTemplate = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(codeTemplateDto);
        return repository.save(codeTemplate)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<CodeTemplateDTO> update(Long id, CodeTemplateDTO codeTemplateDto) {
        CodeTemplate codeTemplate = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(codeTemplateDto);
        codeTemplate.setId(id);
        return repository.save(codeTemplate)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<CodeTemplateDTO> findById(Long id) {
        return repository.findById(id)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<CodeTemplateDTO> save(CodeTemplateDTO codeTemplateDto) {
        CodeTemplate codeTemplate = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(codeTemplateDto);
        return repository.save(codeTemplate)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
}