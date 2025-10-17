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
 * 数据可视化DTO测试类
 *
 * @author ivan
 */
class VisualizationDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        VisualizationDTO dto = new VisualizationDTO();
        Long id = 1L;
        String name = "用户统计图表";
        Integer type = 0;
        Long dataSourceId = 1L;
        String querySql = "SELECT date, count FROM user_stats";
        String config = "{\"type\":\"line\",\"xAxis\":\"date\",\"yAxis\":\"count\"}";
        String description = "用户增长统计图表";
        Integer status = 0;
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setDataSourceId(dataSourceId);
        dto.setQuerySql(querySql);
        dto.setConfig(config);
        dto.setDescription(description);
        dto.setStatus(status);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(type, dto.getType());
        assertEquals(dataSourceId, dto.getDataSourceId());
        assertEquals(querySql, dto.getQuerySql());
        assertEquals(config, dto.getConfig());
        assertEquals(description, dto.getDescription());
        assertEquals(status, dto.getStatus());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        VisualizationDTO dto = new VisualizationDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
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

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("VisualizationDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("name=用户统计图表"));
        assertTrue(toStringResult.contains("querySql=SELECT date, count FROM user_stats"));
    }
}