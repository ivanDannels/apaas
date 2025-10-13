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
 * 数据模型DTO测试类
 *
 * @author ivan
 */
class ModelDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        ModelDTO dto = new ModelDTO();
        Long id = 1L;
        String name = "测试模型";
        String type = "entity";
        String content = "{\"name\":\"User\",\"fields\":[{\"name\":\"id\",\"type\":\"Long\"}]}";
        String description = "测试模型描述";
        Integer status = 0;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setContent(content);
        dto.setDescription(description);
        dto.setStatus(status);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(type, dto.getType());
        assertEquals(content, dto.getContent());
        assertEquals(description, dto.getDescription());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        ModelDTO dto = new ModelDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        ModelDTO dto = new ModelDTO();
        dto.setId(1L);
        dto.setName("测试模型");
        dto.setType("entity");
        dto.setContent("{\"name\":\"User\",\"fields\":[{\"name\":\"id\",\"type\":\"Long\"}]}");
        dto.setDescription("测试模型描述");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("ModelDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=测试模型"));
        assertTrue(toStringResult.contains("type=entity"));
    }
}