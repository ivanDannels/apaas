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
package org.apaas.design.platform.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.design.platform.application.service.ReactiveCodeTemplateService;
import org.apaas.design.platform.domain.model.CodeTemplate;
import org.apaas.design.platform.application.dto.CodeTemplateDTO;
import org.apaas.design.platform.application.assembler.CodeTemplateAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 代码模板资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/code-templates")
@Tag(name = "代码模板管理", description = "代码模板管理接口")
@RequiredArgsConstructor
public class CodeTemplateResource {
    
    private final ReactiveCodeTemplateService codeTemplateService;
    
    @Operation(summary = "创建代码模板")
    @PostMapping
    public Mono<CodeTemplateDTO> create(@RequestBody CodeTemplateDTO codeTemplateDto) {
        CodeTemplate codeTemplate = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(codeTemplateDto);
        return codeTemplateService.save(codeTemplate)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取代码模板")
    @GetMapping("/{id}")
    public Mono<CodeTemplateDTO> getById(@PathVariable Long id) {
        return codeTemplateService.findById(id)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新代码模板")
    @PutMapping("/{id}")
    public Mono<CodeTemplateDTO> update(@PathVariable Long id, @RequestBody CodeTemplateDTO codeTemplateDto) {
        CodeTemplate codeTemplate = CodeTemplateAssembler.INSTANCE.convertDtoToEntity(codeTemplateDto);
        codeTemplate.setId(id);
        return codeTemplateService.save(codeTemplate)
                .map(CodeTemplateAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除代码模板")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return codeTemplateService.deleteById(id);
    }
    
    @Operation(summary = "分页查询代码模板")
    @PostMapping("/page")
    public Mono<PageResult<CodeTemplateDTO>> page(@RequestBody Query query) {
        return codeTemplateService.selectPage(query)
                .map(pageResult -> {
                    PageResult<CodeTemplateDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(CodeTemplateAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
}