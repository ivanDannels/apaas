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
package org.apaas.auth.domain.service.reactive;

import org.apaas.auth.domain.entity.Resource;
import org.apaas.domain.application.service.ApplicationService;

import reactor.core.publisher.Flux;

/**
 * 响应式资源服务接��?
 * @author ivan
 */
public interface ReactiveResourceService extends ApplicationService<Resource, Long> {
    
    /**
     * 根据资源名称查询资源
     *
     * @param name 资源名称
     * @return 资源
     */
    Flux<Resource> getResourceByName(String name);
    
    /**
     * 根据资源类型查询资源列表
     *
     * @param type 资源类型
     * @return 资源列表
     */
    Flux<Resource> getResourcesByType(Integer type);
    
    /**
     * 根据角色ID查询资源列表
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    Flux<Resource> getResourcesByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询资源列表
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    Flux<Resource> getResourcesByUserId(Long userId);
    
    /**
     * 获取资源树
     *
     * @param parentId 父级ID
     * @return 资源树
     */
    Flux<Resource> getResourceTree(Long parentId);
}
