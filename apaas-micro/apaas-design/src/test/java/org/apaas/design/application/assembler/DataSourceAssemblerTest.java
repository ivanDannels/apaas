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

import org.apaas.design.application.dto.DataSourceDTO;
import org.apaas.design.domain.model.DataSource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据源装配器测试类
 *
 * @author ivan
 */
class DataSourceAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        DataSourceDTO dto = new DataSourceDTO();
        dto.setId(1L);
        dto.setName("测试数据库");
        dto.setType("postgresql");
        dto.setUrl("jdbc:postgresql://localhost:5432/test");
        dto.setUsername("testuser");
        dto.setPassword("testpass");
        dto.setDriverClass("org.postgresql.Driver");
        dto.setDescription("测试数据库连接");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        DataSource entity = DataSourceAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getUrl(), entity.getUrl());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getDriverClass(), entity.getDriverClass());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        DataSource entity = new DataSource();
        entity.setId(1L);
        entity.setName("测试数据库");
        entity.setType("postgresql");
        entity.setUrl("jdbc:postgresql://localhost:5432/test");
        entity.setUsername("testuser");
        entity.setPassword("testpass");
        entity.setDriverClass("org.postgresql.Driver");
        entity.setDescription("测试数据库连接");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        DataSourceDTO dto = DataSourceAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getUrl(), dto.getUrl());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getPassword(), dto.getPassword());
        assertEquals(entity.getDriverClass(), dto.getDriverClass());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        DataSource entity1 = new DataSource();
        entity1.setId(1L);
        entity1.setName("PostgreSQL数据库");
        entity1.setType("postgresql");
        entity1.setUrl("jdbc:postgresql://localhost:5432/test1");
        entity1.setUsername("user1");
        entity1.setPassword("pass1");
        entity1.setDriverClass("org.postgresql.Driver");
        entity1.setDescription("PostgreSQL数据库连接1");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        DataSource entity2 = new DataSource();
        entity2.setId(2L);
        entity2.setName("MySQL数据库");
        entity2.setType("mysql");
        entity2.setUrl("jdbc:mysql://localhost:3306/test2");
        entity2.setUsername("user2");
        entity2.setPassword("pass2");
        entity2.setDriverClass("com.mysql.cj.jdbc.Driver");
        entity2.setDescription("MySQL数据库连接2");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<DataSource> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<DataSourceDTO> dtoList = DataSourceAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}