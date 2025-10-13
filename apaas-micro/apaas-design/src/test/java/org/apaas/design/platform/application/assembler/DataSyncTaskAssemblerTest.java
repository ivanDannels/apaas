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
package org.apaas.design.platform.application.assembler;

import org.apaas.design.platform.application.dto.DataSyncTaskDTO;
import org.apaas.design.platform.domain.model.DataSyncTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据同步任务装配器测试类
 *
 * @author ivan
 */
class DataSyncTaskAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        DataSyncTask entity = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getSourceDataSourceId(), entity.getSourceDataSourceId());
        assertEquals(dto.getTargetDataSourceId(), entity.getTargetDataSourceId());
        assertEquals(dto.getSyncSql(), entity.getSyncSql());
        assertEquals(dto.getCronExpression(), entity.getCronExpression());
        assertEquals(dto.getLastExecuteTime(), entity.getLastExecuteTime());
        assertEquals(dto.getNextExecuteTime(), entity.getNextExecuteTime());
        assertEquals(dto.getExecuteStatus(), entity.getExecuteStatus());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getFailReason(), entity.getFailReason());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        DataSyncTask entity = new DataSyncTask();
        entity.setId(1L);
        entity.setName("用户数据同步任务");
        entity.setSourceDataSourceId(1L);
        entity.setTargetDataSourceId(2L);
        entity.setSyncSql("SELECT * FROM user");
        entity.setCronExpression("0 0 2 * * ?");
        entity.setLastExecuteTime(LocalDateTime.now());
        entity.setNextExecuteTime(LocalDateTime.now().plusDays(1));
        entity.setExecuteStatus(0);
        entity.setStatus(0);
        entity.setFailReason("");
        entity.setTenantId(1L);

        // 执行转换
        DataSyncTaskDTO dto = DataSyncTaskAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getSourceDataSourceId(), dto.getSourceDataSourceId());
        assertEquals(entity.getTargetDataSourceId(), dto.getTargetDataSourceId());
        assertEquals(entity.getSyncSql(), dto.getSyncSql());
        assertEquals(entity.getCronExpression(), dto.getCronExpression());
        assertEquals(entity.getLastExecuteTime(), dto.getLastExecuteTime());
        assertEquals(entity.getNextExecuteTime(), dto.getNextExecuteTime());
        assertEquals(entity.getExecuteStatus(), dto.getExecuteStatus());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getFailReason(), dto.getFailReason());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        DataSyncTask entity1 = new DataSyncTask();
        entity1.setId(1L);
        entity1.setName("用户数据同步任务");
        entity1.setSourceDataSourceId(1L);
        entity1.setTargetDataSourceId(2L);
        entity1.setSyncSql("SELECT * FROM user");
        entity1.setCronExpression("0 0 2 * * ?");
        entity1.setLastExecuteTime(LocalDateTime.now());
        entity1.setNextExecuteTime(LocalDateTime.now().plusDays(1));
        entity1.setExecuteStatus(0);
        entity1.setStatus(0);
        entity1.setFailReason("");
        entity1.setTenantId(1L);

        DataSyncTask entity2 = new DataSyncTask();
        entity2.setId(2L);
        entity2.setName("订单数据同步任务");
        entity2.setSourceDataSourceId(1L);
        entity2.setTargetDataSourceId(3L);
        entity2.setSyncSql("SELECT * FROM order");
        entity2.setCronExpression("0 0 3 * * ?");
        entity2.setLastExecuteTime(LocalDateTime.now());
        entity2.setNextExecuteTime(LocalDateTime.now().plusDays(1));
        entity2.setExecuteStatus(1);
        entity2.setStatus(1);
        entity2.setFailReason("连接超时");
        entity2.setTenantId(1L);

        List<DataSyncTask> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<DataSyncTaskDTO> dtoList = DataSyncTaskAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}