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
package org.apaas.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.entity.UserAggregate;
import org.apaas.auth.repository.reactive.ReactiveUserRepository;
import org.apaas.auth.service.UserDomainService;
import org.apaas.auth.specification.UserSpecification;
import org.apaas.domain.service.domain.AbstractDomainService;
import org.apaas.domain.specification.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 用户领域服务实现
 * 处理用户相关的复杂业务逻辑
 *
 * @author ivan
 */
@Slf4j
@Service
public class UserDomainServiceImpl extends AbstractDomainService<UserAggregate, Long, ReactiveUserRepository> 
        implements UserDomainService {
    
    public UserDomainServiceImpl(ReactiveUserRepository repository) {
        super(repository);
    }
    
    /**
     * 创建用户
     *
     * @param user 用户聚合根
     * @return 创建后的用户
     */
    @Override
    public Mono<UserAggregate> createUser(UserAggregate user) {
        log.info("创建用户: username={}", user.getUsername());
        
        // 定义创建用户的业务规范
        Specification<UserAggregate> createSpec = 
                UserSpecification.nonEmptyUsername()
                        .and(UserSpecification.nonEmptyPassword())
                        .and(UserSpecification.validStatus());
        
        // 验证并保存
        return validateAndSave(user, createSpec)
                .doOnSuccess(saved -> log.info("用户创建成功: userId={}, username={}", saved.getId(), saved.getUsername()))
                .doOnError(error -> log.error("用户创建失败: username={}, error={}", user.getUsername(), error.getMessage()));
    }
    
    /**
     * 更新用户
     *
     * @param user 用户聚合根
     * @return 更新后的用户
     */
    @Override
    public Mono<UserAggregate> updateUser(UserAggregate user) {
        log.info("更新用户: userId={}, username={}", user.getId(), user.getUsername());
        
        // 定义更新用户的业务规范
        Specification<UserAggregate> updateSpec = 
                UserSpecification.nonEmptyUsername()
                        .and(UserSpecification.validStatus())
                        .and(UserSpecification.notDeleted());
        
        // 验证并保存
        return validateAndSave(user, updateSpec)
                .doOnSuccess(saved -> log.info("用户更新成功: userId={}, username={}", saved.getId(), saved.getUsername()))
                .doOnError(error -> log.error("用户更新失败: userId={}, error={}", user.getId(), error.getMessage()));
    }
    
    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        log.info("删除用户: userId={}", userId);
        
        // 定义删除用户的业务规范（管理员用户不能删除）
        Specification<UserAggregate> deleteSpec = UserSpecification.notAdminUser();
        
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")))
                .flatMap(user -> checkSpecification(user, deleteSpec)
                        .flatMap(satisfied -> {
                            if (!satisfied) {
                                return Mono.error(new RuntimeException("管理员用户不能删除"));
                            }
                            user.setDeleted(1); // 标记为已删除
                            user.setUpdatedTime(LocalDateTime.now());
                            return repository.save(user).thenReturn(true);
                        }))
                .doOnSuccess(result -> log.info("用户删除成功: userId={}", userId))
                .doOnError(error -> log.error("用户删除失败: userId={}, error={}", userId, error.getMessage()));
    }
    
    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    @Override
    public Mono<Boolean> resetPassword(Long userId, String newPassword) {
        log.info("重置用户密码: userId={}", userId);
        
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")))
                .flatMap(user -> {
                    user.setPassword(newPassword); // 实际应用中应该加密密码
                    user.setUpdatedTime(LocalDateTime.now());
                    return repository.save(user).thenReturn(true);
                })
                .doOnSuccess(result -> log.info("用户密码重置成功: userId={}", userId))
                .doOnError(error -> log.error("用户密码重置失败: userId={}, error={}", userId, error.getMessage()));
    }
    
    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态：0-启用，1-禁用
     * @return 更新后的用户
     */
    @Override
    public Mono<UserAggregate> changeStatus(Long userId, Integer status) {
        log.info("修改用户状态: userId={}, status={}", userId, status);
        
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")))
                .flatMap(user -> {
                    user.setStatus(status);
                    user.setUpdatedTime(LocalDateTime.now());
                    return repository.save(user);
                })
                .doOnSuccess(updated -> {
                    String statusDesc = status == 0 ? "启用" : "禁用";
                    log.info("用户状态修改成功: userId={}, username={}, status={}", updated.getId(), updated.getUsername(), statusDesc);
                })
                .doOnError(error -> log.error("用户状态修改失败: userId={}, error={}", userId, error.getMessage()));
    }
    
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录后的用户
     */
    @Override
    public Mono<UserAggregate> login(String username, String password) {
        log.info("用户登录: username={}", username);
        
        // 根据用户名查找用户
        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")))
                .flatMap(user -> {
                    // 验证密码（实际应用中应该使用加密验证）
                    if (user.getPassword().equals(password)) {
                        user.setLoginIp("127.0.0.1"); // 实际应用中应该获取真实IP
                        user.setLoginDate(LocalDateTime.now());
                        user.setUpdatedTime(LocalDateTime.now());
                        log.info("用户登录成功: username={}", username);
                        return repository.save(user);
                    } else {
                        log.warn("用户登录失败-密码错误: username={}", username);
                        return Mono.error(new RuntimeException("密码错误"));
                    }
                });
    }
    
    /**
     * 用户登出
     *
     * @param userId 用户ID
     * @return 登出结果
     */
    @Override
    public Mono<Boolean> logout(Long userId) {
        log.info("用户登出: userId={}", userId);
        
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")))
                .flatMap(user -> {
                    user.setLogoutDate(LocalDateTime.now());
                    user.setUpdatedTime(LocalDateTime.now());
                    return repository.save(user).thenReturn(true);
                })
                .doOnSuccess(result -> log.info("用户登出成功: userId={}", userId))
                .doOnError(error -> log.error("用户登出失败: userId={}, error={}", userId, error.getMessage()));
    }
    
    /**
     * 执行领域逻辑
     *
     * @param domainObject 领域对象
     * @return 处理结果
     */
    @Override
    public Mono<UserAggregate> execute(UserAggregate domainObject) {
        // 默认实现，保存用户
        return repository.save(domainObject);
    }
}