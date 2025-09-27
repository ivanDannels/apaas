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
package org.apaas.integration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.service.IntegrationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/integrations")
public class IntegrationController extends ReactiveBaseController<IntegrationEntity, Long, IntegrationService> {
    
    public IntegrationController(IntegrationService service) {
        super(service);
    }
    
    @Log(title = "获取所有集成", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping
    public Flux<IntegrationEntity> getAllIntegrations() {
        return service.findAll();
    }
    
    @Log(title = "获取集成详情", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping("/{id}")
    public Mono<IntegrationEntity> getIntegrationById(@PathVariable Long id) {
        return service.findById(id);
    }
    
    @Log(title = "创建集成", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping
    public Mono<IntegrationEntity> createIntegration(@RequestBody IntegrationEntity integration) {
        return service.save(integration);
    }
    
    @Log(title = "更新集成", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PutMapping("/{id}")
    public Mono<IntegrationEntity> updateIntegration(@Parameter(description = "集成ID", required = true) @PathVariable Long id, @RequestBody IntegrationEntity integration) {
        integration.setId(id);
        return service.save(integration);
    }
    
    @Log(title = "删除集成", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteIntegration(@PathVariable Long id) {
        return service.deleteById(id);
    }
    
    /**
     * 启用集成
     */
    @Log(title = "启用集成", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/enable")
    @Operation(summary = "启用集成", description = "启用指定的集成")
    public Mono<Void> enableIntegration(@Parameter(description = "集成ID", required = true) @PathVariable Long id) {
        return service.enableIntegration(id);
    }
    
    /**
     * 禁用集成
     */
    @Log(title = "禁用集成", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用集成", description = "禁用指定的集成")
    public Mono<Void> disableIntegration(@Parameter(description = "集成ID", required = true) @PathVariable Long id) {
        return service.disableIntegration(id);
    }
    
    /**
     * 测试集成连接
     */
    @Log(title = "测试集成连接", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/test")
    @Operation(summary = "测试集成连接", description = "测试指定集成的连接是否正常")
    public Mono<Boolean> testConnection(@Parameter(description = "集成ID", required = true) @PathVariable Long id) {
        return service.testConnection(id);
    }
    
    /**
     * 根据类型查询集成
     */
    @Log(title = "根据类型查询集成", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping("/type/{type}")
    @Operation(summary = "根据类型查询集成", description = "根据类型查询集成列表")
    public Flux<IntegrationEntity> findByType(@Parameter(description = "集成类型", required = true) @PathVariable String type) {
        return service.findByType(type);
    }
}