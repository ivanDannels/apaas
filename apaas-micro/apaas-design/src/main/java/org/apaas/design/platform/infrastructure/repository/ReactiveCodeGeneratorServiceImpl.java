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
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.design.platform.domain.model.CodeGenerator;
import org.apaas.design.platform.domain.repository.CodeGeneratorRepository;
import org.apaas.design.platform.application.dto.CodeGeneratorDTO;
import org.apaas.design.platform.application.assembler.CodeGeneratorAssembler;
import org.apaas.design.platform.application.service.ReactiveCodeGeneratorService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.infrastructure.convert.PageConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式代码生成器服务实现类
 * @author ivan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveCodeGeneratorServiceImpl extends AbstractApplicationService<CodeGenerator, Long, CodeGeneratorRepository> implements ReactiveCodeGeneratorService {
    
    private final CodeGeneratorRepository repository;
    
    public ReactiveCodeGeneratorServiceImpl(CodeGeneratorRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<CodeGeneratorDTO>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageable(query);
        return repository.findAll(pageable)
                .map(page -> {
                    PageResult<CodeGenerator> pageResult = PageConverter.convertPageResult(page);
                    PageResult<CodeGeneratorDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(CodeGeneratorAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Override
    public Mono<CodeGeneratorDTO> create(CodeGeneratorDTO codeGeneratorDto) {
        CodeGenerator codeGenerator = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(codeGeneratorDto);
        return repository.save(codeGenerator)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<CodeGeneratorDTO> update(Long id, CodeGeneratorDTO codeGeneratorDto) {
        CodeGenerator codeGenerator = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(codeGeneratorDto);
        codeGenerator.setId(id);
        return repository.save(codeGenerator)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<CodeGeneratorDTO> findById(Long id) {
        return repository.findById(id)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<CodeGeneratorDTO> save(CodeGeneratorDTO codeGeneratorDto) {
        CodeGenerator codeGenerator = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(codeGeneratorDto);
        return repository.save(codeGenerator)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
}