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

import org.apaas.design.application.dto.ApiDefinitionDTO;
import org.apaas.design.domain.model.ApiDefinition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * API定义装配器测试类
 *
 * @author ivan
 */
class ApiDefinitionAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        ApiDefinition entity = ApiDefinitionAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPath(), entity.getPath());
        assertEquals(dto.getMethod(), entity.getMethod());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getSqlScript(), entity.getSqlScript());
        assertEquals(dto.getDataSourceId(), entity.getDataSourceId());
        assertEquals(dto.getRequestParams(), entity.getRequestParams());
        assertEquals(dto.getResponseStructure(), entity.getResponseStructure());
        assertEquals(dto.getVersion(), entity.getVersion());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        ApiDefinition entity = new ApiDefinition();
        entity.setId(1L);
        entity.setName("用户列表API");
        entity.setPath("/api/users");
        entity.setMethod("GET");
        entity.setDescription("获取用户列表");
        entity.setSqlScript("SELECT * FROM sys_user");
        entity.setDataSourceId(1L);
        entity.setRequestParams("[{\"name\":\"page\",\"type\":\"int\"},{\"name\":\"size\",\"type\":\"int\"}]");
        entity.setResponseStructure("{\"users\":[{\"id\":\"long\",\"name\":\"string\"}]}");
        entity.setVersion("v1.0");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        ApiDefinitionDTO dto = ApiDefinitionAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getPath(), dto.getPath());
        assertEquals(entity.getMethod(), dto.getMethod());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getSqlScript(), dto.getSqlScript());
        assertEquals(entity.getDataSourceId(), dto.getDataSourceId());
        assertEquals(entity.getRequestParams(), dto.getRequestParams());
        assertEquals(entity.getResponseStructure(), dto.getResponseStructure());
        assertEquals(entity.getVersion(), dto.getVersion());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        ApiDefinition entity1 = new ApiDefinition();
        entity1.setId(1L);
        entity1.setName("用户列表API");
        entity1.setPath("/api/users");
        entity1.setMethod("GET");
        entity1.setDescription("获取用户列表");
        entity1.setSqlScript("SELECT * FROM sys_user");
        entity1.setDataSourceId(1L);
        entity1.setRequestParams("[{\"name\":\"page\",\"type\":\"int\"},{\"name\":\"size\",\"type\":\"int\"}]");
        entity1.setResponseStructure("{\"users\":[{\"id\":\"long\",\"name\":\"string\"}]}");
        entity1.setVersion("v1.0");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        ApiDefinition entity2 = new ApiDefinition();
        entity2.setId(2L);
        entity2.setName("创建用户API");
        entity2.setPath("/api/users");
        entity2.setMethod("POST");
        entity2.setDescription("创建新用户");
        entity2.setSqlScript("INSERT INTO sys_user (name, email) VALUES (:name, :email)");
        entity2.setDataSourceId(1L);
        entity2.setRequestParams("[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"email\",\"type\":\"string\"}]");
        entity2.setResponseStructure("{\"id\":\"long\",\"name\":\"string\",\"email\":\"string\"}");
        entity2.setVersion("v1.0");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<ApiDefinition> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<ApiDefinitionDTO> dtoList = ApiDefinitionAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}