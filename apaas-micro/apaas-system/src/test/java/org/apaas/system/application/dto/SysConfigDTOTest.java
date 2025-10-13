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
 * 系统配置DTO测试类
 *
 * @author ivan
 */
class SysConfigDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        SysConfigDTO dto = new SysConfigDTO();
        Long id = 1L;
        String name = "主框架页-默认皮肤样式名称";
        String configKey = "sys.index.skinName";
        String code = "sys_index_skin_name";
        String value = "skin-blue";
        Integer type = 0;
        Integer status = 0;
        String description = "蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow";
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setConfigKey(configKey);
        dto.setCode(code);
        dto.setValue(value);
        dto.setType(type);
        dto.setStatus(status);
        dto.setDescription(description);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(configKey, dto.getConfigKey());
        assertEquals(code, dto.getCode());
        assertEquals(value, dto.getValue());
        assertEquals(type, dto.getType());
        assertEquals(status, dto.getStatus());
        assertEquals(description, dto.getDescription());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        SysConfigDTO dto = new SysConfigDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        SysConfigDTO dto = new SysConfigDTO();
        dto.setId(1L);
        dto.setName("主框架页-默认皮肤样式名称");
        dto.setConfigKey("sys.index.skinName");
        dto.setCode("sys_index_skin_name");
        dto.setValue("skin-blue");
        dto.setType(0);
        dto.setStatus(0);
        dto.setDescription("蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow");
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("SysConfigDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=主框架页-默认皮肤样式名称"));
        assertTrue(toStringResult.contains("configKey=sys.index.skinName"));
    }
}