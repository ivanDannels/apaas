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
import org.apaas.form.engine.application.service.ReactiveFormDataService;
import org.apaas.form.engine.entity.FormData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单数据控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-data")
@Tag(name = "响应式表单数据管理", description = "响应式表单数据相关操作")
public class ReactiveFormDataController {
    
    protected final ReactiveFormDataService service;
    
    public ReactiveFormDataController(ReactiveFormDataService service) {
        this.service = service;
    }
    
    /**
     * 根据表单编码获取表单数据列表
     */
    @GetMapping(value = "/form/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单编码获取表单数据列表", description = "查询指定表单的所有数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Flux<FormData> getByFormCode(@PathVariable String formCode) {
        return service.getFormDataByFormCode(formCode);
    }
    
    /**
     * 根据表单编码和版本获取表单数据列表
     */
    @GetMapping(value = "/form/{formCode}/version/{version}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单编码和版本获取表单数据列表", description = "查询指定表单版本的所有数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    @Parameter(name = "version", description = "表单版本", required = true)
    public Flux<FormData> getByFormCodeAndVersion(@PathVariable String formCode, @PathVariable String version) {
        return service.getFormDataByFormCodeAndVersion(formCode, version);
    }
    
    /**
     * 根据业务键获取表单数据
     */
    @GetMapping(value = "/business-key/{businessKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据业务键获取表单数据", description = "查询指定业务键的表单数据")
    @Parameter(name = "businessKey", description = "业务键", required = true)
    public Mono<FormData> getByBusinessKey(@PathVariable String businessKey) {
        return service.getFormDataByBusinessKey(businessKey);
    }
    
    /**
     * 获取表单数据详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单数据详情", description = "根据ID查询表单数据信息")
    @Parameter(name = "id", description = "表单数据ID", required = true)
    public Mono<FormData> getById(@PathVariable Long id) {
        return service.findById(id);
    }
    
    /**
     * 保存表单数据（草稿状态）
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "保存表单数据", description = "保存表单数据为草稿状态")
    public Mono<Long> save(@RequestBody FormData formData) {
        return service.saveFormData(formData);
    }
    
    /**
     * 提交表单数据
     */
    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "提交表单数据", description = "提交表单数据为已提交状态")
    public Mono<Long> submit(@RequestBody FormData formData) {
        return service.submitFormData(formData);
    }
    
    /**
     * 删除表单数据
     */
    @DeleteMapping(value = "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单数据", description = "批量删除表单数据")
    @Parameter(name = "ids", description = "表单数据ID数组", required = true)
    public Mono<Boolean> remove(@PathVariable Long[] ids) {
        return service.deleteFormData(ids);
    }
    
    /**
     * 批量导入表单数据
     */
    @PostMapping(value = "/import/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量导入表单数据", description = "从JSON数据导入表单数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Mono<Boolean> importData(@PathVariable String formCode, @RequestBody String dataJson) {
        return service.importFormData(formCode, dataJson);
    }
    
    /**
     * 获取表单数据统计
     */
    @GetMapping(value = "/statistics/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单数据统计", description = "统计表单数据信息")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Mono<Object> getStatistics(@PathVariable String formCode) {
        return service.getFormDataStatistics(formCode);
    }
    
    /**
     * 验证表单数据
     */
    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "验证表单数据", description = "验证表单数据是否有效")
    public Mono<Boolean> validate(@RequestBody FormData formData) {
        return service.validateFormData(formData);
    }
}