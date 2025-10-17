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
package org.apaas.interfaces.event.listener;

import org.apaas.BaseTest;
import org.apaas.domain.event.UserEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.stream.ObjectRecord;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * UserEventListener 测试类
 * @author ivan
 */
class UserEventListenerTest {
    
    private UserEventListener listener;
    
    @BeforeEach
    public void setUp() {
        listener = new UserEventListener();
    }
    
    @Test
    void testOnMessageWithUserCreatedEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_CREATED").userId(1L).username("testuser").operationType("CREATE").description("Test user creation").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserUpdatedEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_UPDATED").userId(1L).username("testuser").operationType("UPDATE").description("Test user update").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserDeletedEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_DELETED").userId(1L).username("testuser").operationType("DELETE").description("Test user deletion").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserLoginEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_LOGIN").userId(1L).username("testuser").operationType("LOGIN").description("Test user login").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserLoginFailedEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_LOGIN_FAILED").userId(1L).username("testuser").operationType("LOGIN_FAILED").description("Test user login failed").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserLogoutEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_LOGOUT").userId(1L).username("testuser").operationType("LOGOUT").description("Test user logout").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserPasswordResetEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_PASSWORD_RESET").userId(1L).username("testuser").operationType("PASSWORD_RESET").description("Test user password reset").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUserStatusChangedEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("USER_STATUS_CHANGED").userId(1L).username("testuser").operationType("STATUS_CHANGE").description("Test user status change").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUnknownEvent() {
        // 准备测试数据
        UserEvent event = UserEvent.builder().eventType("UNKNOWN_EVENT").userId(1L).username("testuser").operationType("UNKNOWN").description("Unknown event").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, UserEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
}