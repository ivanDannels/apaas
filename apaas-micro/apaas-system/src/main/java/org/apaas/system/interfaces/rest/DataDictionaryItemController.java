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
package org.apaas.system.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.Query;
import org.apaas.domain.interfaces.rest.ReactiveBaseController;
import org.apaas.system.domain.model.DataDictionaryItem;
import org.apaas.system.application.service.ReactiveDataDictionaryItemService;
import org.apaas.core.query.PageResult;
import org.apaas.system.application.dto.DataDictionaryItemDTO;
import org.apaas.system.application.assembler.DataDictionaryItemAssembler;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式数据字典项控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/data-dictionary-items")
@Tag(name = "响应式数据字典项管理", description = "响应式数据字典项相关操作")
public class DataDictionaryItemController extends ReactiveBaseController<DataDictionaryItem, Long, ReactiveDataDictionaryItemService> {
    
    public DataDictionaryItemController(ReactiveDataDictionaryItemService service) {
        super(service);
    }
    
    /**
     * 分页查询数据字典项
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询数据字典项", description = "根据条件分页查询数据字典项列表")
    public Mono<PageResult<DataDictionaryItemDTO>> selectPage(@RequestBody Query query) {
        return service.selectPage(query);
    }
    
    /**
     * 获取数据字典项详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取数据字典项详情", description = "根据ID获取数据字典项详情")
    @Parameter(name = "id", description = "数据字典项ID", required = true)
    public Mono<DataDictionaryItemDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("数据字典项不存在")));
    }
    
    /**
     * 根据字典ID查询字典项列表
     */
    @GetMapping(value = "/by-dictionary/{dictionaryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字典ID查询字典项列表", description = "根据字典ID查询有效的字典项列表")
    @Parameter(name = "dictionaryId", description = "字典ID", required = true)
    public Flux<DataDictionaryItemDTO> getByDictionaryId(@PathVariable Long dictionaryId) {
        return service.selectByDictionaryId(dictionaryId);
    }
    
    /**
     * 创建数据字典项
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建数据字典项", description = "创建新的数据字典项")
    public Mono<Boolean> create(@RequestBody DataDictionaryItemDTO dataDictionaryItemDto) {
        return service.create(dataDictionaryItemDto);
    }
    
    /**
     * 更新数据字典项
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新数据字典项", description = "更新数据字典项信息")
    @Parameter(name = "id", description = "数据字典项ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody DataDictionaryItemDTO dataDictionaryItemDto) {
        dataDictionaryItemDto.setId(id);
        return service.update(dataDictionaryItemDto);
    }
    
    /**
     * 导出数据字典项
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出数据字典项", description = "导出数据字典项")
    public Mono<Void> exportExcel(ServerWebExchange exchange, @RequestBody DataDictionaryItemDTO query) {
        return service.exportExcel(exchange, query);
    }
    
    /**
     * 导入数据字典项
     */
    @PostMapping(value = "/import/{dictionaryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入数据字典项", description = "导入数据字典项")
    @Parameter(name = "dictionaryId", description = "数据字典ID", required = true)
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@PathVariable Long dictionaryId, @RequestBody byte[] fileData) {
        return service.importExcel(dictionaryId, fileData);
    }
}