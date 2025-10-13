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
package org.apaas.auth.domain.service;

import org.apaas.auth.domain.entity.User;
import org.apaas.domain.domain.service.DomainService;
import reactor.core.publisher.Mono;

/**
 * 用户领域服务接口
 * 处理用户相关的复杂业务逻辑
 *
 * @author ivan
 */
public interface UserDomainService extends DomainService<User> {
    
    /**
     * 创建用户
     *
     * @param user 用户聚合根
     * @return 创建后的用户
     */
    Mono<User> createUser(User user);
    
    /**
     * 更新用户
     *
     * @param user 用户聚合根
     * @return 更新后的用户
     */
    Mono<User> updateUser(User user);
    
    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    Mono<Boolean> deleteUser(Long userId);
    
    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    Mono<Boolean> resetPassword(Long userId, String newPassword);
    
    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态：0-启用，1-禁用
     * @return 更新后的用户
     */
    Mono<User> changeStatus(Long userId, Integer status);
    
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录后的用户
     */
    Mono<User> login(String username, String password);
    
    /**
     * 用户登出
     *
     * @param userId 用户ID
     * @return 登出结果
     */
    Mono<Boolean> logout(Long userId);
}