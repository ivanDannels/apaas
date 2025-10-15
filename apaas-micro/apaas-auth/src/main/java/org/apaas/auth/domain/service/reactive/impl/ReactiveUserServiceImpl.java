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
package org.apaas.auth.domain.service.reactive.impl;

import org.apaas.auth.application.assembler.UserAssembler;
import org.apaas.auth.application.dto.UserDTO;
import org.apaas.auth.domain.aggregate.UserAggregate;
import org.apaas.auth.domain.entity.User;
import org.apaas.auth.domain.repository.reactive.UserRepository;
import org.apaas.auth.domain.service.UserApplicationService;
import org.apaas.auth.domain.service.reactive.ReactiveUserService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.application.service.AbstractApplicationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务实现适配器
 * 适配旧的接口到新的应用服务实现
 * @author ivan
 */
@Service
public class ReactiveUserServiceImpl extends AbstractApplicationService<User, UserDTO, Long, UserRepository> implements ReactiveUserService {
    
    private final UserRepository repository;

    private final UserAssembler assembler;
    
    public ReactiveUserServiceImpl(UserRepository repository, UserAssembler assembler) {
        super(repository,  assembler);
        this.repository = repository;
        this.assembler = assembler;
    }
    
    @Override
    public Mono<User> getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }
    
    @Override
    public Mono<User> addUser(User user) {
        return repository.addUser(user);
    }
    
    @Override
    public Mono<User> updateUser(User user) {
        return repository.updateUser(user);
    }
    
    @Override
    public Mono<Void> deleteUser(Long id) {
        return repository.deleteUser(id).then();
    }
    
    @Override
    public Mono<Void> resetPassword(Long id, String newPassword) {
        return repository.resetPassword(id, newPassword).then();
    }
    
    @Override
    public Mono<Void> changeStatus(Long id, Integer status) {
        return repository.changeStatus(id, status).then();
    }
    
    @Override
    public Flux<String> getUserPermissions(Long userId) {
        return repository.getUserPermissions(userId);
    }
    
    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        return repository.recordLoginInfo(userId, loginIp);
    }
    
    @Override
    public Mono<User> login(String username, String password) {
        return repository.login(username, password);
    }
    
    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        return repository.updatePassword(oldPassword, newPassword);
    }
    
    @Override
    public Mono<User> getCurrentUser() {
        return repository.getCurrentUser();
    }
    
    @Override
    public Mono<Void> logout(Long userId) {
        return repository.logout(userId).then();
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

}