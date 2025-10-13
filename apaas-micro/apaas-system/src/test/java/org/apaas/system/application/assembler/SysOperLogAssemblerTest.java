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

import org.apaas.system.application.dto.SysOperLogDTO;
import org.apaas.system.domain.model.SysOperLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 操作日志装配器测试类
 *
 * @author ivan
 */
class SysOperLogAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
        // 准备测试数据
        SysOperLogDTO dto = new SysOperLogDTO();
        dto.setId(1L);
        dto.setTitle("测试操作");
        dto.setBusinessType(1);
        dto.setMethod("testMethod");
        dto.setRequestMethod("GET");
        dto.setOperatorType("admin");
        dto.setOperName("管理员");
        dto.setDeptName("技术部");
        dto.setOperUrl("/test");
        dto.setOperIp("127.0.0.1");
        dto.setOperLocation("本地");
        dto.setOperParam("param1=value1");
        dto.setJsonResult("{\"result\":\"success\"}");
        dto.setStatus(0);
        dto.setErrorMsg("");
        dto.setOperTime(LocalDateTime.now());
        dto.setTenantId(1L);

        // 执行转换
        SysOperLog entity = SysOperLogAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getBusinessType(), entity.getBusinessType());
        assertEquals(dto.getMethod(), entity.getMethod());
        assertEquals(dto.getRequestMethod(), entity.getRequestMethod());
        assertEquals(dto.getOperatorType(), entity.getOperatorType());
        assertEquals(dto.getOperName(), entity.getOperName());
        assertEquals(dto.getDeptName(), entity.getDeptName());
        assertEquals(dto.getOperUrl(), entity.getOperUrl());
        assertEquals(dto.getOperIp(), entity.getOperIp());
        assertEquals(dto.getOperLocation(), entity.getOperLocation());
        assertEquals(dto.getOperParam(), entity.getOperParam());
        assertEquals(dto.getJsonResult(), entity.getJsonResult());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getErrorMsg(), entity.getErrorMsg());
        assertEquals(dto.getOperTime(), entity.getOperTime());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        SysOperLog entity = new SysOperLog();
        entity.setId(1L);
        entity.setTitle("测试操作");
        entity.setBusinessType(1);
        entity.setMethod("testMethod");
        entity.setRequestMethod("GET");
        entity.setOperatorType("admin");
        entity.setOperName("管理员");
        entity.setDeptName("技术部");
        entity.setOperUrl("/test");
        entity.setOperIp("127.0.0.1");
        entity.setOperLocation("本地");
        entity.setOperParam("param1=value1");
        entity.setJsonResult("{\"result\":\"success\"}");
        entity.setStatus(0);
        entity.setErrorMsg("");
        entity.setOperTime(LocalDateTime.now());
        entity.setTenantId(1L);

        // 执行转换
        SysOperLogDTO dto = SysOperLogAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getBusinessType(), dto.getBusinessType());
        assertEquals(entity.getMethod(), dto.getMethod());
        assertEquals(entity.getRequestMethod(), dto.getRequestMethod());
        assertEquals(entity.getOperatorType(), dto.getOperatorType());
        assertEquals(entity.getOperName(), dto.getOperName());
        assertEquals(entity.getDeptName(), dto.getDeptName());
        assertEquals(entity.getOperUrl(), dto.getOperUrl());
        assertEquals(entity.getOperIp(), dto.getOperIp());
        assertEquals(entity.getOperLocation(), dto.getOperLocation());
        assertEquals(entity.getOperParam(), dto.getOperParam());
        assertEquals(entity.getJsonResult(), dto.getJsonResult());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getErrorMsg(), dto.getErrorMsg());
        assertEquals(entity.getOperTime(), dto.getOperTime());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        SysOperLog entity1 = new SysOperLog();
        entity1.setId(1L);
        entity1.setTitle("测试操作1");
        entity1.setBusinessType(1);
        entity1.setMethod("testMethod1");
        entity1.setRequestMethod("GET");
        entity1.setOperatorType("admin");
        entity1.setOperName("管理员");
        entity1.setDeptName("技术部");
        entity1.setOperUrl("/test1");
        entity1.setOperIp("127.0.0.1");
        entity1.setOperLocation("本地");
        entity1.setOperParam("param1=value1");
        entity1.setJsonResult("{\"result\":\"success\"}");
        entity1.setStatus(0);
        entity1.setErrorMsg("");
        entity1.setOperTime(LocalDateTime.now());
        entity1.setTenantId(1L);

        SysOperLog entity2 = new SysOperLog();
        entity2.setId(2L);
        entity2.setTitle("测试操作2");
        entity2.setBusinessType(2);
        entity2.setMethod("testMethod2");
        entity2.setRequestMethod("POST");
        entity2.setOperatorType("user");
        entity2.setOperName("用户");
        entity2.setDeptName("市场部");
        entity2.setOperUrl("/test2");
        entity2.setOperIp("192.168.1.1");
        entity2.setOperLocation("远程");
        entity2.setOperParam("param2=value2");
        entity2.setJsonResult("{\"result\":\"fail\"}");
        entity2.setStatus(1);
        entity2.setErrorMsg("操作失败");
        entity2.setOperTime(LocalDateTime.now());
        entity2.setTenantId(1L);

        List<SysOperLog> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<SysOperLogDTO> dtoList = SysOperLogAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getTitle(), dtoList.get(0).getTitle());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getTitle(), dtoList.get(1).getTitle());
    }
}