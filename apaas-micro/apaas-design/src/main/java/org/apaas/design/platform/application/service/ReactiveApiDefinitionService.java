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
package org.apaas.design.platform.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.design.platform.domain.model.ApiDefinition;
import org.apaas.design.platform.application.dto.ApiDefinitionDTO;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * 响应式API定义服务接口
 * @author ivan
 */
public interface ReactiveApiDefinitionService extends ApplicationService<ApiDefinition, Long> {
    
    /**
     * 分页查询API定义
     */
    Mono<PageResult<ApiDefinitionDTO>> selectPage(Query query);
    
    /**
     * 创建API定义
     */
    Mono<ApiDefinitionDTO> create(ApiDefinitionDTO apiDefinitionDto);
    
    /**
     * 更新API定义
     */
    Mono<ApiDefinitionDTO> update(Long id, ApiDefinitionDTO apiDefinitionDto);
    
    /**
     * 根据ID获取API定义
     */
    Mono<ApiDefinitionDTO> findById(Long id);
    
    /**
     * 根据ID删除API定义
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * 保存API定义
     */
    Mono<ApiDefinitionDTO> save(ApiDefinitionDTO apiDefinitionDto);
}