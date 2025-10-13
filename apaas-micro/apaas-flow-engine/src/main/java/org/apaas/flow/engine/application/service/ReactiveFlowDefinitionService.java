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

import org.apaas.core.query.PageResult;
import org.apaas.domain.application.service.ApplicationService;
import org.apaas.flow.engine.application.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.domain.model.FlowDefinition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义服务接口
 */
public interface ReactiveFlowDefinitionService extends ApplicationService<FlowDefinitionDTO, Long> {
    
    /**
     * 根据流程定义键获取流程定义
     *
     * @param flowKey 流程定义键
     * @return 流程定义信息
     */
    Mono<FlowDefinitionDTO> getFlowDefinitionByKey(String flowKey);
    
    /**
     * 分页获取流程定义列表
     *
     * @param pageable 分页参数
     * @return 流程定义分页结果
     */
    Mono<PageResult<FlowDefinitionDTO>> getFlowDefinitionPage(Pageable pageable);
    
    /**
     * 添加流程定义
     *
     * @param flowDefinition 流程定义信息
     * @return 添加结果
     */
    Mono<FlowDefinitionDTO> addFlowDefinition(FlowDefinitionDTO flowDefinitionDTO);
    
    /**
     * 更新流程定义
     *
     * @param flowDefinition 流程定义信息
     * @return 更新结果
     */
    Mono<FlowDefinitionDTO> updateFlowDefinition(FlowDefinitionDTO flowDefinitionDTO);
    
    /**
     * 删除流程定义
     *
     * @param id 流程定义ID
     * @return 删除结果
     */
    Mono<Void> deleteFlowDefinition(Long id);
    
    /**
     * 部署流程定义
     *
     * @param id 流程定义ID
     * @return 部署结果
     */
    Mono<FlowDefinitionDTO> deployFlowDefinition(Long id);
    
    /**
     * 挂起流程定义
     *
     * @param id 流程定义ID
     * @return 挂起结果
     */
    Mono<Void> suspendFlowDefinition(Long id);
    
    /**
     * 激活流程定义
     *
     * @param id 流程定义ID
     * @return 激活结果
     */
    Mono<Void> activateFlowDefinition(Long id);
    
    Mono<FlowDefinitionDTO> disableFlowDefinition(Long id);
    
    Flux<FlowDefinitionDTO> getVersionsByCode(String code);
}