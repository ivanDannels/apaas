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
package org.apaas.flow.execution;

import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.apaas.flow.execution.service.TaskManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskManagementServiceTest {
    
    private TaskManagementService taskManagementService;
    
    @Test
    void getUserTasks() {
        // TODO: 实现查询用户任务列表测试
        WorkflowTask task = WorkflowTask.builder().build();
        when(taskManagementService.getUserTasks(any())).thenReturn(Mono.just(Result.success(Flux.just(task))));
        
        StepVerifier.create(taskManagementService.getUserTasks(1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
    @Test
    void claimTask() {
        // TODO: 实现领取任务测试
        WorkflowTask task = WorkflowTask.builder().build();
        when(taskManagementService.claimTask(any(), any())).thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.claimTask(1L, 1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
    @Test
    void completeTask() {
        // TODO: 实现完成任务测试
        WorkflowTask task = WorkflowTask.builder().build();
        when(taskManagementService.completeTask(any(), any())).thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.completeTask(1L, 1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
    @Test
    void transferTask() {
        // TODO: 实现转办任务测试
        WorkflowTask task = WorkflowTask.builder().build();
        when(taskManagementService.transferTask(any(), any(), any())).thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.transferTask(1L, 1L, 2L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
}