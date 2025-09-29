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
package org.apaas.auth.service;

import org.apaas.auth.entity.User;
import org.apaas.auth.entity.User;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.application.service.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用户应用服务接口
 * 处理用户相关的应用层逻辑
 *
 * @author ivan
 */
public interface UserApplicationService extends ApplicationService<User, Long> {
    
    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<User> getUserByUsername(String username);
    
    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    Mono<User> addUser(User user);
    
    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 更新结果
     */
    Mono<User> updateUser(User user);
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    Mono<Boolean> deleteUser(Long id);
    
    /**
     * 重置用户密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    Mono<Boolean> resetPassword(Long id, String newPassword);
    
    /**
     * 修改用户状态
     *
     * @param id 用户ID
     * @param status 状态
     * @return 修改结果
     */
    Mono<Boolean> changeStatus(Long id, Integer status);
    
    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Flux<String> getUserPermissions(Long userId);
    
    /**
     * 记录用户登录信息
     *
     * @param userId 用户ID
     * @param loginIp 登录IP
     * @return 记录结果
     */
    Mono<Void> recordLoginInfo(Long userId, String loginIp);
    
    Mono<User> login(String username, String password);
    
    Mono<Boolean> updatePassword(String oldPassword, String newPassword);
    
    Mono<User> getCurrentUser();
    
    /**
     * 用户登出
     *
     * @param userId 用户ID
     * @return 登出结果
     */
    Mono<Boolean> logout(Long userId);
    
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    Mono<PageResult<User>> selectPage(Query query);
}