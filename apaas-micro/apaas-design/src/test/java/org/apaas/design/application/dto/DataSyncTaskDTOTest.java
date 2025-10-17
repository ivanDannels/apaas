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
package org.apaas.design.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据同步任务DTO测试类
 *
 * @author ivan
 */
class DataSyncTaskDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        DataSyncTaskDTO dto = new DataSyncTaskDTO();
        Long id = 1L;
        String name = "用户数据同步任务";
        Long sourceDataSourceId = 1L;
        Long targetDataSourceId = 2L;
        String syncSql = "SELECT * FROM user";
        String cronExpression = "0 0 2 * * ?";
        LocalDateTime lastExecuteTime = LocalDateTime.now();
        LocalDateTime nextExecuteTime = LocalDateTime.now().plusDays(1);
        Integer executeStatus = 0;
        Integer status = 0;
        String failReason = "";
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setSourceDataSourceId(sourceDataSourceId);
        dto.setTargetDataSourceId(targetDataSourceId);
        dto.setSyncSql(syncSql);
        dto.setCronExpression(cronExpression);
        dto.setLastExecuteTime(lastExecuteTime);
        dto.setNextExecuteTime(nextExecuteTime);
        dto.setExecuteStatus(executeStatus);
        dto.setStatus(status);
        dto.setFailReason(failReason);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(sourceDataSourceId, dto.getSourceDataSourceId());
        assertEquals(targetDataSourceId, dto.getTargetDataSourceId());
        assertEquals(syncSql, dto.getSyncSql());
        assertEquals(cronExpression, dto.getCronExpression());
        assertEquals(lastExecuteTime, dto.getLastExecuteTime());
        assertEquals(nextExecuteTime, dto.getNextExecuteTime());
        assertEquals(executeStatus, dto.getExecuteStatus());
        assertEquals(status, dto.getStatus());
        assertEquals(failReason, dto.getFailReason());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        DataSyncTaskDTO dto = new DataSyncTaskDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        DataSyncTaskDTO dto = new DataSyncTaskDTO();
        dto.setId(1L);
        dto.setName("用户数据同步任务");
        dto.setSourceDataSourceId(1L);
        dto.setTargetDataSourceId(2L);
        dto.setSyncSql("SELECT * FROM user");
        dto.setCronExpression("0 0 2 * * ?");
        dto.setLastExecuteTime(LocalDateTime.now());
        dto.setNextExecuteTime(LocalDateTime.now().plusDays(1));
        dto.setExecuteStatus(0);
        dto.setStatus(0);
        dto.setFailReason("");
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("DataSyncTaskDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=用户数据同步任务"));
        assertTrue(toStringResult.contains("syncSql=SELECT * FROM user"));
    }
}