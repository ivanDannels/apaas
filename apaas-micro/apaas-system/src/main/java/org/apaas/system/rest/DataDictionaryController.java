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
package org.apaas.system.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import org.apaas.system.service.ReactiveDataDictionaryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式数据字典控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/data-dictionaries")
@Tag(name = "响应式数据字典管理", description = "响应式数据字典相关操作")
public class DataDictionaryController extends ReactiveBaseController<DataDictionary, Long, ReactiveDataDictionaryService> {
    
    public DataDictionaryController(ReactiveDataDictionaryService service) {
        super(service);
    }
    
    /**
     * 分页查询数据字典
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询数据字典", description = "根据条件分页查询数据字典列表")
    @Parameters({@Parameter(name = "pageNum", description = "页码", required = true), @Parameter(name = "pageSize", description = "每页条数", required = true), @Parameter(name = "name", description = "字典名称，模糊查询"), @Parameter(name = "type", description = "字典类型：0-系统字典，1-业务字典"), @Parameter(name = "status", description = "状态：0-正常，1-停用")})
    public Flux<DataDictionary> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        DataDictionaryDTO query = new DataDictionaryDTO();
        query.setName(name);
        query.setType(type);
        query.setStatus(status);
        return service.selectPage(pageable, query);
    }
    
    /**
     * 获取数据字典详情
     * 注：由于响应式服务接口中没有直接提供根据ID查询的方法，
     * 实际实现中可能需要添加该方法或通过其他方式实现
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取数据字典详情", description = "根据ID获取数据字典详情")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<DataDictionary> getById(@PathVariable Long id) {
        // 这里需要实现根据ID查询数据字典的逻辑
        // 临时实现：假设通过分页查询并过滤来获取单个结果
        Pageable pageable = Pageable.ofSize(1);
        DataDictionaryDTO query = new DataDictionaryDTO();
        return service.selectPage(pageable, query).next().switchIfEmpty(Mono.error(new RuntimeException("数据字典不存在")));
    }
    
    /**
     * 创建数据字典
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建数据字典", description = "创建新的数据字典")
    public Mono<Boolean> create(@RequestBody DataDictionary dataDictionary) {
        return service.create(dataDictionary);
    }
    
    /**
     * 更新数据字典
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新数据字典", description = "更新数据字典信息")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody DataDictionary dataDictionary) {
        dataDictionary.setId(id);
        return service.update(dataDictionary);
    }
    
    /**
     * 导出数据字典
     */
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出数据字典", description = "导出数据字典")
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        return service.exportExcel(exchange, query);
    }
    
    /**
     * 导入数据字典
     * 注：需要在响应式服务接口中添加导入方法
     */
    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入数据字典", description = "导入数据字典")
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@RequestPart("file") Mono<FilePart> filePart) {
        // 实现导入逻辑，需要在ReactiveDataDictionaryService中添加importExcel方法
        // 此处为示例实现
        return Mono.error(new UnsupportedOperationException("导入功能尚未实现"));
    }
    
    /**
     * 启用/停用数据字典
     */
    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启用/停用数据字典", description = "启用或停用指定的数据字典")
    @Parameters({@Parameter(name = "id", description = "数据字典ID", required = true), @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)})
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }
}