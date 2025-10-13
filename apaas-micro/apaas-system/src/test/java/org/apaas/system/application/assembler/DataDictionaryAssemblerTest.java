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

import org.apaas.system.application.dto.DataDictionaryDTO;
import org.apaas.system.domain.model.DataDictionary;
import org.apaas.system.domain.model.DataDictionaryAggregate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据字典装配器测试类
 *
 * @author ivan
 */
class DataDictionaryAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        DataDictionaryDTO dto = new DataDictionaryDTO();
        dto.setId(1L);
        dto.setName("用户状态");
        dto.setCode("user_status");
        dto.setType(0);
        dto.setStatus(0);
        dto.setDescription("用户状态字典");
        dto.setTenantId(1L);

        // 执行转换
        DataDictionary entity = DataDictionaryAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        DataDictionary entity = new DataDictionary();
        entity.setId(1L);
        entity.setName("用户状态");
        entity.setCode("user_status");
        entity.setType(0);
        entity.setStatus(0);
        entity.setDescription("用户状态字典");
        entity.setTenantId(1L);

        // 执行转换
        DataDictionaryDTO dto = DataDictionaryAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertAggregateToDto() {
        // 准备测试数据
        DataDictionaryAggregate aggregate = new DataDictionaryAggregate();
        aggregate.setId(1L);
        aggregate.setName("用户状态");
        aggregate.setCode("user_status");
        aggregate.setType(0);
        aggregate.setStatus(0);
        aggregate.setDescription("用户状态字典");
        aggregate.setTenantId(1L);

        // 执行转换
        DataDictionaryDTO dto = DataDictionaryAssembler.INSTANCE.convertAggregateToDto(aggregate);

        // 验证结果
        assertNotNull(dto);
        assertEquals(aggregate.getId(), dto.getId());
        assertEquals(aggregate.getName(), dto.getName());
        assertEquals(aggregate.getCode(), dto.getCode());
        assertEquals(aggregate.getType(), dto.getType());
        assertEquals(aggregate.getStatus(), dto.getStatus());
        assertEquals(aggregate.getDescription(), dto.getDescription());
        assertEquals(aggregate.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertDtoToAggregate() {
        // 准备测试数据
        DataDictionaryDTO dto = new DataDictionaryDTO();
        dto.setId(1L);
        dto.setName("用户状态");
        dto.setCode("user_status");
        dto.setType(0);
        dto.setStatus(0);
        dto.setDescription("用户状态字典");
        dto.setTenantId(1L);

        // 执行转换
        DataDictionaryAggregate aggregate = DataDictionaryAssembler.INSTANCE.convertDtoToAggregate(dto);

        // 验证结果
        assertNotNull(aggregate);
        assertEquals(dto.getId(), aggregate.getId());
        assertEquals(dto.getName(), aggregate.getName());
        assertEquals(dto.getCode(), aggregate.getCode());
        assertEquals(dto.getType(), aggregate.getType());
        assertEquals(dto.getStatus(), aggregate.getStatus());
        assertEquals(dto.getDescription(), aggregate.getDescription());
        assertEquals(dto.getTenantId(), aggregate.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        DataDictionary entity1 = new DataDictionary();
        entity1.setId(1L);
        entity1.setName("用户状态");
        entity1.setCode("user_status");
        entity1.setType(0);
        entity1.setStatus(0);
        entity1.setDescription("用户状态字典");
        entity1.setTenantId(1L);

        DataDictionary entity2 = new DataDictionary();
        entity2.setId(2L);
        entity2.setName("性别");
        entity2.setCode("gender");
        entity2.setType(0);
        entity2.setStatus(0);
        entity2.setDescription("性别字典");
        entity2.setTenantId(1L);

        List<DataDictionary> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<DataDictionaryDTO> dtoList = DataDictionaryAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }

    @Test
    void testConvertAggregateListToDtoList() {
        // 准备测试数据
        DataDictionaryAggregate aggregate1 = new DataDictionaryAggregate();
        aggregate1.setId(1L);
        aggregate1.setName("用户状态");
        aggregate1.setCode("user_status");
        aggregate1.setType(0);
        aggregate1.setStatus(0);
        aggregate1.setDescription("用户状态字典");
        aggregate1.setTenantId(1L);

        DataDictionaryAggregate aggregate2 = new DataDictionaryAggregate();
        aggregate2.setId(2L);
        aggregate2.setName("性别");
        aggregate2.setCode("gender");
        aggregate2.setType(0);
        aggregate2.setStatus(0);
        aggregate2.setDescription("性别字典");
        aggregate2.setTenantId(1L);

        List<DataDictionaryAggregate> aggregateList = Arrays.asList(aggregate1, aggregate2);

        // 执行转换
        List<DataDictionaryDTO> dtoList = DataDictionaryAssembler.INSTANCE.convertAggregateListToDtoList(aggregateList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(aggregate1.getId(), dtoList.get(0).getId());
        assertEquals(aggregate1.getName(), dtoList.get(0).getName());
        assertEquals(aggregate2.getId(), dtoList.get(1).getId());
        assertEquals(aggregate2.getName(), dtoList.get(1).getName());
    }
}