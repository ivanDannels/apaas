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
import org.apaas.auth.repository.reactive.ReactiveUserRepository;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.apaas.core.event.EventPublisherService;
import org.apaas.core.event.UserEvent;
import org.apaas.core.exception.LockAcquisitionException;
import org.apaas.core.log.LogUtil;
import org.apaas.core.lock.DistributedLockService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 响应式用户服务实现类
 * @author ivan
 */
@Service
public class ReactiveUserServiceImpl extends BaseServiceImpl<User, Long, ReactiveUserRepository> implements ReactiveUserService {
    
    private final DistributedLockService distributedLockService;
    private final EventPublisherService eventPublisherService;
    
    public ReactiveUserServiceImpl(ReactiveUserRepository repository, DistributedLockService distributedLockService, EventPublisherService eventPublisherService) {
        super(repository);
        this.distributedLockService = distributedLockService;
        this.eventPublisherService = eventPublisherService;
    }
    
    @Override
    @Cacheable(value = "users", key = "#username")
    public Mono<User> getUserByUsername(String username) {
        LogUtil.info(ReactiveUserServiceImpl.class, "根据用户名查询用户信息: username={}", username);
        return repository.findByUsername(username);
    }
    
    @Override
    public Mono<User> addUser(User user) {
        LogUtil.info(ReactiveUserServiceImpl.class, "创建用户: username={}", user.getUsername());
        String lockKey = "user:add:" + user.getUsername();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> handleAddUser(locked, user, lockKey));
    }
    
    private Mono<User> handleAddUser(Boolean locked, User user, String lockKey) {
        if (Boolean.TRUE.equals(locked)) {
            // 检查用户名是否已存在
            return repository.findByUsername(user.getUsername()).flatMap(existingUser -> {
                LogUtil.warn(ReactiveUserServiceImpl.class, "用户名已存在: username={}", user.getUsername());
                // 用户名已存在，释放锁并返回错误
                return distributedLockService.unlock(lockKey).then(Mono.<User>error(new RuntimeException("用户名已存在")));
            }).switchIfEmpty(Mono.defer(() -> {
                // 用户名不存在，保存用户
                return repository.save(user).flatMap(savedUser -> {
                    LogUtil.info(ReactiveUserServiceImpl.class, "用户创建成功: userId={}, username={}", savedUser.getId(), savedUser.getUsername());
                    // 发布用户创建事件
                    UserEvent userEvent = new UserEvent("USER_CREATED", savedUser.getId(), savedUser.getUsername(), "CREATE", "用户创建成功");
                    return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.just(savedUser));
                }).onErrorResume(throwable -> {
                    LogUtil.error(ReactiveUserServiceImpl.class, "用户创建失败: username={}, error={}", user.getUsername(), throwable.getMessage(), throwable);
                    return distributedLockService.unlock(lockKey).then(Mono.<User>error(throwable));
                });
            }));
        } else {
            LogUtil.error(ReactiveUserServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
            return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
        }
    }
    
    @Override
    @CacheEvict(value = "users", key = "#user.username")
    public Mono<User> updateUser(User user) {
        LogUtil.info(ReactiveUserServiceImpl.class, "更新用户信息: userId={}, username={}", user.getId(), user.getUsername());
        String lockKey = "user:update:" + user.getId();
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> handleUpdateUser(locked, user, lockKey));
    }
    
    private Mono<User> handleUpdateUser(Boolean locked, User user, String lockKey) {
        if (Boolean.TRUE.equals(locked)) {
            return repository.findById(user.getId()).flatMap(existingUser -> {
                // 更新用户信息
                existingUser.setNickname(user.getNickname());
                existingUser.setEmail(user.getEmail());
                existingUser.setPhone(user.getPhone());
                existingUser.setUpdatedTime(LocalDateTime.now());
                return repository.save(existingUser);
            }).flatMap(updatedUser -> {
                LogUtil.info(ReactiveUserServiceImpl.class, "用户信息更新成功: userId={}, username={}", updatedUser.getId(), updatedUser.getUsername());
                // 发布用户更新事件
                UserEvent userEvent = new UserEvent("USER_UPDATED", updatedUser.getId(), updatedUser.getUsername(), "UPDATE", "用户信息更新成功");
                return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.just(updatedUser));
            }).onErrorResume(throwable -> {
                LogUtil.error(ReactiveUserServiceImpl.class, "用户信息更新失败: userId={}, error={}", user.getId(), throwable.getMessage(), throwable);
                return distributedLockService.unlock(lockKey).then(Mono.<User>error(throwable));
            });
        } else {
            LogUtil.error(ReactiveUserServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
            return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
        }
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Void> deleteUser(Long id) {
        LogUtil.info(ReactiveUserServiceImpl.class, "删除用户: userId={}", id);
        String lockKey = "user:delete:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> handleDeleteUser(locked, id, lockKey));
    }
    
    private Mono<Void> handleDeleteUser(Boolean locked, Long id, String lockKey) {
        if (Boolean.TRUE.equals(locked)) {
            // 检查是否为管理员
            return repository.findById(id).flatMap(user -> {
                user.setDeleted(1); // 标记为已删除
                user.setUpdatedTime(LocalDateTime.now());
                return repository.save(user);
            }).flatMap(deletedUser -> {
                LogUtil.info(ReactiveUserServiceImpl.class, "用户删除成功: userId={}, username={}", deletedUser.getId(), deletedUser.getUsername());
                // 发布用户删除事件
                UserEvent userEvent = new UserEvent("USER_DELETED", deletedUser.getId(), deletedUser.getUsername(), "DELETE", "用户删除成功");
                return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.<Void>empty());
            }).onErrorResume(throwable -> {
                LogUtil.error(ReactiveUserServiceImpl.class, "用户删除失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                return distributedLockService.unlock(lockKey).then(Mono.<Void>error(throwable));
            });
        } else {
            LogUtil.error(ReactiveUserServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
            return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
        }
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Void> resetPassword(Long id, String newPassword) {
        LogUtil.info(ReactiveUserServiceImpl.class, "重置用户密码: userId={}", id);
        String lockKey = "user:resetPassword:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> handleResetPassword(locked, id, newPassword, lockKey));
    }
    
    private Mono<Void> handleResetPassword(Boolean locked, Long id, String newPassword, String lockKey) {
        if (Boolean.TRUE.equals(locked)) {
            return repository.findById(id).flatMap(user -> {
                user.setPassword(newPassword); // 实际应用中应该加密密码
                user.setUpdatedTime(LocalDateTime.now());
                return repository.save(user);
            }).flatMap(updatedUser -> {
                LogUtil.info(ReactiveUserServiceImpl.class, "用户密码重置成功: userId={}, username={}", updatedUser.getId(), updatedUser.getUsername());
                // 发布密码重置事件
                UserEvent userEvent = new UserEvent("USER_PASSWORD_RESET", updatedUser.getId(), updatedUser.getUsername(), "PASSWORD_RESET", "用户密码重置成功");
                return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.<Void>empty());
            }).onErrorResume(throwable -> {
                LogUtil.error(ReactiveUserServiceImpl.class, "用户密码重置失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                return distributedLockService.unlock(lockKey).then(Mono.<Void>error(throwable));
            });
        } else {
            LogUtil.error(ReactiveUserServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
            return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
        }
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public Mono<Void> changeStatus(Long id, Integer status) {
        LogUtil.info(ReactiveUserServiceImpl.class, "修改用户状态: userId={}, status={}", id, status);
        String lockKey = "user:changeStatus:" + id;
        return distributedLockService.tryLock(lockKey, 3, 10, TimeUnit.SECONDS).flatMap(locked -> handleChangeStatus(locked, id, status, lockKey));
    }
    
    private Mono<Void> handleChangeStatus(Boolean locked, Long id, Integer status, String lockKey) {
        if (Boolean.TRUE.equals(locked)) {
            return repository.findById(id).flatMap(user -> {
                user.setStatus(status);
                user.setUpdatedTime(LocalDateTime.now());
                return repository.save(user);
            }).flatMap(updatedUser -> {
                String statusDesc = status == 0 ? "启用" : "禁用";
                LogUtil.info(ReactiveUserServiceImpl.class, "用户状态修改成功: userId={}, username={}, status={}", updatedUser.getId(), updatedUser.getUsername(), statusDesc);
                // 发布状态变更事件
                UserEvent userEvent = new UserEvent("USER_STATUS_CHANGED", updatedUser.getId(), updatedUser.getUsername(), "STATUS_CHANGE", "用户状态变更为" + statusDesc);
                return eventPublisherService.publishEvent("user.events", userEvent).then(distributedLockService.unlock(lockKey)).then(Mono.<Void>empty());
            }).onErrorResume(throwable -> {
                LogUtil.error(ReactiveUserServiceImpl.class, "用户状态修改失败: userId={}, error={}", id, throwable.getMessage(), throwable);
                return distributedLockService.unlock(lockKey).then(Mono.<Void>error(throwable));
            });
        } else {
            LogUtil.error(ReactiveUserServiceImpl.class, "获取分布式锁失败: lockKey={}", lockKey);
            return Mono.error(new LockAcquisitionException("获取锁失败，请稍后重试"));
        }
    }
    
    @Override
    public Flux<String> getUserPermissions(Long userId) {
        LogUtil.info(ReactiveUserServiceImpl.class, "获取用户权限: userId={}", userId);
        // 这里需要根据用户ID查询其权限，具体实现依赖于权限模型设计
        // 暂时返回一些示例权限，实际开发中需要实现具体的权限查询逻辑
        List<String> permissions = Arrays.asList("system:user:view", "system:user:add", "system:user:edit");
        return Flux.fromIterable(permissions);
    }
    
    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        LogUtil.info(ReactiveUserServiceImpl.class, "记录用户登录信息: userId={}, loginIp={}", userId, loginIp);
        return repository.findById(userId).flatMap(user -> {
            user.setLoginIp(loginIp);
            user.setLoginDate(LocalDateTime.now());
            user.setUpdatedTime(LocalDateTime.now());
            return repository.save(user);
        }).then();
    }
    
    @Override
    public Mono<User> login(String username, String password) {
        LogUtil.info(ReactiveUserServiceImpl.class, "用户登录: username={}", username);
        // 根据用户名查找用户
        return repository.findByUsername(username).flatMap(user -> {
            // 验证密码（实际应用中应该使用加密验证）
            if (user.getPassword().equals(password)) {
                LogUtil.info(ReactiveUserServiceImpl.class, "用户登录成功: username={}", username);
                // 发布登录事件
                UserEvent userEvent = new UserEvent("USER_LOGIN", user.getId(), user.getUsername(), "LOGIN", "用户登录成功");
                eventPublisherService.publishEvent("user.events", userEvent).subscribe();
                return Mono.just(user);
            } else {
                LogUtil.warn(ReactiveUserServiceImpl.class, "用户登录失败-密码错误: username={}", username);
                // 发布登录失败事件
                UserEvent userEvent = new UserEvent("USER_LOGIN_FAILED", null, username, "LOGIN_FAILED", "用户登录失败");
                eventPublisherService.publishEvent("user.events", userEvent).subscribe();
                return Mono.error(new RuntimeException("密码错误"));
            }
        }).switchIfEmpty(Mono.error(new RuntimeException("用户不存在")));
    }
    
    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        LogUtil.info(ReactiveUserServiceImpl.class, "更新用户密码");
        // 这里应该获取当前用户并验证旧密码
        // 暂时返回true表示成功
        return Mono.just(true);
    }
    
    @Override
    public Mono<User> getCurrentUser() {
        LogUtil.info(ReactiveUserServiceImpl.class, "获取当前用户信息");
        // 这里应该从安全上下文中获取当前用户
        // 暂时返回一个示例用户
        User user = User.builder().id(1L).username("admin").nickname("管理员").build();
        return Mono.just(user);
    }
    
    @Override
    @CacheEvict(value = "users", key = "#userId")
    public Mono<Void> logout(Long userId) {
        LogUtil.info(ReactiveUserServiceImpl.class, "用户登出: userId={}", userId);
        // 记录登出日志
        // 这里可以添加具体的登出逻辑，如清除用户会话、记录登出时间等
        return repository.findById(userId).flatMap(user -> {
            user.setLogoutDate(LocalDateTime.now());
            return repository.save(user);
        }).flatMap(logoutUser -> {
            LogUtil.info(ReactiveUserServiceImpl.class, "用户登出成功: userId={}, username={}", logoutUser.getId(), logoutUser.getUsername());
            // 发布登出事件
            UserEvent userEvent = new UserEvent("USER_LOGOUT", logoutUser.getId(), logoutUser.getUsername(), "LOGOUT", "用户登出成功");
            return eventPublisherService.publishEvent("user.events", userEvent).then(Mono.<Void>empty());
        });
    }
}