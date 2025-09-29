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
package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.UserRole;
import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户角色关联仓库接口
 * @author ivan
 */
public interface ReactiveUserRoleRepository extends R2dbcRepository<UserRole, Long> {
    
    /**
     * 根据用户ID查询用户角色关联
     *
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    Flux<UserRole> findByUserId(Long userId);
    
    /**
     * 根据角色ID查询用户角色关联
     *
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    Flux<UserRole> findByRoleId(Long roleId);
    
    /**
     * 根据用户ID删除用户角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    Mono<Void> deleteByUserId(Long userId);
    
    /**
     * 根据角色ID删除用户角色关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    Mono<Void> deleteByRoleId(Long roleId);
}