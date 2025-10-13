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
import org.apaas.flow.engine.domain.model.FlowDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlowDefinitionRepository extends BaseRepository<FlowDefinition, Long> {
    
    /**
     * 根据流程定义键获取流程定义
     *
     * @param flowKey 流程定义键
     * @return 流程定义信息
     */
    Mono<FlowDefinition> findByFlowKey(String flowKey);
    
    /**
     * 根据流程编码获取所有版本
     *
     * @param code 流程编码
     * @return 流程定义列表
     */
    Flux<FlowDefinition> findByCode(String code);
    
    /**
     * 更新默认版本标识
     *
     * @param code 流程编码
     * @param isDefault 是否默认版本
     * @return 更新结果
     */
    Mono<Void> updateIsDefaultByCode(String code, boolean isDefault);
}