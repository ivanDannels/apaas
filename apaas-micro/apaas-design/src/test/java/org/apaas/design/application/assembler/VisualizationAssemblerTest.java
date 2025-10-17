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

import org.apaas.design.application.dto.VisualizationDTO;
import org.apaas.design.domain.model.Visualization;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据可视化装配器测试类
 *
 * @author ivan
 */
class VisualizationAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        VisualizationDTO dto = new VisualizationDTO();
        dto.setId(1L);
        dto.setName("用户统计图表");
        dto.setType(0);
        dto.setDataSourceId(1L);
        dto.setQuerySql("SELECT date, count FROM user_stats");
        dto.setConfig("{\"type\":\"line\",\"xAxis\":\"date\",\"yAxis\":\"count\"}");
        dto.setDescription("用户增长统计图表");
        dto.setStatus(0);
        dto.setTenantId(1L);

        // 执行转换
        Visualization entity = VisualizationAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getDataSourceId(), entity.getDataSourceId());
        assertEquals(dto.getQuerySql(), entity.getQuerySql());
        assertEquals(dto.getConfig(), entity.getConfig());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        Visualization entity = new Visualization();
        entity.setId(1L);
        entity.setName("用户统计图表");
        entity.setType(0);
        entity.setDataSourceId(1L);
        entity.setQuerySql("SELECT date, count FROM user_stats");
        entity.setConfig("{\"type\":\"line\",\"xAxis\":\"date\",\"yAxis\":\"count\"}");
        entity.setDescription("用户增长统计图表");
        entity.setStatus(0);
        entity.setTenantId(1L);

        // 执行转换
        VisualizationDTO dto = VisualizationAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getDataSourceId(), dto.getDataSourceId());
        assertEquals(entity.getQuerySql(), dto.getQuerySql());
        assertEquals(entity.getConfig(), dto.getConfig());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        Visualization entity1 = new Visualization();
        entity1.setId(1L);
        entity1.setName("用户统计图表");
        entity1.setType(0);
        entity1.setDataSourceId(1L);
        entity1.setQuerySql("SELECT date, count FROM user_stats");
        entity1.setConfig("{\"type\":\"line\",\"xAxis\":\"date\",\"yAxis\":\"count\"}");
        entity1.setDescription("用户增长统计图表");
        entity1.setStatus(0);
        entity1.setTenantId(1L);

        Visualization entity2 = new Visualization();
        entity2.setId(2L);
        entity2.setName("订单统计图表");
        entity2.setType(1);
        entity2.setDataSourceId(1L);
        entity2.setQuerySql("SELECT product, count FROM order_stats");
        entity2.setConfig("{\"type\":\"bar\",\"xAxis\":\"product\",\"yAxis\":\"count\"}");
        entity2.setDescription("订单统计图表");
        entity2.setStatus(1);
        entity2.setTenantId(1L);

        List<Visualization> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<VisualizationDTO> dtoList = VisualizationAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getName(), dtoList.get(0).getName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getName(), dtoList.get(1).getName());
    }
}