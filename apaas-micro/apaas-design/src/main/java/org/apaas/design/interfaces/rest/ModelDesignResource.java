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
import org.apaas.design.application.service.ReactiveModelService;
import org.apaas.design.domain.model.Model;
import org.apaas.design.application.dto.ModelDTO;
import org.apaas.design.application.assembler.ModelAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 模型设计资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/models")
@Tag(name = "模型设计", description = "数据模型设计接口")
@RequiredArgsConstructor
public class ModelDesignResource {
    
    private final ReactiveModelService modelService;
    
    @Operation(summary = "创建数据模型")
    @PostMapping
    public Mono<ModelDTO> create(@RequestBody ModelDTO modelDto) {
        Model model = ModelAssembler.INSTANCE.convertDtoToEntity(modelDto);
        return modelService.save(model)
                .map(ModelAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取数据模型")
    @GetMapping("/{id}")
    public Mono<ModelDTO> getById(@PathVariable Long id) {
        return modelService.findById(id)
                .map(ModelAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新数据模型")
    @PutMapping("/{id}")
    public Mono<ModelDTO> update(@PathVariable Long id, @RequestBody ModelDTO modelDto) {
        Model model = ModelAssembler.INSTANCE.convertDtoToEntity(modelDto);
        model.setId(id);
        return modelService.save(model)
                .map(ModelAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除数据模型")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return modelService.deleteById(id);
    }
    
    @Operation(summary = "分页查询数据模型")
    @PostMapping("/page")
    public Mono<PageResult<ModelDTO>> page(@RequestBody Query query) {
        return modelService.selectPage(query)
                .map(pageResult -> {
                    PageResult<ModelDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(ModelAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
}