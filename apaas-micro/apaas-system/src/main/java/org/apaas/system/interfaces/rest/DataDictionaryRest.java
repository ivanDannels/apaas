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
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.interfaces.rest.BaseRest;
import org.apaas.system.domain.model.DataDictionaryAggregate;
import org.apaas.system.application.service.DataDictionaryApplicationService;
import org.apaas.system.application.dto.DataDictionaryDTO;
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
public class DataDictionaryRest extends BaseRest<DataDictionaryAggregate, Long, DataDictionaryApplicationService> {
    
    public DataDictionaryRest(DataDictionaryApplicationService service) {
        super(service);
    }
    
    /**
     * 分页查询数据字典
     */
    @Log(title = "分页查询数据字典", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询数据字典", description = "根据条件分页查询数据字典列表")
    @Parameters({@Parameter(name = "pageNum", description = "页码", required = true), @Parameter(name = "pageSize", description = "每页条数", required = true), @Parameter(name = "name", description = "字典名称，模糊查询"), @Parameter(name = "type", description = "字典类型：0-系统字典，1-业务字典"), @Parameter(name = "status", description = "状态：0-正常，1-停用")})
    public Flux<DataDictionaryDTO> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        DataDictionaryDTO query = new DataDictionaryDTO();
        query.setName(name);
        query.setType(type);
        query.setStatus(status);
        return service.selectPage(pageable, query);
    }
    
    /**
     * 获取数据字典详情
     */
    @Log(title = "获取数据字典详情", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取数据字典详情", description = "根据ID获取数据字典详情")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<DataDictionaryDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("数据字典不存在")));
    }
    
    /**
     * 创建数据字典
     */
    @Log(title = "创建数据字典", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建数据字典", description = "创建新的数据字典")
    public Mono<Boolean> create(@RequestBody DataDictionaryDTO dictionaryDto) {
        return service.create(dictionaryDto);
    }
    
    /**
     * 更新数据字典
     */
    @Log(title = "更新数据字典", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新数据字典", description = "更新数据字典信息")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody DataDictionaryDTO dictionaryDto) {
        dictionaryDto.setId(id);
        return service.update(dictionaryDto);
    }
    
    /**
     * 导出数据字典
     */
    @Log(title = "导出数据字典", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出数据字典", description = "导出数据字典")
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        return service.exportExcel(exchange, query);
    }
    
    /**
     * 导入数据字典
     */
    @Log(title = "导入数据字典", businessType = BusinessType.IMPORT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入数据字典", description = "导入数据字典")
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@RequestPart("file") Mono<FilePart> filePart) {
        return filePart.flatMap(part -> part.content().reduce(new byte[0], (bytes, dataBuffer) -> {
            byte[] newBytes = new byte[bytes.length + dataBuffer.readableByteCount()];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            dataBuffer.read(newBytes, bytes.length, dataBuffer.readableByteCount());
            return newBytes;
        }).flatMap(fileData -> service.importExcel(fileData)));
    }
    
    /**
     * 启用/停用数据字典
     */
    @Log(title = "启用/停用数据字典", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启用/停用数据字典", description = "启用或停用指定的数据字典")
    @Parameters({@Parameter(name = "id", description = "数据字典ID", required = true), @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)})
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }
}