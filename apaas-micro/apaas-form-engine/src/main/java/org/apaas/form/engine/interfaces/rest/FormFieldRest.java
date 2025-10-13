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
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.interfaces.rest.BaseRest;
import org.apaas.form.engine.domain.model.FormField;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/form-fields")
@Tag(name = "表单字段管理", description = "表单字段管理API")
public class FormFieldRest extends BaseRest<FormField, Long, ReactiveFormFieldService> {
    
    public FormFieldRest(ReactiveFormFieldService service) {
        super(service);
    }
    
    /**
     * 分页查询表单字段列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单字段列表", description = "分页查询表单字段列表")
    public Mono<org.apaas.core.query.PageResult<FormField>> selectPage(@RequestBody org.apaas.core.query.Query query) {
        // 从查询条件中提取表单ID
        Long formId = null;
        if (query.getConditions() != null) {
            for (org.apaas.core.query.Condition condition : query.getConditions()) {
                if ("formId".equals(condition.getField())) {
                    formId = Long.valueOf(condition.getValue().toString());
                    break;
                }
            }
        }
        
        // 获取分页信息
        org.apaas.core.query.PageRequest pageRequest = query.getPageRequest();
        Pageable pageable = Pageable.ofSize(pageRequest.getPageSize()).withPage(pageRequest.getPageNumber() - 1);
        
        return service.selectPage(formId, pageable);
    }
    
    /**
     * 根据表单ID查询字段列表
     */
    @GetMapping(value = "/form/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询字段列表", description = "根据表单ID查询字段列表")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormField> selectByFormId(@PathVariable Long formId) {
        return service.selectByFormId(formId);
    }
    
    /**
     * 根据表单ID和字段类型查询字段列表
     */
    @GetMapping(value = "/form/{formId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和字段类型查询字段列表", description = "根据表单ID和字段类型查询字段列表")
    @Parameters({@Parameter(name = "formId", description = "表单ID", required = true), @Parameter(name = "type", description = "字段类型", required = true)})
    public Flux<FormField> selectByFormIdAndType(@PathVariable Long formId, @PathVariable Integer type) {
        return service.selectByFormIdAndType(formId, type);
    }
    
    /**
     * 根据表单ID和分组名称查询字段列表
     */
    @GetMapping(value = "/form/{formId}/group/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和分组名称查询字段列表", description = "根据表单ID和分组名称查询字段列表")
    @Parameters({@Parameter(name = "formId", description = "表单ID", required = true), @Parameter(name = "groupName", description = "分组名称", required = true)})
    public Flux<FormField> selectByFormIdAndGroupName(@PathVariable Long formId, @PathVariable String groupName) {
        return service.selectByFormIdAndGroupName(formId, groupName);
    }
    
    /**
     * 创建表单字段
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单字段", description = "创建新的表单字段")
    public Mono<FormField> create(@RequestBody FormField formField) {
        return service.create(formField);
    }
    
    /**
     * 更新表单字段
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单字段", description = "更新表单字段信息")
    @Parameter(name = "id", description = "字段ID", required = true)
    public Mono<FormField> update(@PathVariable Long id, @RequestBody FormField formField) {
        formField.setId(id);
        return service.update(formField);
    }
    
    /**
     * 删除表单字段
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单字段", description = "删除表单字段")
    @Parameter(name = "id", description = "字段ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
    
    /**
     * 批量创建表单字段
     */
    @PostMapping(value = "/batch/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量创建表单字段", description = "批量创建表单字段")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormField> batchCreate(@PathVariable Long formId, @RequestBody Flux<FormField> formFields) {
        return service.batchCreate(formId, formFields);
    }
}