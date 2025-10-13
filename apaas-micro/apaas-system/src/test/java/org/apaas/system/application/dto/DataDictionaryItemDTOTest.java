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
package org.apaas.system.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据字典项DTO测试类
 *
 * @author ivan
 */
class DataDictionaryItemDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        DataDictionaryItemDTO dto = new DataDictionaryItemDTO();
        Long id = 1L;
        Long dictId = 1L;
        String code = "active";
        String name = "激活";
        String value = "0";
        Integer sort = 1;
        Integer status = 0;
        String description = "用户账号已激活";
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setDictId(dictId);
        dto.setCode(code);
        dto.setName(name);
        dto.setValue(value);
        dto.setSort(sort);
        dto.setStatus(status);
        dto.setDescription(description);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(dictId, dto.getDictId());
        assertEquals(code, dto.getCode());
        assertEquals(name, dto.getName());
        assertEquals(value, dto.getValue());
        assertEquals(sort, dto.getSort());
        assertEquals(status, dto.getStatus());
        assertEquals(description, dto.getDescription());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        DataDictionaryItemDTO dto = new DataDictionaryItemDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        DataDictionaryItemDTO dto = new DataDictionaryItemDTO();
        dto.setId(1L);
        dto.setDictId(1L);
        dto.setCode("active");
        dto.setName("激活");
        dto.setValue("0");
        dto.setSort(1);
        dto.setStatus(0);
        dto.setDescription("用户账号已激活");
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("DataDictionaryItemDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("code=active"));
        assertTrue(toStringResult.contains("name=激活"));
    }
}