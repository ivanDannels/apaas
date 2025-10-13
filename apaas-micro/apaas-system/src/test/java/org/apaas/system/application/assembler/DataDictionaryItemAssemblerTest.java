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

import org.apaas.system.application.dto.DataDictionaryItemDTO;
import org.apaas.system.domain.model.DataDictionaryItem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据字典项装配器测试类
 *
 * @author ivan
 */
class DataDictionaryItemAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        DataDictionaryItem entity = DataDictionaryItemAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getDictId(), entity.getDictId());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getValue(), entity.getValue());
        assertEquals(dto.getSort(), entity.getSort());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        DataDictionaryItem entity = new DataDictionaryItem();
        entity.setId(1L);
        entity.setDictId(1L);
        entity.setCode("active");
        entity.setName("激活");
        entity.setValue("0");
        entity.setSort(1);
        entity.setStatus(0);
        entity.setDescription("用户账号已激活");
        entity.setTenantId(1L);

        // 执行转换
        DataDictionaryItemDTO dto = DataDictionaryItemAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getDictId(), dto.getDictId());
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getValue(), dto.getValue());
        assertEquals(entity.getSort(), dto.getSort());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        DataDictionaryItem entity1 = new DataDictionaryItem();
        entity1.setId(1L);
        entity1.setDictId(1L);
        entity1.setCode("active");
        entity1.setName("激活");
        entity1.setValue("0");
        entity1.setSort(1);
        entity1.setStatus(0);
        entity1.setDescription("用户账号已激活");
        entity1.setTenantId(1L);

        DataDictionaryItem entity2 = new DataDictionaryItem();
        entity2.setId(2L);
        entity2.setDictId(1L);
        entity2.setCode("inactive");
        entity2.setName("未激活");
        entity2.setValue("1");
        entity2.setSort(2);
        entity2.setStatus(0);
        entity2.setDescription("用户账号未激活");
        entity2.setTenantId(1L);

        List<DataDictionaryItem> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<DataDictionaryItemDTO> dtoList = DataDictionaryItemAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}