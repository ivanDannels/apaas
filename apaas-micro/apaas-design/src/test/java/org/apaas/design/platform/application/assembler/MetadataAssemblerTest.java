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

import org.apaas.design.platform.application.dto.MetadataDTO;
import org.apaas.design.platform.domain.model.Metadata;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 元数据装配器测试类
 *
 * @author ivan
 */
class MetadataAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        MetadataDTO dto = new MetadataDTO();
        dto.setId(1L);
        dto.setName("测试元数据");
        dto.setType("table");
        dto.setContent("{\"name\":\"test\",\"columns\":[{\"name\":\"id\",\"type\":\"BIGINT\"}]}");
        dto.setDescription("测试元数据描述");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        Metadata entity = MetadataAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        Metadata entity = new Metadata();
        entity.setId(1L);
        entity.setName("测试元数据");
        entity.setType("table");
        entity.setContent("{\"name\":\"test\",\"columns\":[{\"name\":\"id\",\"type\":\"BIGINT\"}]}");
        entity.setDescription("测试元数据描述");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        MetadataDTO dto = MetadataAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        Metadata entity1 = new Metadata();
        entity1.setId(1L);
        entity1.setName("测试元数据1");
        entity1.setType("table");
        entity1.setContent("{\"name\":\"test1\",\"columns\":[{\"name\":\"id\",\"type\":\"BIGINT\"}]}");
        entity1.setDescription("测试元数据描述1");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        Metadata entity2 = new Metadata();
        entity2.setId(2L);
        entity2.setName("测试元数据2");
        entity2.setType("view");
        entity2.setContent("{\"name\":\"test2\",\"columns\":[{\"name\":\"name\",\"type\":\"VARCHAR\"}]}");
        entity2.setDescription("测试元数据描述2");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<Metadata> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<MetadataDTO> dtoList = MetadataAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}