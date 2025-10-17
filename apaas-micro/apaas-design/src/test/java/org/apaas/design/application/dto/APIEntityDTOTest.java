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

import static org.junit.jupiter.api.Assertions.*;

/**
 * API实体DTO测试类
 *
 * @author ivan
 */
class APIEntityDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        APIEntityDTO dto = new APIEntityDTO();
        Long id = 1L;
        String name = "用户管理API";
        String path = "/api/users";
        String method = "GET";
        String description = "用户管理相关API";
        String sqlScript = "SELECT * FROM sys_user";
        String version = "v1.0";
        Integer status = 0;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setPath(path);
        dto.setMethod(method);
        dto.setDescription(description);
        dto.setSqlScript(sqlScript);
        dto.setVersion(version);
        dto.setStatus(status);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(path, dto.getPath());
        assertEquals(method, dto.getMethod());
        assertEquals(description, dto.getDescription());
        assertEquals(sqlScript, dto.getSqlScript());
        assertEquals(version, dto.getVersion());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        APIEntityDTO dto = new APIEntityDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
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

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("APIEntityDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=用户管理API"));
        assertTrue(toStringResult.contains("path=/api/users"));
    }
}