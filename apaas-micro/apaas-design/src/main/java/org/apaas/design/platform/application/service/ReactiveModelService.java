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
import org.apaas.design.platform.domain.model.Model;
import org.apaas.design.platform.application.dto.ModelDTO;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * 响应式模型服务接口
 * @author ivan
 */
public interface ReactiveModelService extends ApplicationService<Model, Long> {
    
    /**
     * 分页查询模型
     */
    Mono<PageResult<ModelDTO>> selectPage(Query query);
    
    /**
     * 创建模型
     */
    Mono<ModelDTO> create(ModelDTO modelDto);
    
    /**
     * 更新模型
     */
    Mono<ModelDTO> update(Long id, ModelDTO modelDto);
    
    /**
     * 根据ID获取模型
     */
    Mono<ModelDTO> findById(Long id);
    
    /**
     * 根据ID删除模型
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * 保存模型
     */
    Mono<ModelDTO> save(ModelDTO modelDto);
}