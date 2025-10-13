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
package org.apaas.form.engine.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.form.engine.application.dto.FormDefinitionDTO;
import org.apaas.form.engine.application.service.ReactiveFormDefinitionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单定义控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/form-definitions")
@Tag(name = "响应式表单定义管理", description = "响应式表单定义相关操作")
public class ReactiveFormDefinitionController {
    
    protected final ReactiveFormDefinitionService service;
    
    public ReactiveFormDefinitionController(ReactiveFormDefinitionService service) {
        this.service = service;
    }
    
    /**
     * 获取表单定义列表
     */
    @Log(title = "获取表单定义列表", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单定义列表", description = "分页查询表单定义信息")
    public Mono<PageResult<FormDefinitionDTO>> list(@RequestBody Query query) {
        return service.selectPage(query);
    }
    
    /**
     * 获取表单定义详情
     */
    @Log(title = "获取表单定义详情", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @Operation(summary = "获取表单定义详情", description = "根据ID查询表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    @GetMapping("/{id}")
    public Mono<FormDefinitionDTO> get(@PathVariable Long id) {
        return service.findById(id);
    }
    
    /**
     * 创建表单定义
     */
    @Log(title = "创建表单定义", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @Operation(summary = "创建表单定义", description = "新增表单定义信息")
    @PostMapping
    public Mono<FormDefinitionDTO> add(@RequestBody FormDefinitionDTO formDefinitionDTO) {
        return service.saveFormDefinition(formDefinitionDTO).then(Mono.empty());
    }
    
    /**
     * 更新表单定义
     */
    @Log(title = "更新表单定义", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单定义", description = "修改表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<FormDefinitionDTO> update(@PathVariable Long id, @RequestBody FormDefinitionDTO formDefinitionDTO) {
        return service.updateFormDefinition(formDefinitionDTO).then(Mono.empty());
    }
    
    /**
     * 发布表单定义
     */
    @Log(title = "发布表单定义", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/publish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发布表单定义", description = "发布表单定义为可用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Void> publish(@PathVariable Long id) {
        return service.publishFormDefinition(id).then(Mono.empty());
    }
    
    /**
     * 停用表单定义
     */
    @Log(title = "停用表单定义", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用表单定义", description = "将表单定义设置为停用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Void> disable(@PathVariable Long id) {
        return service.disableFormDefinition(id).then(Mono.empty());
    }
    
    /**
     * 获取表单版本列表
     */
    @Log(title = "获取表单版本列表", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单版本列表", description = "根据表单编码查询所有版本")
    @Parameter(name = "code", description = "表单编码", required = true)
    public Flux<FormDefinitionDTO> getVersionsByCode(@PathVariable String code) {
        return service.getVersionsByCode(code);
    }
    
    /**
     * 复制表单定义
     */
    @Log(title = "复制表单定义", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/copy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "复制表单定义", description = "复制现有表单定义创建新表单")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Long> copy(@PathVariable Long id, @RequestParam String newName) {
        return service.copyFormDefinition(id, newName);
    }
    
    /**
     * 导出表单定义
     */
    @Log(title = "导出表单定义", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/export/{id}")
    @Operation(summary = "导出表单定义", description = "导出表单定义为JSON文件")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<ResponseEntity<byte[]>> export(@PathVariable Long id) {
        return service.exportFormDefinition(id).map(data -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=form-" + id + ".json").contentType(MediaType.APPLICATION_JSON).body(data));
    }
    
    /**
     * 导入表单定义
     */
    @Log(title = "导入表单定义", businessType = BusinessType.IMPORT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入表单定义", description = "从JSON数据导入表单定义")
    public Mono<Long> importForm(@RequestBody byte[] data) {
        return service.importFormDefinition(data);
    }
}