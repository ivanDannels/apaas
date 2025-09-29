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
package org.apaas.flow.engine.repository.reactive;

import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例仓库接口
 */
@Repository
public interface ReactiveFlowInstanceRepository extends ReactiveBaseRepository<FlowInstance, Long> {
    
    /**
     * 根据业务键查询流程实例
     *
     * @param businessKey 业务键
     * @return 流程实例列表
     */
    Mono<FlowInstance> findByBusinessKey(String businessKey);
    
    /**
     * 根据流程定义ID查询流程实例
     *
     * @param flowDefinitionId 流程定义ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByFlowDefinitionId(Long flowDefinitionId);
    
    /**
     * 根据启动人ID查询流程实例
     *
     * @param starterId 启动人ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByStarterId(Long starterId);
}