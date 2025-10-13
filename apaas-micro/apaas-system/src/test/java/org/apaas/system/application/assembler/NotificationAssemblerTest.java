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
package org.apaas.system.application.assembler;

import org.apaas.system.application.dto.NotificationDTO;
import org.apaas.system.domain.model.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 通知装配器测试类
 *
 * @author ivan
 */
class NotificationAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        Notification entity = NotificationAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getReceiverId(), entity.getReceiverId());
        assertEquals(dto.getReceiverName(), entity.getReceiverName());
        assertEquals(dto.getSenderId(), entity.getSenderId());
        assertEquals(dto.getSenderName(), entity.getSenderName());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getChannel(), entity.getChannel());
        assertEquals(dto.getBusinessId(), entity.getBusinessId());
        assertEquals(dto.getBusinessType(), entity.getBusinessType());
        assertEquals(dto.getReadStatus(), entity.getReadStatus());
        assertEquals(dto.getReadTime(), entity.getReadTime());
        assertEquals(dto.getSendStatus(), entity.getSendStatus());
        assertEquals(dto.getSendTime(), entity.getSendTime());
        assertEquals(dto.getFailReason(), entity.getFailReason());
        assertEquals(dto.getTemplateId(), entity.getTemplateId());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        Notification entity = new Notification();
        entity.setId(1L);
        entity.setTitle("系统通知");
        entity.setContent("这是一条系统通知");
        entity.setReceiverId(1L);
        entity.setReceiverName("管理员");
        entity.setSenderId(2L);
        entity.setSenderName("系统");
        entity.setType("system");
        entity.setChannel("email");
        entity.setBusinessId("123456");
        entity.setBusinessType("user");
        entity.setReadStatus(0);
        entity.setReadTime(LocalDateTime.now());
        entity.setSendStatus(0);
        entity.setSendTime(LocalDateTime.now());
        entity.setFailReason("");
        entity.setTemplateId(1L);
        entity.setTenantId(1L);

        // 执行转换
        NotificationDTO dto = NotificationAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getReceiverId(), dto.getReceiverId());
        assertEquals(entity.getReceiverName(), dto.getReceiverName());
        assertEquals(entity.getSenderId(), dto.getSenderId());
        assertEquals(entity.getSenderName(), dto.getSenderName());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getChannel(), dto.getChannel());
        assertEquals(entity.getBusinessId(), dto.getBusinessId());
        assertEquals(entity.getBusinessType(), dto.getBusinessType());
        assertEquals(entity.getReadStatus(), dto.getReadStatus());
        assertEquals(entity.getReadTime(), dto.getReadTime());
        assertEquals(entity.getSendStatus(), dto.getSendStatus());
        assertEquals(entity.getSendTime(), dto.getSendTime());
        assertEquals(entity.getFailReason(), dto.getFailReason());
        assertEquals(entity.getTemplateId(), dto.getTemplateId());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        Notification entity1 = new Notification();
        entity1.setId(1L);
        entity1.setTitle("系统通知");
        entity1.setContent("这是一条系统通知");
        entity1.setReceiverId(1L);
        entity1.setReceiverName("管理员");
        entity1.setSenderId(2L);
        entity1.setSenderName("系统");
        entity1.setType("system");
        entity1.setChannel("email");
        entity1.setBusinessId("123456");
        entity1.setBusinessType("user");
        entity1.setReadStatus(0);
        entity1.setReadTime(LocalDateTime.now());
        entity1.setSendStatus(0);
        entity1.setSendTime(LocalDateTime.now());
        entity1.setFailReason("");
        entity1.setTemplateId(1L);
        entity1.setTenantId(1L);

        Notification entity2 = new Notification();
        entity2.setId(2L);
        entity2.setTitle("任务提醒");
        entity2.setContent("您有一个任务需要处理");
        entity2.setReceiverId(1L);
        entity2.setReceiverName("管理员");
        entity2.setSenderId(3L);
        entity2.setSenderName("任务系统");
        entity2.setType("task");
        entity2.setChannel("sms");
        entity2.setBusinessId("789012");
        entity2.setBusinessType("task");
        entity2.setReadStatus(1);
        entity2.setReadTime(LocalDateTime.now());
        entity2.setSendStatus(1);
        entity2.setSendTime(LocalDateTime.now());
        entity2.setFailReason("");
        entity2.setTemplateId(2L);
        entity2.setTenantId(1L);

        List<Notification> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<NotificationDTO> dtoList = NotificationAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getTitle(), dtoList.get(0).getTitle());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getTitle(), dtoList.get(1).getTitle());
    }
}