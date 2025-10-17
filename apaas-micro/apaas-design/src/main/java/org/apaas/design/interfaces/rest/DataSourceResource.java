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
import org.apaas.design.application.service.ReactiveDataSourceService;
import org.apaas.design.domain.model.DataSource;
import org.apaas.design.application.dto.DataSourceDTO;
import org.apaas.design.application.assembler.DataSourceAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 数据源管理资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/data-sources")
@Tag(name = "数据源管理", description = "数据源管理接口")
@RequiredArgsConstructor
public class DataSourceResource {
    
    private final ReactiveDataSourceService dataSourceService;
    
    @Operation(summary = "创建数据源")
    @PostMapping
    public Mono<DataSourceDTO> create(@RequestBody DataSourceDTO dataSourceDto) {
        DataSource dataSource = DataSourceAssembler.INSTANCE.convertDtoToEntity(dataSourceDto);
        return dataSourceService.save(dataSource)
                .map(DataSourceAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取数据源")
    @GetMapping("/{id}")
    public Mono<DataSourceDTO> getById(@PathVariable Long id) {
        return dataSourceService.findById(id)
                .map(DataSourceAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新数据源")
    @PutMapping("/{id}")
    public Mono<DataSourceDTO> update(@PathVariable Long id, @RequestBody DataSourceDTO dataSourceDto) {
        DataSource dataSource = DataSourceAssembler.INSTANCE.convertDtoToEntity(dataSourceDto);
        dataSource.setId(id);
        return dataSourceService.save(dataSource)
                .map(DataSourceAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除数据源")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return dataSourceService.deleteById(id);
    }
    
    @Operation(summary = "分页查询数据源")
    @PostMapping("/page")
    public Mono<PageResult<DataSourceDTO>> page(@RequestBody Query query) {
        return dataSourceService.selectPage(query)
                .map(pageResult -> {
                    PageResult<DataSourceDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(DataSourceAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Operation(summary = "测试数据源连接")
    @PostMapping("/{id}/test-connection")
    public Mono<Boolean> testConnection(@PathVariable Long id) {
        // TODO: 实现数据源连接测试逻辑
        return Mono.just(true);
    }
}