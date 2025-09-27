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
package org.apaas.auth.factory;

import org.apaas.auth.entity.UserAggregate;
import org.apaas.auth.entity.Username;
import org.apaas.domain.factory.EntityFactory;

/**
 * 用户工厂类
 * 用于创建复杂的用户对象，封装对象创建逻辑
 *
 * @author ivan
 */
public class UserFactory implements EntityFactory<UserAggregate, Long> {
    
    /**
     * 创建用户聚合根
     *
     * @param id 用户ID
     * @param args 创建参数（用户名，密码，昵称）
     * @return 用户聚合根
     */
    @Override
    public UserAggregate create(Long id, Object... args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("创建用户需要用户名、密码和昵称参数");
        }
        
        String username = (String) args[0];
        String password = (String) args[1];
        String nickname = (String) args[2];
        
        UserAggregate user = UserAggregate.builder()
                .id(id)
                .username(username)
                .password(password)
                .nickname(nickname)
                .status(0) // 默认状态为启用
                .deleted(0) // 默认未删除
                .build();
        
        return user;
    }
    
    /**
     * 创建用户聚合根（无参）
     *
     * @return 用户聚合根
     */
    @Override
    public UserAggregate create() {
        return UserAggregate.builder()
                .status(0) // 默认状态为启用
                .deleted(0) // 默认未删除
                .build();
    }
    
    /**
     * 从原型创建用户聚合根
     *
     * @param prototype 原型对象
     * @return 用户聚合根
     */
    @Override
    public UserAggregate createFrom(UserAggregate prototype) {
        UserAggregate user = UserAggregate.builder()
                .id(prototype.getId())
                .username(prototype.getUsername())
                .password(prototype.getPassword())
                .nickname(prototype.getNickname())
                .phone(prototype.getPhone())
                .email(prototype.getEmail())
                .avatar(prototype.getAvatar())
                .gender(prototype.getGender())
                .status(prototype.getStatus())
                .loginIp(prototype.getLoginIp())
                .loginDate(prototype.getLoginDate())
                .logoutDate(prototype.getLogoutDate())
                .deleted(prototype.getDeleted())
                .build();
        
        // 复制关联对象
        user.setRoles(prototype.getRoles());
        user.setOrganizations(prototype.getOrganizations());
        user.setPositions(prototype.getPositions());
        user.setTenants(prototype.getTenants());
        
        return user;
    }
    
    /**
     * 创建管理员用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 管理员用户聚合根
     */
    public UserAggregate createAdminUser(String username, String password) {
        Username user = new Username(username);
        if (!user.isAdmin()) {
            throw new IllegalArgumentException("只能创建管理员用户");
        }
        
        return UserAggregate.builder()
                .username(username)
                .password(password)
                .nickname("管理员")
                .status(0) // 默认状态为启用
                .deleted(0) // 默认未删除
                .build();
    }
}