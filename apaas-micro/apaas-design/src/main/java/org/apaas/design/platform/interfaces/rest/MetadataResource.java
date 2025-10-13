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
import org.apaas.design.platform.application.service.ReactiveMetadataService;
import org.apaas.design.platform.domain.model.Metadata;
import org.apaas.design.platform.application.dto.MetadataDTO;
import org.apaas.design.platform.application.assembler.MetadataAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 元数据管理资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/metadata")
@Tag(name = "元数据管理", description = "元数据管理接口")
@RequiredArgsConstructor
public class MetadataResource {
    
    private final ReactiveMetadataService metadataService;
    
    @Operation(summary = "创建元数据")
    @PostMapping
    public Mono<MetadataDTO> create(@RequestBody MetadataDTO metadataDto) {
        Metadata metadata = MetadataAssembler.INSTANCE.convertDtoToEntity(metadataDto);
        return metadataService.save(metadata)
                .map(MetadataAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取元数据")
    @GetMapping("/{id}")
    public Mono<MetadataDTO> getById(@PathVariable Long id) {
        return metadataService.findById(id)
                .map(MetadataAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新元数据")
    @PutMapping("/{id}")
    public Mono<MetadataDTO> update(@PathVariable Long id, @RequestBody MetadataDTO metadataDto) {
        Metadata metadata = MetadataAssembler.INSTANCE.convertDtoToEntity(metadataDto);
        metadata.setId(id);
        return metadataService.save(metadata)
                .map(MetadataAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除元数据")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return metadataService.deleteById(id);
    }
    
    @Operation(summary = "分页查询元数据")
    @PostMapping("/page")
    public Mono<PageResult<MetadataDTO>> page(@RequestBody Query query) {
        return metadataService.selectPage(query)
                .map(pageResult -> {
                    PageResult<MetadataDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(MetadataAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
}