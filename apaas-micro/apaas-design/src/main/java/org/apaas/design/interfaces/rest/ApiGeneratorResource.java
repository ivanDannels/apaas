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
import org.apaas.design.application.service.ReactiveApiDefinitionService;
import org.apaas.design.domain.model.ApiDefinition;
import org.apaas.design.application.dto.ApiDefinitionDTO;
import org.apaas.design.application.assembler.ApiDefinitionAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * API接口生成资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/api-definitions")
@Tag(name = "API接口生成", description = "API接口定义与生成接口")
@RequiredArgsConstructor
public class ApiGeneratorResource {
    
    private final ReactiveApiDefinitionService apiDefinitionService;
    
    @Operation(summary = "创建API定义")
    @PostMapping
    public Mono<ApiDefinitionDTO> create(@RequestBody ApiDefinitionDTO apiDefinitionDto) {
        ApiDefinition apiDefinition = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(apiDefinitionDto);
        return apiDefinitionService.save(apiDefinition)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取API定义")
    @GetMapping("/{id}")
    public Mono<ApiDefinitionDTO> getById(@PathVariable Long id) {
        return apiDefinitionService.findById(id)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新API定义")
    @PutMapping("/{id}")
    public Mono<ApiDefinitionDTO> update(@PathVariable Long id, @RequestBody ApiDefinitionDTO apiDefinitionDto) {
        ApiDefinition apiDefinition = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(apiDefinitionDto);
        apiDefinition.setId(id);
        return apiDefinitionService.save(apiDefinition)
                .map(ApiDefinitionAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除API定义")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return apiDefinitionService.deleteById(id);
    }
    
    @Operation(summary = "分页查询API定义")
    @PostMapping("/page")
    public Mono<PageResult<ApiDefinitionDTO>> page(@RequestBody Query query) {
        return apiDefinitionService.selectPage(query)
                .map(pageResult -> {
                    PageResult<ApiDefinitionDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(ApiDefinitionAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Operation(summary = "发布API")
    @PostMapping("/{id}/publish")
    public Mono<String> publish(@PathVariable Long id) {
        // TODO: 实现API发布逻辑
        return Mono.just("API发布成功");
    }
    
    @Operation(summary = "测试API")
    @PostMapping("/{id}/test")
    public Mono<String> test(@PathVariable Long id) {
        // TODO: 实现API测试逻辑
        return Mono.just("API测试成功");
    }
}