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
package org.apaas.integration.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.integration.entity.IntegrationEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegrationService extends ApplicationService<IntegrationEntity, Long> {
    
    Flux<IntegrationEntity> findAll();
    Mono<IntegrationEntity> findById(Long id);
    Mono<IntegrationEntity> save(IntegrationEntity entity);
    Mono<Void> deleteById(Long id);
    
    /**
     * 启用集成
     */
    Mono<Void> enableIntegration(Long id);
    
    /**
     * 禁用集成
     */
    Mono<Void> disableIntegration(Long id);
    
    /**
     * 测试集成连接
     */
    Mono<Boolean> testConnection(Long id);
    
    /**
     * 根据类型查询集成
     */
    Flux<IntegrationEntity> findByType(String type);
}