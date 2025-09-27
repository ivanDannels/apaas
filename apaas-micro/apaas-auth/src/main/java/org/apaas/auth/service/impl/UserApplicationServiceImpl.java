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
import org.apaas.auth.service.UserApplicationService;
import org.apaas.auth.service.UserDomainService;
import org.apaas.domain.event.EventPublisherService;
import org.apaas.domain.event.UserEvent;
import org.apaas.domain.exception.LockAcquisitionException;
import org.apaas.domain.log.LogUtil;
import org.apaas.domain.lock.DistributedLockService;
import org.apaas.domain.service.application.AbstractApplicationService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户应用服务实现
 * 处理用户相关的应用层逻辑，协调领域服务和基础设施层
 *
 * @author ivan
 */
@Slf4j
@Service
public class UserApplicationServiceImpl extends AbstractApplicationService<UserAggregate, Long, ReactiveUserRepository> 
        implements UserApplicationService {
    
    private final DistributedLockService distributedLockService;
    private final EventPublisherService eventPublisherService;
    private final UserDomainService userDomainService;
    
    public UserApplicationServiceImpl(
            ReactiveUserRepository repository,
            DistributedLockService distributedLockService,
            EventPublisherService eventPublisherService,
            UserDomainService userDomainService) {
        super(repository);
        this.distributedLockService = distributedLockService;
        this.eventPublisherService = eventPublisherService;
        this.userDomainService = userDomainService;
    }
    
    @Override
    @Cacheable(value = "users", key = "#username")
    public Mono<UserAggregate> getUserByUsername(String username) {
        LogUtil.info(UserApplicationServiceImpl.class, "根据用户名查询用户信息: username={}", username);
        return repository.findByUsername(username);
    }
    
    @Override
    public Mono<UserAggregate> addUser(UserAggregate user) {
        LogUtil.info(UserApplicationServiceImpl.class, "创建用户: username={}", user.getUsername());
        String lockKey = "user:add:" + user.getUsername();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                user.setCreatedTime(LocalDateTime.now());
                user.setUpdatedTime(LocalDateTime.now());
                return userDomainService.createUser(user).flatMap(createdUser -> {
                    LogUtil.info(UserApplicationServiceImpl.class, "用户创建成功: userId={}, username={}", createdUser.getId(), createdUser.getUsername());
                    // 发布用户创建事件
                    UserEvent userEvent = new UserEvent("USER_CREATED", createdUser.getId(), createdUser.getUsername(), "CREATE", "用户创建成功");
                    return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.just(createdUser));
                }).onErrorResume(throwable -> {
                    LogUtil.error(UserApplicationServiceImpl.class, "用户创建失败: username={}, error={}", user.getUsername(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(UserApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "users", key = "#user.username")
    public Mono<UserAggregate> updateUser(UserAggregate user) {
        LogUtil.info(UserApplicationServiceImpl.class, "更新用户信息: userId={}, username={}", user.getId(), user.getUsername());
        String lockKey = "user:update:" + user.getId();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                user.setUpdatedTime(LocalDateTime.now());
                return userDomainService.updateUser(user).flatMap(updatedUser -> {
                    LogUtil.info(UserApplicationServiceImpl.class, "用户信息更新成功: userId={}, username={}", updatedUser.getId(), updatedUser.getUsername());
                    // 发布用户更新事件
                    UserEvent userEvent = new UserEvent("USER_UPDATED", updatedUser.getId(), updatedUser.getUsername(), "UPDATE", "用户信息更新成功");
                    return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.just(updatedUser));
                }).onErrorResume(throwable -> {
                    LogUtil.error(UserApplicationServiceImpl.class, "用户信息更新失败: userId={}, error={}", user.getId(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(UserApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Boolean> deleteUser(Long id) {
        LogUtil.info(UserApplicationServiceImpl.class, "删除用户: userId={}", id);
        String lockKey = "user:delete:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return userDomainService.deleteUser(id).flatMap(deleted -> {
                    if (deleted) {
                        LogUtil.info(UserApplicationServiceImpl.class, "用户删除成功: userId={}", id);
                        // 发布用户删除事件
                        UserEvent userEvent = new UserEvent("USER_DELETED", id, "", "DELETE", "用户删除成功");
                        return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                    } else {
                        LogUtil.info(UserApplicationServiceImpl.class, "用户删除失败: userId={}", id);
                        return distributedLockService.unlock(lockKey).thenReturn(false);
                    }
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(UserApplicationServiceImpl.class, "用户删除失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(UserApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Boolean> resetPassword(Long id, String newPassword) {
        LogUtil.info(UserApplicationServiceImpl.class, "重置用户密码: userId={}", id);
        String lockKey = "user:resetPassword:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return userDomainService.resetPassword(id, newPassword).flatMap(reset -> {
                    if (reset) {
                        LogUtil.info(UserApplicationServiceImpl.class, "用户密码重置成功: userId={}", id);
                        // 发布密码重置事件
                        UserEvent userEvent = new UserEvent("USER_PASSWORD_RESET", id, "", "PASSWORD_RESET", "用户密码重置成功");
                        return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                    } else {
                        LogUtil.info(UserApplicationServiceImpl.class, "用户密码重置失败: userId={}", id);
                        return distributedLockService.unlock(lockKey).thenReturn(false);
                    }
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(UserApplicationServiceImpl.class, "用户密码重置失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(UserApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        LogUtil.info(UserApplicationServiceImpl.class, "修改用户状态: userId={}, status={}", id, status);
        String lockKey = "user:changeStatus:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> {
            if (locked) {
                return userDomainService.changeStatus(id, status).flatMap(updatedUser -> {
                    String statusDesc = status == 0 ? "启用" : "禁用";
                    LogUtil.info(UserApplicationServiceImpl.class, "用户状态修改成功: userId={}, username={}, status={}", updatedUser.getId(), updatedUser.getUsername(), statusDesc);
                    // 发布状态变更事件
                    UserEvent userEvent = new UserEvent("USER_STATUS_CHANGED", updatedUser.getId(), updatedUser.getUsername(), "STATUS_CHANGE", "用户状态变更为" + statusDesc);
                    return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).thenReturn(true);
                }).onErrorReturn(false).onErrorResume(throwable -> {
                    LogUtil.error(UserApplicationServiceImpl.class, "用户状态修改失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.error(throwable));
                });
            } else {
                LogUtil.error(UserApplicationServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
                return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
            }
        });
    }
    
    @Override
    public Flux<String> getUserPermissions(Long userId) {
        LogUtil.info(UserApplicationServiceImpl.class, "获取用户权限: userId={}", userId);
        // 这里需要根据用户ID查询其权限，具体实现依赖于权限模型设计
        // 暂时返回一些示例权限，实际开发中需要实现具体的权限查询逻辑
        List<String> permissions = Arrays.asList("system:user:view", "system:user:add", "system:user:edit");
        return Flux.fromIterable(permissions);
    }
    
    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        LogUtil.info(UserApplicationServiceImpl.class, "记录用户登录信息: userId={}, loginIp={}", userId, loginIp);
        return repository.findById(userId).flatMap(user -> {
            user.setLoginIp(loginIp);
            user.setLoginDate(LocalDateTime.now());
            user.setUpdatedTime(LocalDateTime.now());
            return repository.save(user);
        }).then();
    }
    
    @Override
    public Mono<UserAggregate> login(String username, String password) {
        LogUtil.info(UserApplicationServiceImpl.class, "用户登录: username={}", username);
        return userDomainService.login(username, password).flatMap(loggedInUser -> {
            // 发布登录事件
            UserEvent userEvent = new UserEvent("USER_LOGIN", loggedInUser.getId(), loggedInUser.getUsername(), "LOGIN", "用户登录成功");
            eventPublisherService.publishEvent("user.events", userEvent).subscribe();
            return Mono.just(loggedInUser);
        }).onErrorResume(throwable -> {
            LogUtil.warn(UserApplicationServiceImpl.class, "用户登录失败: username={}, error={}", username, throwable.getMessage());
            // 发布登录失败事件
            UserEvent userEvent = new UserEvent("USER_LOGIN_FAILED", null, username, "LOGIN_FAILED", "用户登录失败");
            eventPublisherService.publishEvent("user.events", userEvent).subscribe();
            return Mono.error(throwable);
        });
    }
    
    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        LogUtil.info(UserApplicationServiceImpl.class, "更新用户密码");
        // 这里应该获取当前用户并验证旧密码
        // 暂时返回true表示成功
        return Mono.just(true);
    }
    
    @Override
    public Mono<UserAggregate> getCurrentUser() {
        LogUtil.info(UserApplicationServiceImpl.class, "获取当前用户信息");
        // 这里应该从安全上下文中获取当前用户
        // 暂时返回一个示例用户
        UserAggregate user = UserAggregate.builder().id(1L).username("admin").nickname("管理员").build();
        return Mono.just(user);
    }
    
    @Override
    @CacheEvict(value = "users", key = "#userId")
    public Mono<Boolean> logout(Long userId) {
        LogUtil.info(UserApplicationServiceImpl.class, "用户登出: userId={}", userId);
        return userDomainService.logout(userId).flatMap(logout -> {
            if (logout) {
                // 记录登出日志
                return repository.findById(userId).flatMap(user -> {
                    user.setLogoutDate(LocalDateTime.now());
                    return repository.save(user);
                }).flatMap(logoutUser -> {
                    LogUtil.info(UserApplicationServiceImpl.class, "用户登出成功: userId={}, username={}", logoutUser.getId(), logoutUser.getUsername());
                    // 发布登出事件
                    UserEvent userEvent = new UserEvent("USER_LOGOUT", logoutUser.getId(), logoutUser.getUsername(), "LOGOUT", "用户登出成功");
                    return eventPublisherService.publishEvent("user.events", userEvent).then(Mono.just(true));
                });
            } else {
                return Mono.just(false);
            }
        });
    }
    
    @Override
    public Mono<PageResult<UserAggregate>> selectPage(Query query) {
        Long tenantId = org.apaas.core.context.TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1, 
                query.getPageSize(), 
                Sort.by(Sort.Direction.DESC, "id")
        );
        return repository.findByPage(query, pageRequest)
                .map(page -> new PageResult<UserAggregate>()
                        .setRecords(page.getContent())
                        .setTotal(page.getTotalElements())
                        .setCurrent(query.getPageNum())
                        .setSize(query.getPageSize()));
    }
}