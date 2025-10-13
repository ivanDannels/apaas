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
package org.apaas.flow.engine.domain.repository;

import org.apaas.domain.repository.BaseRepository;
import org.apaas.flow.engine.domain.model.FlowTask;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlowTaskRepository extends BaseRepository<FlowTask, Long> {
    
    /**
     * 根据流程实例ID获取任务列表
     *
     * @param instanceId 流程实例ID
     * @return 任务列表
     */
    Flux<FlowTask> findByInstanceId(Long instanceId);
    
    /**
     * 根据任务定义键获取任务
     *
     * @param taskKey 任务定义键
     * @return 任务
     */
    Mono<FlowTask> findByTaskKey(String taskKey);
    
    /**
     * 根据处理人ID获取任务列表
     *
     * @param assigneeId 处理人ID
     * @return 任务列表
     */
    Flux<FlowTask> findByAssigneeId(Long assigneeId);
    
    /**
     * 根据任务状态获取任务列表
     *
     * @param status 任务状态
     * @return 任务列表
     */
    Flux<FlowTask> findByStatus(Integer status);
    
    /**
     * 根据流程实例ID获取任务列表
     *
     * @param flowInstanceId 流程实例ID
     * @return 任务列表
     */
    Flux<FlowTask> findByFlowInstanceId(Long flowInstanceId);
    
    /**
     * 根据任务定义键获取任务列表
     *
     * @param taskDefinitionKey 任务定义键
     * @return 任务列表
     */
    Flux<FlowTask> findByTaskDefinitionKey(String taskDefinitionKey);
}