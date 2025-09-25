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
package org.apaas.flow.execution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.apaas.flow.execution.repository.FlowInstanceRepository;
import org.apaas.flow.execution.service.FlowRuntimeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FlowRuntimeServiceImpl extends BaseServiceImpl<FlowInstance, Long, FlowInstanceRepository> implements FlowRuntimeService {
    
    public FlowRuntimeServiceImpl(FlowInstanceRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<Result<FlowInstance>> startProcessInstance(Long processId, String businessKey, Long starter) {
        // 实现流程启动逻辑
        return Mono.empty();
    }
    
    @Override
    public Mono<Result<FlowInstance>> completeActivityInstance(Long activityInstanceId, Long userId) {
        // 实现活动完成逻辑
        return Mono.empty();
    }
    
    @Override
    public Mono<Result<FlowInstance>> terminateFlowInstance(Long flowInstanceId, Long userId) {
        // 实现流程终止逻辑
        return Mono.empty();
    }
}