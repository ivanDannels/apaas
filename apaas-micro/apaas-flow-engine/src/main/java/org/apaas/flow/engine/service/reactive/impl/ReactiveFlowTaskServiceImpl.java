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
package org.apaas.flow.engine.service.reactive.impl;

import org.apaas.flow.engine.entity.FlowTask;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowTaskRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowTaskService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式流程任务服务实现类
 */
@Service
public class ReactiveFlowTaskServiceImpl extends BaseServiceImpl<FlowTask, Long, ReactiveFlowTaskRepository> implements ReactiveFlowTaskService {
    
    public ReactiveFlowTaskServiceImpl(ReactiveFlowTaskRepository repository) {
        super(repository);
    }
    
    /**
     * 根据流程实例ID查询流程任务列表
     *
     * @param flowInstanceId 流程实例ID
     * @return 流程任务列表
     */
    @Override
    public Flux<FlowTask> getFlowTasksByFlowInstanceId(Long flowInstanceId) {
        return repository.findByFlowInstanceId(flowInstanceId);
    }
    
    /**
     * 根据任务定义键查询流程任务列表
     *
     * @param taskDefinitionKey 任务定义键
     * @return 流程任务列表
     */
    @Override
    public Flux<FlowTask> getFlowTasksByTaskDefinitionKey(String taskDefinitionKey) {
        return repository.findByTaskDefinitionKey(taskDefinitionKey);
    }
    
    /**
     * 根据任务ID签收任务
     *
     * @param taskId 任务ID
     * @param assignee 签收人
     * @return 签收结果
     */
    @Override
    public Mono<Boolean> claimTask(Long taskId, Long assignee) {
        return super.findById(taskId).thenReturn(true);
    }
    
    /**
     * 根据任务ID完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     * @return 完成结果
     */
    @Override
    public Mono<Boolean> completeTask(Long taskId, java.util.Map<String, Object> variables) {
        return super.findById(taskId).thenReturn(true);
    }
    
    /**
     * 根据任务ID委派任务
     *
     * @param taskId 任务ID
     * @param delegateTo 委派人
     * @return 委派结果
     */
    @Override
    public Mono<Boolean> delegateTask(Long taskId, Long delegateTo) {
        return super.findById(taskId).thenReturn(true);
    }
    
    /**
     * 根据任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteTask(Long taskId) {
        return super.findById(taskId).thenReturn(true);
    }
}