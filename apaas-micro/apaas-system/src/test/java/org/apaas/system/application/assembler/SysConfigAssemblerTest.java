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
package org.apaas.system.application.assembler;

import org.apaas.system.application.dto.SysConfigDTO;
import org.apaas.system.domain.model.SysConfig;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 系统配置装配器测试类
 *
 * @author ivan
 */
class SysConfigAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        SysConfig entity = SysConfigAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getConfigKey(), entity.getConfigKey());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getValue(), entity.getValue());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        SysConfig entity = new SysConfig();
        entity.setId(1L);
        entity.setName("主框架页-默认皮肤样式名称");
        entity.setConfigKey("sys.index.skinName");
        entity.setCode("sys_index_skin_name");
        entity.setValue("skin-blue");
        entity.setType(0);
        entity.setStatus(0);
        entity.setDescription("蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow");
        entity.setTenantId(1L);

        // 执行转换
        SysConfigDTO dto = SysConfigAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getConfigKey(), dto.getConfigKey());
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getValue(), dto.getValue());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        SysConfig entity1 = new SysConfig();
        entity1.setId(1L);
        entity1.setName("主框架页-默认皮肤样式名称");
        entity1.setConfigKey("sys.index.skinName");
        entity1.setCode("sys_index_skin_name");
        entity1.setValue("skin-blue");
        entity1.setType(0);
        entity1.setStatus(0);
        entity1.setDescription("蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow");
        entity1.setTenantId(1L);

        SysConfig entity2 = new SysConfig();
        entity2.setId(2L);
        entity2.setName("用户管理-账号初始密码");
        entity2.setConfigKey("sys.user.initPassword");
        entity2.setCode("sys_user_init_password");
        entity2.setValue("123456");
        entity2.setType(0);
        entity2.setStatus(0);
        entity2.setDescription("初始化密码 123456");
        entity2.setTenantId(1L);

        List<SysConfig> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<SysConfigDTO> dtoList = SysConfigAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}