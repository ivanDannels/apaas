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
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.apaas.flow.execution.service.FlowRuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FlowRuntimeServiceTest {
    
    private FlowRuntimeService flowRuntimeService;
    
    @Test
    void startProcessInstance() {
        // TODO: 实现启动流程实例测试
        FlowInstance flowInstance = FlowInstance.builder().build();
        when(flowRuntimeService.startProcessInstance(any(), any(), any())).thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.startProcessInstance(1L, "key", 1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
    @Test
    void completeActivityInstance() {
        // TODO: 实现完成活动实例测试
        FlowInstance flowInstance = FlowInstance.builder().build();
        when(flowRuntimeService.completeActivityInstance(any(), any())).thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.completeActivityInstance(1L, 1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
    @Test
    void terminateFlowInstance() {
        // TODO: 实现终止流程实例测试
        FlowInstance flowInstance = FlowInstance.builder().build();
        when(flowRuntimeService.terminateFlowInstance(any(), any())).thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.terminateFlowInstance(1L, 1L)).expectNextMatches(result -> result.getCode() == 200).verifyComplete();
    }
    
}