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
package org.apaas.core.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 用户事件监听器
 * @author ivan
 */
@Slf4j
@Component
public class UserEventListener implements StreamListener<String, ObjectRecord<String, UserEvent>> {
    
    @Override
    public void onMessage(ObjectRecord<String, UserEvent> message) {
        UserEvent userEvent = message.getValue();
        log.info("收到用户事件: EventType={}, UserId={}, Username={}, OperationType={}, Description={}", userEvent.getEventType(), userEvent.getUserId(), userEvent.getUsername(), userEvent.getOperationType(), userEvent.getDescription());
        
        // 根据事件类型处理不同的业务逻辑
        switch (userEvent.getEventType()) {
            case "USER_CREATED":
                handleUserCreated(userEvent);
                break;
            case "USER_UPDATED":
                handleUserUpdated(userEvent);
                break;
            case "USER_DELETED":
                handleUserDeleted(userEvent);
                break;
            case "USER_LOGIN":
                handleUserLogin(userEvent);
                break;
            case "USER_LOGIN_FAILED":
                handleUserLoginFailed(userEvent);
                break;
            case "USER_LOGOUT":
                handleUserLogout(userEvent);
                break;
            case "USER_PASSWORD_RESET":
                handleUserPasswordReset(userEvent);
                break;
            case "USER_STATUS_CHANGED":
                handleUserStatusChanged(userEvent);
                break;
            default:
                log.warn("未知的用户事件类型: {}", userEvent.getEventType());
        }
    }
    
    /**
     * 处理用户创建事件
     */
    private void handleUserCreated(UserEvent userEvent) {
        log.info("处理用户创建事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如发送欢迎邮件、初始化用户数据等
    }
    
    /**
     * 处理用户更新事件
     */
    private void handleUserUpdated(UserEvent userEvent) {
        log.info("处理用户更新事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如同步用户信息到其他系统等
    }
    
    /**
     * 处理用户删除事件
     */
    private void handleUserDeleted(UserEvent userEvent) {
        log.info("处理用户删除事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如清理用户相关数据等
    }
    
    /**
     * 处理用户登录事件
     */
    private void handleUserLogin(UserEvent userEvent) {
        log.info("处理用户登录事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如记录登录日志、更新用户登录信息等
    }
    
    /**
     * 处理用户登录失败事件
     */
    private void handleUserLoginFailed(UserEvent userEvent) {
        log.info("处理用户登录失败事件: 用户名={}", userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如记录登录失败日志、安全监控等
    }
    
    /**
     * 处理用户登出事件
     */
    private void handleUserLogout(UserEvent userEvent) {
        log.info("处理用户登出事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如记录登出日志等
    }
    
    /**
     * 处理用户密码重置事件
     */
    private void handleUserPasswordReset(UserEvent userEvent) {
        log.info("处理用户密码重置事件: 用户ID={}, 用户名={}", userEvent.getUserId(), userEvent.getUsername());
        // 在这里可以添加具体的业务逻辑，比如发送密码重置通知等
    }
    
    /**
     * 处理用户状态变更事件
     */
    private void handleUserStatusChanged(UserEvent userEvent) {
        log.info("处理用户状态变更事件: 用户ID={}, 用户名={}, 状态描述={}", userEvent.getUserId(), userEvent.getUsername(), userEvent.getDescription());
        // 在这里可以添加具体的业务逻辑，比如同步状态变更到其他系统等
    }
}