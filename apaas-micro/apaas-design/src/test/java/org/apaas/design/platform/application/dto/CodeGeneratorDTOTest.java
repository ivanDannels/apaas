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
package org.apaas.design.platform.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 代码生成器DTO测试类
 *
 * @author ivan
 */
class CodeGeneratorDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        CodeGeneratorDTO dto = new CodeGeneratorDTO();
        Long id = 1L;
        String name = "用户实体生成器";
        String description = "生成用户相关的实体类代码";
        Long templateId = 1L;
        Long metadataId = 1L;
        String outputPath = "/src/main/java/com/example/entity";
        Integer type = 0;
        Integer status = 0;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setTemplateId(templateId);
        dto.setMetadataId(metadataId);
        dto.setOutputPath(outputPath);
        dto.setType(type);
        dto.setStatus(status);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(templateId, dto.getTemplateId());
        assertEquals(metadataId, dto.getMetadataId());
        assertEquals(outputPath, dto.getOutputPath());
        assertEquals(type, dto.getType());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        CodeGeneratorDTO dto = new CodeGeneratorDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
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

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("CodeGeneratorDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=用户实体生成器"));
        assertTrue(toStringResult.contains("description=生成用户相关的实体类代码"));
    }
}