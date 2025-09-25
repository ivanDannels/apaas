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
package org.apaas.flow.execution.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.apaas.flow.execution.service.FlowRuntimeService;
import org.apaas.flow.execution.service.TaskManagementService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/flow-execution")
@RequiredArgsConstructor
public class FlowExecutionController {
    
    private final FlowRuntimeService flowRuntimeService;
    private final TaskManagementService taskManagementService;
    
    /**
     * 启动流程实例
     *
     * @param processId 流程定义ID
     * @param businessKey 业务主键
     * @param starter 启动人
     * @return 流程实例
     */
    @PostMapping("/instances/start")
    public Mono<Result<FlowInstance>> startProcessInstance(@RequestParam Long processId, @RequestParam String businessKey, @RequestHeader("user-id") Long starter) {
        return flowRuntimeService.startProcessInstance(processId, businessKey, starter).onErrorReturn(Result.error("启动流程实例失败"));
    }
    
    /**
     * 完成活动实例
     *
     * @param activityInstanceId 活动实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    @PostMapping("/activities/{activityInstanceId}/complete")
    public Mono<Result<FlowInstance>> completeActivityInstance(@PathVariable Long activityInstanceId, @RequestHeader("user-id") Long userId) {
        return flowRuntimeService.completeActivityInstance(activityInstanceId, userId).onErrorReturn(Result.error("完成活动实例失败"));
    }
    
    /**
     * 终止流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    @PostMapping("/instances/{flowInstanceId}/terminate")
    public Mono<Result<FlowInstance>> terminateFlowInstance(@PathVariable Long flowInstanceId, @RequestHeader("user-id") Long userId) {
        return flowRuntimeService.terminateFlowInstance(flowInstanceId, userId).onErrorReturn(Result.error("终止流程实例失败"));
    }
    
    /**
     * 查询用户任务列表
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    @GetMapping("/tasks")
    public Mono<Result<Flux<WorkflowTask>>> getUserTasks(@RequestHeader("user-id") Long userId) {
        return taskManagementService.getUserTasks(userId);
    }
    
    /**
     * 领取任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    @PostMapping("/tasks/{taskId}/claim")
    public Mono<Result<WorkflowTask>> claimTask(@PathVariable Long taskId, @RequestHeader("user-id") Long userId) {
        return taskManagementService.claimTask(taskId, userId).onErrorReturn(Result.error("领取任务失败"));
    }
    
    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    @PostMapping("/tasks/{taskId}/complete")
    public Mono<Result<WorkflowTask>> completeTask(@PathVariable Long taskId, @RequestHeader("user-id") Long userId) {
        return taskManagementService.completeTask(taskId, userId).onErrorReturn(Result.error("完成任务失败"));
    }
    
    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param fromUserId 原处理人
     * @param toUserId 新处理人
     * @return 任务
     */
    @PostMapping("/tasks/{taskId}/transfer")
    public Mono<Result<WorkflowTask>> transferTask(@PathVariable Long taskId, @RequestParam Long fromUserId, @RequestParam Long toUserId) {
        return taskManagementService.transferTask(taskId, fromUserId, toUserId).onErrorReturn(Result.error("转办任务失败"));
    }
}