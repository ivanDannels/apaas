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
import org.apaas.flow.execution.domain.model.WorkflowTask;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskManagementService extends ApplicationService<WorkflowTask, Long> {
    
    /**
     * 查询用户任务列表
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    Mono<Result<Flux<WorkflowTask>>> getUserTasks(Long userId);
    
    /**
     * 领取任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    Mono<Result<WorkflowTask>> claimTask(Long taskId, Long userId);
    
    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    Mono<Result<WorkflowTask>> completeTask(Long taskId, Long userId);
    
    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param fromUserId 原处理人
     * @param toUserId 新处理人
     * @return 任务
     */
    Mono<Result<WorkflowTask>> transferTask(Long taskId, Long fromUserId, Long toUserId);
    
}