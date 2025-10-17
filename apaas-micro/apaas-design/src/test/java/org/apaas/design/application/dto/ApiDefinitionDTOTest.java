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
 * API定义DTO测试类
 *
 * @author ivan
 */
class ApiDefinitionDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        ApiDefinitionDTO dto = new ApiDefinitionDTO();
        Long id = 1L;
        String name = "用户列表API";
        String path = "/api/users";
        String method = "GET";
        String description = "获取用户列表";
        String sqlScript = "SELECT * FROM sys_user";
        Long dataSourceId = 1L;
        String requestParams = "[{\"name\":\"page\",\"type\":\"int\"},{\"name\":\"size\",\"type\":\"int\"}]";
        String responseStructure = "{\"users\":[{\"id\":\"long\",\"name\":\"string\"}]}";
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
        dto.setDataSourceId(dataSourceId);
        dto.setRequestParams(requestParams);
        dto.setResponseStructure(responseStructure);
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
        assertEquals(dataSourceId, dto.getDataSourceId());
        assertEquals(requestParams, dto.getRequestParams());
        assertEquals(responseStructure, dto.getResponseStructure());
        assertEquals(version, dto.getVersion());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        ApiDefinitionDTO dto = new ApiDefinitionDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        ApiDefinitionDTO dto = new ApiDefinitionDTO();
        dto.setId(1L);
        dto.setName("用户列表API");
        dto.setPath("/api/users");
        dto.setMethod("GET");
        dto.setDescription("获取用户列表");
        dto.setSqlScript("SELECT * FROM sys_user");
        dto.setDataSourceId(1L);
        dto.setRequestParams("[{\"name\":\"page\",\"type\":\"int\"},{\"name\":\"size\",\"type\":\"int\"}]");
        dto.setResponseStructure("{\"users\":[{\"id\":\"long\",\"name\":\"string\"}]}");
        dto.setVersion("v1.0");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("ApiDefinitionDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=用户列表API"));
        assertTrue(toStringResult.contains("path=/api/users"));
    }
}