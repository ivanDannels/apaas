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
package org.apaas.auth.service.reactive.impl;

import org.apaas.auth.entity.User;
import org.apaas.auth.entity.UserAggregate;
import org.apaas.auth.repository.reactive.ReactiveUserRepository;
import org.apaas.auth.service.UserApplicationService;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务实现适配器
 * 适配旧的接口到新的应用服务实现
 * @author ivan
 */
@Service
public class ReactiveUserServiceImpl extends AbstractApplicationService<User, Long, ReactiveUserRepository> implements ReactiveUserService {
    
    private final UserApplicationService userApplicationService;
    
    public ReactiveUserServiceImpl(ReactiveUserRepository repository, UserApplicationService userApplicationService) {
        super(repository);
        this.userApplicationService = userApplicationService;
    }
    
    @Override
    public Mono<User> getUserByUsername(String username) {
        return userApplicationService.getUserByUsername(username);
    }
    
    @Override
    public Mono<User> addUser(User user) {
        UserAggregate userAggregate = convertToUserAggregate(user);
        return userApplicationService.addUser(userAggregate);
    }
    
    @Override
    public Mono<User> updateUser(User user) {
        UserAggregate userAggregate = convertToUserAggregate(user);
        return userApplicationService.updateUser(userAggregate);
    }
    
    @Override
    public Mono<Void> deleteUser(Long id) {
        return userApplicationService.deleteUser(id).then();
    }
    
    @Override
    public Mono<Void> resetPassword(Long id, String newPassword) {
        return userApplicationService.resetPassword(id, newPassword).then();
    }
    
    @Override
    public Mono<Void> changeStatus(Long id, Integer status) {
        return userApplicationService.changeStatus(id, status).then();
    }
    
    @Override
    public Flux<String> getUserPermissions(Long userId) {
        return userApplicationService.getUserPermissions(userId);
    }
    
    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        return userApplicationService.recordLoginInfo(userId, loginIp);
    }
    
    @Override
    public Mono<User> login(String username, String password) {
        return userApplicationService.login(username, password);
    }
    
    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        return userApplicationService.updatePassword(oldPassword, newPassword);
    }
    
    @Override
    public Mono<User> getCurrentUser() {
        return userApplicationService.getCurrentUser();
    }
    
    @Override
    public Mono<Void> logout(Long userId) {
        return userApplicationService.logout(userId).then();
    }
    
    /**
     * 将UserAggregate转换为User
     */
    private User convertToUser(UserAggregate userAggregate) {
        if (userAggregate == null) {
            return null;
        }
        
        return User.builder().id(userAggregate.getId()).username(userAggregate.getUsername()).password(userAggregate.getPassword()).nickname(userAggregate.getNickname()).phone(userAggregate.getPhone()).email(userAggregate.getEmail()).avatar(userAggregate.getAvatar()).gender(userAggregate.getGender()).status(userAggregate.getStatus()).loginIp(userAggregate.getLoginIp()).loginDate(userAggregate.getLoginDate()).logoutDate(userAggregate.getLogoutDate()).deleted(userAggregate.getDeleted()).createdTime(userAggregate.getCreatedTime()).updatedTime(userAggregate.getUpdatedTime())
                .creator(userAggregate.getCreator()).updater(userAggregate.getUpdater()).tenantId(userAggregate.getTenantId()).build();
    }
    
    /**
     * 将User转换为UserAggregate
     */
    private UserAggregate convertToUserAggregate(User user) {
        if (user == null) {
            return null;
        }
        
        return UserAggregate.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword()).nickname(user.getNickname()).phone(user.getPhone()).email(user.getEmail()).avatar(user.getAvatar()).gender(user.getGender()).status(user.getStatus()).loginIp(user.getLoginIp()).loginDate(user.getLoginDate()).logoutDate(user.getLogoutDate()).deleted(user.getDeleted()).createdTime(user.getCreatedTime()).updatedTime(user.getUpdatedTime()).creator(user.getCreator()).updater(user.getUpdater()).tenantId(user.getTenantId()).build();
    }
    
    @Override
    public Mono<PageResult<User>> selectPage(Query query) {
        return userApplicationService.selectPage(query).map(pageResult -> {
            PageResult<User> userPageResult = new PageResult<>();
            userPageResult.setRecords(pageResult.getRecords().stream().toList());
            userPageResult.setTotal(pageResult.getTotal());
            userPageResult.setCurrent(pageResult.getCurrent());
            userPageResult.setSize(pageResult.getSize());
            return userPageResult;
        });
    }
    
    @Override
    public Mono<byte[]> export(Query query) {
        return userApplicationService.export(query);
    }
    
    @Override
    public Mono<Void> importData(byte[] data) {
        return userApplicationService.importData(data);
    }
}