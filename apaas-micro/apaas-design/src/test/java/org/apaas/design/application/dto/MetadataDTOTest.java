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
 * 元数据DTO测试类
 *
 * @author ivan
 */
class MetadataDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        MetadataDTO dto = new MetadataDTO();
        Long id = 1L;
        String name = "测试元数据";
        String type = "table";
        String content = "{\"name\":\"test\",\"columns\":[{\"name\":\"id\",\"type\":\"BIGINT\"}]}";
        String description = "测试元数据描述";
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
        MetadataDTO dto = new MetadataDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        MetadataDTO dto = new MetadataDTO();
        dto.setId(1L);
        dto.setName("测试元数据");
        dto.setType("table");
        dto.setContent("{\"name\":\"test\",\"columns\":[{\"name\":\"id\",\"type\":\"BIGINT\"}]}");
        dto.setDescription("测试元数据描述");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("MetadataDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=测试元数据"));
        assertTrue(toStringResult.contains("type=table"));
    }
}