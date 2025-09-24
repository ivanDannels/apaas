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

import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowInstanceRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务实现类
 */
@Service
public class ReactiveFlowInstanceServiceImpl extends BaseServiceImpl<FlowInstance, Long, ReactiveFlowInstanceRepository> implements ReactiveFlowInstanceService {
    
    public ReactiveFlowInstanceServiceImpl(ReactiveFlowInstanceRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<FlowInstance> getFlowInstanceById(Long instanceId) {
        return repository.findById(instanceId);
    }
    
    @Override
    public Mono<FlowInstance> startInstance(StartInstanceDTO startInstanceDTO) {
        return null;
    }
    
    @Override
    public Mono<FlowInstance> terminateInstance(Long id) {
        return null;
    }
    
    @Override
    public Mono<FlowInstance> suspendInstance(Long id) {
        return null;
    }
    
    @Override
    public Mono<FlowInstance> resumeInstance(Long id) {
        return null;
    }
    
    @Override
    public Flux<Object> getInstanceTasks(Long id) {
        return null;
    }
}