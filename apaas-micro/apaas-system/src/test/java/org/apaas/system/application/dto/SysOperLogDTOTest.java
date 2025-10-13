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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 操作日志DTO测试类
 *
 * @author ivan
 */
class SysOperLogDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        SysOperLogDTO dto = new SysOperLogDTO();
        Long id = 1L;
        String title = "测试操作";
        Integer businessType = 1;
        String method = "testMethod";
        String requestMethod = "GET";
        String operatorType = "admin";
        String operName = "管理员";
        String deptName = "技术部";
        String operUrl = "/test";
        String operIp = "127.0.0.1";
        String operLocation = "本地";
        String operParam = "param1=value1";
        String jsonResult = "{\"result\":\"success\"}";
        Integer status = 0;
        String errorMsg = "";
        LocalDateTime operTime = LocalDateTime.now();
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setTitle(title);
        dto.setBusinessType(businessType);
        dto.setMethod(method);
        dto.setRequestMethod(requestMethod);
        dto.setOperatorType(operatorType);
        dto.setOperName(operName);
        dto.setDeptName(deptName);
        dto.setOperUrl(operUrl);
        dto.setOperIp(operIp);
        dto.setOperLocation(operLocation);
        dto.setOperParam(operParam);
        dto.setJsonResult(jsonResult);
        dto.setStatus(status);
        dto.setErrorMsg(errorMsg);
        dto.setOperTime(operTime);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(title, dto.getTitle());
        assertEquals(businessType, dto.getBusinessType());
        assertEquals(method, dto.getMethod());
        assertEquals(requestMethod, dto.getRequestMethod());
        assertEquals(operatorType, dto.getOperatorType());
        assertEquals(operName, dto.getOperName());
        assertEquals(deptName, dto.getDeptName());
        assertEquals(operUrl, dto.getOperUrl());
        assertEquals(operIp, dto.getOperIp());
        assertEquals(operLocation, dto.getOperLocation());
        assertEquals(operParam, dto.getOperParam());
        assertEquals(jsonResult, dto.getJsonResult());
        assertEquals(status, dto.getStatus());
        assertEquals(errorMsg, dto.getErrorMsg());
        assertEquals(operTime, dto.getOperTime());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        SysOperLogDTO dto = new SysOperLogDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
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

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("SysOperLogDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("title=测试操作"));
        assertTrue(toStringResult.contains("method=testMethod"));
    }
}