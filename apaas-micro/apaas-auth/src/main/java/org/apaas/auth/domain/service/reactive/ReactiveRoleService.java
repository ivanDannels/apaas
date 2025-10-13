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

import org.apaas.auth.domain.entity.Role;
import org.apaas.domain.application.service.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色服务接��?
 * @author ivan
 */
public interface ReactiveRoleService extends ApplicationService<Role, Long> {
    
    /**
     * 根据角色名获取角��?
     *
     * @param roleName 角色��?
     * @return 角色信息
     */
    Mono<Role> getRoleByRoleName(String roleName);
    
    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return 添加结果
     */
    Mono<Role> addRole(Role role);
    
    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return 更新结果
     */
    Mono<Role> updateRole(Role role);
    
    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 删除结果
     */
    Mono<Void> deleteRole(Long id);
    
    /**
     * 获取角色权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    Flux<String> getRolePermissions(Long roleId);
    
    /**
     * 为角色分配权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 分配结果
     */
    Mono<Void> assignPermissionsToRole(Long roleId, Flux<Long> permissionIds);
    
    Flux<Role> getUserRoles(Long userId);
    
    Mono<Boolean> assignRoles(Long userId, Long[] roleIds);
    
    Flux<Role> getAllRoles();
    
    Mono<Boolean> changeStatus(Long id, Integer status);
}
