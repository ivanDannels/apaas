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
package org.apaas.auth.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.auth.application.assembler.UserAssembler;
import org.apaas.auth.application.dto.UserDTO;
import org.apaas.auth.domain.entity.User;
import org.apaas.auth.domain.repository.reactive.UserRepository;
import org.apaas.auth.domain.service.UserApplicationService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 用户应用服务实现类
 * 处理用户相关的应用层逻辑
 *
 * @author ivan
 */
@Slf4j
@Service
public class UserApplicationServiceImpl extends AbstractApplicationService<User, UserDTO, Long, UserRepository> implements UserApplicationService {
    
    private final PasswordEncoder passwordEncoder;
    
    public UserApplicationServiceImpl(UserRepository repository, UserAssembler assembler, PasswordEncoder passwordEncoder) {
        super(repository, assembler);
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Mono<UserDTO> getUserByUsername(String username) {
        return domainService.findByUsername(username).map(assembler::toDTO);
    }
    
    @Override
    public Mono<UserDTO> addUser(User user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置创建时间
        user.setCreatedTime(LocalDateTime.now());
        return domainService.save(user).map(assembler::toDTO);
    }
    
    @Override
    public Mono<UserDTO> updateUser(User user) {
        // 设置更新时间
        user.setUpdatedTime(LocalDateTime.now());
        return domainService.save(user).map(assembler::toDTO);
    }
    
    @Override
    public Mono<Boolean> deleteUser(Long id) {
        return domainService.deleteById(id).thenReturn(true);
    }
    
    @Override
    public Mono<Boolean> resetPassword(Long id, String newPassword) {
        return domainService.findById(id).flatMap(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setUpdatedTime(LocalDateTime.now());
            return domainService.save(user);
        }).thenReturn(true);
    }
    
    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return domainService.findById(id).flatMap(user -> {
            user.setStatus(status);
            user.setUpdatedTime(LocalDateTime.now());
            return domainService.save(user);
        }).thenReturn(true);
    }
    
    @Override
    public Flux<String> getUserPermissions(Long userId) {
        // 这里应该实现获取用户权限的逻辑
        // 暂时返回空的权限列表
        return Flux.empty();
    }
    
    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        return domainService.findById(userId).flatMap(user -> {
            user.setLoginIp(loginIp);
            user.setLoginDate(LocalDateTime.now());
            return domainService.save(user);
        }).then();
    }
    
    @Override
    public Mono<UserDTO> login(String username, String password) {
        return domainService.findByUsername(username).filter(user -> passwordEncoder.matches(password, user.getPassword())).map(assembler::toDTO);
    }
    
    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        // 这个方法需要当前用户的上下文信息，暂时不实现
        return Mono.just(false);
    }
    
    @Override
    public Mono<UserDTO> getCurrentUser() {
        // 这个方法需要当前用户的上下文信息，暂时不实现
        return Mono.empty();
    }
    
    @Override
    public Mono<Void> logout(Long userId) {
        return domainService.findById(userId).flatMap(user -> {
            user.setLogoutDate(LocalDateTime.now());
            return domainService.save(user);
        }).then();
    }
    
    @Override
    public Mono<PageResult<UserDTO>> selectPage(Query query) {
        return super.selectPage(query);
    }
}