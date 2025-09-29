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
package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Application;
import org.apaas.domain.application.service.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
public interface ReactiveApplicationService extends ApplicationService<Application, Long> {
    
    /**
     * 根据应用名称获取应用
     *
     * @param name 应用名称
     * @return 应用信息
     */
    Mono<Application> getApplicationByName(String name);
    
    /**
     * 根据应用编码获取应用
     *
     * @param code 应用编码
     * @return 应用信息
     */
    Mono<Application> getApplicationByCode(String code);
    
    /**
     * 获取所有应用列表
     *
     * @return 应用列表
     */
    Flux<Application> getAllApplications();
    
    /**
     * 更新应用状态
     *
     * @param id     应用ID
     * @param status 应用状态
     * @return 是否成功
     */
    Mono<Boolean> updateApplicationStatus(Long id, Integer status);
}