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
package org.apaas.design.application.assembler;

import org.apaas.design.application.dto.APIEntityDTO;
import org.apaas.design.domain.model.APIEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * API实体装配器测试类
 *
 * @author ivan
 */
class APIEntityAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        APIEntityDTO dto = new APIEntityDTO();
        dto.setId(1L);
        dto.setName("用户管理API");
        dto.setPath("/api/users");
        dto.setMethod("GET");
        dto.setDescription("用户管理相关API");
        dto.setSqlScript("SELECT * FROM sys_user");
        dto.setVersion("v1.0");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        APIEntity entity = APIEntityAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPath(), entity.getPath());
        assertEquals(dto.getMethod(), entity.getMethod());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getSqlScript(), entity.getSqlScript());
        assertEquals(dto.getVersion(), entity.getVersion());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        APIEntity entity = new APIEntity();
        entity.setId(1L);
        entity.setName("用户管理API");
        entity.setPath("/api/users");
        entity.setMethod("GET");
        entity.setDescription("用户管理相关API");
        entity.setSqlScript("SELECT * FROM sys_user");
        entity.setVersion("v1.0");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        APIEntityDTO dto = APIEntityAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getPath(), dto.getPath());
        assertEquals(entity.getMethod(), dto.getMethod());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getSqlScript(), dto.getSqlScript());
        assertEquals(entity.getVersion(), dto.getVersion());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        APIEntity entity1 = new APIEntity();
        entity1.setId(1L);
        entity1.setName("用户管理API");
        entity1.setPath("/api/users");
        entity1.setMethod("GET");
        entity1.setDescription("用户管理相关API");
        entity1.setSqlScript("SELECT * FROM sys_user");
        entity1.setVersion("v1.0");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        APIEntity entity2 = new APIEntity();
        entity2.setId(2L);
        entity2.setName("订单管理API");
        entity2.setPath("/api/orders");
        entity2.setMethod("POST");
        entity2.setDescription("订单管理相关API");
        entity2.setSqlScript("INSERT INTO sys_order (user_id, product_id) VALUES (:userId, :productId)");
        entity2.setVersion("v1.0");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<APIEntity> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<APIEntityDTO> dtoList = APIEntityAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}