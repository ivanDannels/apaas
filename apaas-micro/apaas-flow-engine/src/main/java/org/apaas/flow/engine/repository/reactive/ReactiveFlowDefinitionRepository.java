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

import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.domain.repository.ReactiveBaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义仓库接口
 */
public interface ReactiveFlowDefinitionRepository extends ReactiveBaseRepository<FlowDefinition, Long> {
    
    /**
     * 根据流程编码查询所有版本，按版本降序排序
     *
     * @param code 流程编码
     * @return 流程定义列表
     */
    Flux<FlowDefinition> findByCodeOrderByVersionDesc(String code);
    
    Mono<FlowDefinition> findByCodeAndVersion(String code, Integer version);
    
    Flux<FlowDefinition> updateIsDefaultByCode(String code, boolean b);
    
    Flux<Integer> findMaxVersionByCode(String code);
}