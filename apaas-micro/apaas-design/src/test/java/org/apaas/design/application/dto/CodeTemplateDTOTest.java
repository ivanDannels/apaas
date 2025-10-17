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
 * 代码模板DTO测试类
 *
 * @author ivan
 */
class CodeTemplateDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        CodeTemplateDTO dto = new CodeTemplateDTO();
        Long id = 1L;
        String name = "Java实体类模板";
        String content = "public class ${className} {\n${fields}\n}";
        String language = "java";
        String description = "生成Java实体类的模板";
        Integer status = 0;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setContent(content);
        dto.setLanguage(language);
        dto.setDescription(description);
        dto.setStatus(status);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(content, dto.getContent());
        assertEquals(language, dto.getLanguage());
        assertEquals(description, dto.getDescription());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        CodeTemplateDTO dto = new CodeTemplateDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        CodeTemplateDTO dto = new CodeTemplateDTO();
        dto.setId(1L);
        dto.setName("Java实体类模板");
        dto.setContent("public class ${className} {\n${fields}\n}");
        dto.setLanguage("java");
        dto.setDescription("生成Java实体类的模板");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("CodeTemplateDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=Java实体类模板"));
        assertTrue(toStringResult.contains("language=java"));
    }
}