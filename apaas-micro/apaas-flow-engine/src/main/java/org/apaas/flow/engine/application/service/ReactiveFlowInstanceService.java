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
package org.apaas.flow.engine.application.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.flow.engine.application.dto.StartInstanceDTO;
import org.apaas.flow.engine.application.dto.FlowInstanceDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务接口
 */
public interface ReactiveFlowInstanceService extends ApplicationService<FlowInstanceDTO, Long> {
    
    /**
     * 根据流程实例ID获取流程实例
     *
     * @param instanceId 流程实例ID
     * @return 流程实例信息
     */
    Mono<FlowInstanceDTO> getFlowInstanceById(Long instanceId);
    
    Mono<FlowInstanceDTO> startInstance(StartInstanceDTO startInstanceDTO);
    
    Mono<FlowInstanceDTO> terminateInstance(Long id);
    
    Mono<FlowInstanceDTO> suspendInstance(Long id);
    
    Mono<FlowInstanceDTO> resumeInstance(Long id);
    
    Flux<FlowInstanceDTO> getInstanceTasks(Long id);
}