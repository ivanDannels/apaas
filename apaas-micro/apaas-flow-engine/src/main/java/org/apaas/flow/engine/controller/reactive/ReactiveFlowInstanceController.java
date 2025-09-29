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
import org.apaas.domain.interfaces.rest.ReactiveBaseController;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.entity.FlowTask;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/flow/instance")
@Tag(name = "响应式流程实例管理", description = "响应式流程实例生命周期管理")
public class ReactiveFlowInstanceController extends ReactiveBaseController<FlowInstance, Long, ReactiveFlowInstanceService> {
    
    public ReactiveFlowInstanceController(ReactiveFlowInstanceService service) {
        super(service);
    }
    
    /**
     * 启动流程实例
     */
    @Log(title = "启动流程实例", businessType = BusinessType.FLOW_START, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启动流程实例", description = "根据流程定义ID启动新的流程实例")
    public Mono<FlowInstance> startInstance(@RequestBody StartInstanceDTO startInstanceDTO) {
        return service.startInstance(startInstanceDTO);
    }
    
    /**
     * 终止流程实例
     */
    @Log(title = "终止流程实例", businessType = BusinessType.FLOW_TERMINATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/{id}/terminate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "终止流程实例", description = "将运行中的流程实例终止")
    public Mono<FlowInstance> terminateInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.terminateInstance(id);
    }
    
    /**
     * 暂停流程实例
     */
    @Log(title = "暂停流程实例", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/{id}/suspend", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "暂停流程实例", description = "暂停运行中的流程实例")
    public Mono<FlowInstance> suspendInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.suspendInstance(id);
    }
    
    /**
     * 恢复流程实例
     */
    @Log(title = "恢复流程实例", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "恢复流程实例", description = "恢复暂停的流程实例")
    public Mono<FlowInstance> resumeInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.resumeInstance(id);
    }
    
    /**
     * 获取流程实例的审批记录
     */
    @Log(title = "获取审批记录", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取审批记录", description = "查询流程实例的所有任务记录")
    public Flux<FlowTask> getInstanceTasks(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.getInstanceTasks(id);
    }
}