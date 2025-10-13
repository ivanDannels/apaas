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
package org.apaas.flow.engine.infrastructure.repository;

import org.apaas.flow.engine.application.assembler.FlowInstanceAssembler;
import org.apaas.flow.engine.application.dto.StartInstanceDTO;
import org.apaas.flow.engine.application.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.model.FlowInstance;
import org.apaas.flow.engine.domain.repository.ReactiveFlowInstanceRepository;
import org.apaas.flow.engine.application.service.ReactiveFlowInstanceService;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 响应式流程实例服务实现类
 */
@Service
public class ReactiveFlowInstanceServiceImpl extends AbstractApplicationService<FlowInstanceDTO, Long, ReactiveFlowInstanceRepository> implements ReactiveFlowInstanceService {
    
    private final FlowInstanceAssembler flowInstanceAssembler = FlowInstanceAssembler.INSTANCE;
    
    public ReactiveFlowInstanceServiceImpl(ReactiveFlowInstanceRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<FlowInstanceDTO> getFlowInstanceById(Long instanceId) {
        return repository.findById(instanceId).map(flowInstanceAssembler::toDTO);
    }
    
    @Override
    public Mono<FlowInstanceDTO> startInstance(StartInstanceDTO startInstanceDTO) {
        // 创建新的流程实例
        FlowInstance instance = FlowInstance.builder().definitionId(startInstanceDTO.getDefinitionId()).title(startInstanceDTO.getTitle()).startUserId(startInstanceDTO.getStarterId()).variables(startInstanceDTO.getVariables() != null ? startInstanceDTO.getVariables().toString() : null).status(1) // 1-运行中
                .startTime(LocalDateTime.now()).createdTime(LocalDateTime.now()).updatedTime(LocalDateTime.now()).build();
        
        // 保存流程实例
        return repository.save(instance).map(flowInstanceAssembler::toDTO);
    }
    
    @Override
    public Mono<FlowInstanceDTO> terminateInstance(Long id) {
        return repository.findById(id).flatMap(instance -> {
            instance.setStatus(3); // 3-已终止
            instance.setEndTime(LocalDateTime.now());
            instance.setUpdatedTime(LocalDateTime.now());
            return repository.save(instance);
        }).map(flowInstanceAssembler::toDTO);
    }
    
    @Override
    public Mono<FlowInstanceDTO> suspendInstance(Long id) {
        return repository.findById(id).flatMap(instance -> {
            instance.setStatus(2); // 2-已暂停
            instance.setUpdatedTime(LocalDateTime.now());
            return repository.save(instance);
        }).map(flowInstanceAssembler::toDTO);
    }
    
    @Override
    public Mono<FlowInstanceDTO> resumeInstance(Long id) {
        return repository.findById(id).flatMap(instance -> {
            instance.setStatus(1); // 1-运行中
            instance.setUpdatedTime(LocalDateTime.now());
            return repository.save(instance);
        }).map(flowInstanceAssembler::toDTO);
    }
    
    @Override
    public Flux<FlowInstanceDTO> getInstanceTasks(Long id) {
        // 这里需要根据流程实例ID查询相关的任务记录
        // 暂时返回空的Flux，实际开发中需要实现具体的查询逻辑
        return Flux.empty();
    }
}