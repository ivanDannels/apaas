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
package org.apaas.design.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.design.application.service.ReactiveCodeGeneratorService;
import org.apaas.design.domain.model.CodeGenerator;
import org.apaas.design.application.dto.CodeGeneratorDTO;
import org.apaas.design.application.assembler.CodeGeneratorAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 代码生成器资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/code-generators")
@Tag(name = "代码生成器", description = "代码生成器接口")
@RequiredArgsConstructor
public class CodeGeneratorResource {
    
    private final ReactiveCodeGeneratorService codeGeneratorService;
    
    @Operation(summary = "创建代码生成器")
    @PostMapping
    public Mono<CodeGeneratorDTO> create(@RequestBody CodeGeneratorDTO codeGeneratorDto) {
        CodeGenerator codeGenerator = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(codeGeneratorDto);
        return codeGeneratorService.save(codeGenerator)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取代码生成器")
    @GetMapping("/{id}")
    public Mono<CodeGeneratorDTO> getById(@PathVariable Long id) {
        return codeGeneratorService.findById(id)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新代码生成器")
    @PutMapping("/{id}")
    public Mono<CodeGeneratorDTO> update(@PathVariable Long id, @RequestBody CodeGeneratorDTO codeGeneratorDto) {
        CodeGenerator codeGenerator = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(codeGeneratorDto);
        codeGenerator.setId(id);
        return codeGeneratorService.save(codeGenerator)
                .map(CodeGeneratorAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除代码生成器")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return codeGeneratorService.deleteById(id);
    }
    
    @Operation(summary = "分页查询代码生成器")
    @PostMapping("/page")
    public Mono<PageResult<CodeGeneratorDTO>> page(@RequestBody Query query) {
        return codeGeneratorService.selectPage(query)
                .map(pageResult -> {
                    PageResult<CodeGeneratorDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(CodeGeneratorAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Operation(summary = "执行代码生成")
    @PostMapping("/{id}/generate")
    public Mono<String> generateCode(@PathVariable Long id) {
        // TODO: 实现代码生成逻辑
        return Mono.just("代码生成任务已提交");
    }
}