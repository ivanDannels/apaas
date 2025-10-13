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
package org.apaas.flow.execution.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.flow.execution.application.dto.Result;
import org.apaas.flow.execution.domain.model.FlowInstance;
import reactor.core.publisher.Mono;

public interface FlowRuntimeService extends ApplicationService<FlowInstance, Long> {
    
    /**
     * 启动流程实例
     *
     * @param processId 流程定义ID
     * @param businessKey 业务主键
     * @param starter 启动人
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> startProcessInstance(Long processId, String businessKey, Long starter);
    
    /**
     * 完成活动实例
     *
     * @param activityInstanceId 活动实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> completeActivityInstance(Long activityInstanceId, Long userId);
    
    /**
     * 终止流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> terminateFlowInstance(Long flowInstanceId, Long userId);
}