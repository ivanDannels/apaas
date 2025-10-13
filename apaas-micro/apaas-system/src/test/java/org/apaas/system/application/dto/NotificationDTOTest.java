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
package org.apaas.system.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 通知DTO测试类
 *
 * @author ivan
 */
class NotificationDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        NotificationDTO dto = new NotificationDTO();
        Long id = 1L;
        String title = "系统通知";
        String content = "这是一条系统通知";
        Long receiverId = 1L;
        String receiverName = "管理员";
        Long senderId = 2L;
        String senderName = "系统";
        String type = "system";
        String channel = "email";
        String businessId = "123456";
        String businessType = "user";
        Integer readStatus = 0;
        LocalDateTime readTime = LocalDateTime.now();
        Integer sendStatus = 0;
        LocalDateTime sendTime = LocalDateTime.now();
        String failReason = "";
        Long templateId = 1L;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setReceiverId(receiverId);
        dto.setReceiverName(receiverName);
        dto.setSenderId(senderId);
        dto.setSenderName(senderName);
        dto.setType(type);
        dto.setChannel(channel);
        dto.setBusinessId(businessId);
        dto.setBusinessType(businessType);
        dto.setReadStatus(readStatus);
        dto.setReadTime(readTime);
        dto.setSendStatus(sendStatus);
        dto.setSendTime(sendTime);
        dto.setFailReason(failReason);
        dto.setTemplateId(templateId);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(title, dto.getTitle());
        assertEquals(content, dto.getContent());
        assertEquals(receiverId, dto.getReceiverId());
        assertEquals(receiverName, dto.getReceiverName());
        assertEquals(senderId, dto.getSenderId());
        assertEquals(senderName, dto.getSenderName());
        assertEquals(type, dto.getType());
        assertEquals(channel, dto.getChannel());
        assertEquals(businessId, dto.getBusinessId());
        assertEquals(businessType, dto.getBusinessType());
        assertEquals(readStatus, dto.getReadStatus());
        assertEquals(readTime, dto.getReadTime());
        assertEquals(sendStatus, dto.getSendStatus());
        assertEquals(sendTime, dto.getSendTime());
        assertEquals(failReason, dto.getFailReason());
        assertEquals(templateId, dto.getTemplateId());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        NotificationDTO dto = new NotificationDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        NotificationDTO dto = new NotificationDTO();
        dto.setId(1L);
        dto.setTitle("系统通知");
        dto.setContent("这是一条系统通知");
        dto.setReceiverId(1L);
        dto.setReceiverName("管理员");
        dto.setSenderId(2L);
        dto.setSenderName("系统");
        dto.setType("system");
        dto.setChannel("email");
        dto.setBusinessId("123456");
        dto.setBusinessType("user");
        dto.setReadStatus(0);
        dto.setReadTime(LocalDateTime.now());
        dto.setSendStatus(0);
        dto.setSendTime(LocalDateTime.now());
        dto.setFailReason("");
        dto.setTemplateId(1L);
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("NotificationDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("title=系统通知"));
        assertTrue(toStringResult.contains("content=这是一条系统通知"));
    }
}