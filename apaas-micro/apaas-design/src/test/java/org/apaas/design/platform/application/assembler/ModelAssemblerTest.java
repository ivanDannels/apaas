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

import org.apaas.design.platform.application.dto.ModelDTO;
import org.apaas.design.platform.domain.model.Model;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据模型装配器测试类
 *
 * @author ivan
 */
class ModelAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        ModelDTO dto = new ModelDTO();
        dto.setId(1L);
        dto.setName("测试模型");
        dto.setType("entity");
        dto.setContent("{\"name\":\"User\",\"fields\":[{\"name\":\"id\",\"type\":\"Long\"}]}");
        dto.setDescription("测试模型描述");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        Model entity = ModelAssembler.INSTANCE.convertDtoToEntity(dto);

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
        Model entity = new Model();
        entity.setId(1L);
        entity.setName("测试模型");
        entity.setType("entity");
        entity.setContent("{\"name\":\"User\",\"fields\":[{\"name\":\"id\",\"type\":\"Long\"}]}");
        entity.setDescription("测试模型描述");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        ModelDTO dto = ModelAssembler.INSTANCE.convertEntityToDto(entity);

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
        Model entity1 = new Model();
        entity1.setId(1L);
        entity1.setName("测试模型1");
        entity1.setType("entity");
        entity1.setContent("{\"name\":\"User\",\"fields\":[{\"name\":\"id\",\"type\":\"Long\"}]}");
        entity1.setDescription("测试模型描述1");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        Model entity2 = new Model();
        entity2.setId(2L);
        entity2.setName("测试模型2");
        entity2.setType("dto");
        entity2.setContent("{\"name\":\"UserDTO\",\"fields\":[{\"name\":\"name\",\"type\":\"String\"}]}");
        entity2.setDescription("测试模型描述2");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<Model> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<ModelDTO> dtoList = ModelAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}