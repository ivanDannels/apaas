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

import org.apaas.design.application.dto.CodeGeneratorDTO;
import org.apaas.design.domain.model.CodeGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 代码生成器装配器测试类
 *
 * @author ivan
 */
class CodeGeneratorAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        CodeGeneratorDTO dto = new CodeGeneratorDTO();
        dto.setId(1L);
        dto.setName("用户实体生成器");
        dto.setDescription("生成用户相关的实体类代码");
        dto.setTemplateId(1L);
        dto.setMetadataId(1L);
        dto.setOutputPath("/src/main/java/com/example/entity");
        dto.setType(0);
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        CodeGenerator entity = CodeGeneratorAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getTemplateId(), entity.getTemplateId());
        assertEquals(dto.getMetadataId(), entity.getMetadataId());
        assertEquals(dto.getOutputPath(), entity.getOutputPath());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        CodeGenerator entity = new CodeGenerator();
        entity.setId(1L);
        entity.setName("用户实体生成器");
        entity.setDescription("生成用户相关的实体类代码");
        entity.setTemplateId(1L);
        entity.setMetadataId(1L);
        entity.setOutputPath("/src/main/java/com/example/entity");
        entity.setType(0);
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        CodeGeneratorDTO dto = CodeGeneratorAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getTemplateId(), dto.getTemplateId());
        assertEquals(entity.getMetadataId(), dto.getMetadataId());
        assertEquals(entity.getOutputPath(), dto.getOutputPath());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        CodeGenerator entity1 = new CodeGenerator();
        entity1.setId(1L);
        entity1.setName("用户实体生成器");
        entity1.setDescription("生成用户相关的实体类代码");
        entity1.setTemplateId(1L);
        entity1.setMetadataId(1L);
        entity1.setOutputPath("/src/main/java/com/example/entity");
        entity1.setType(0);
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        CodeGenerator entity2 = new CodeGenerator();
        entity2.setId(2L);
        entity2.setName("用户DTO生成器");
        entity2.setDescription("生成用户相关的DTO类代码");
        entity2.setTemplateId(2L);
        entity2.setMetadataId(1L);
        entity2.setOutputPath("/src/main/java/com/example/dto");
        entity2.setType(1);
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<CodeGenerator> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<CodeGeneratorDTO> dtoList = CodeGeneratorAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}