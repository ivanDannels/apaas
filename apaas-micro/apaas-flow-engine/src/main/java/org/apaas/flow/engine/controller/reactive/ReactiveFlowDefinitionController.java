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
package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.core.query.Query;
import org.apaas.domain.interfaces.rest.ReactiveBaseController;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.service.reactive.ReactiveFlowDefinitionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/flow/definition")
@Tag(name = "响应式流程定义管理", description = "响应式流程定义CRUD及部署操作")
public class ReactiveFlowDefinitionController extends ReactiveBaseController<FlowDefinition, Long, ReactiveFlowDefinitionService> {
    
    public ReactiveFlowDefinitionController(ReactiveFlowDefinitionService service) {
        super(service);
    }
    
    /**
     * 部署流程定义
     */
    @Log(title = "部署流程定义", businessType = BusinessType.FLOW_START, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/deploy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "部署流程定义", description = "发布流程定义为可用状态")
    public Mono<FlowDefinition> deploy(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return service.deployFlowDefinition(id);
    }
    
    /**
     * 停用流程定义
     */
    @Log(title = "停用流程定义", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用流程定义", description = "将流程定义设置为停用状态")
    public Mono<FlowDefinition> disable(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return service.disableFlowDefinition(id);
    }
    
    /**
     * 获取流程版本列表
     */
    @Log(title = "获取流程版本列表", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程版本列表", description = "根据流程编码查询所有版本")
    public Flux<FlowDefinition> getVersionsByCode(@Parameter(description = "流程编码", required = true) @PathVariable String code) {
        return service.getVersionsByCode(code);
    }
}