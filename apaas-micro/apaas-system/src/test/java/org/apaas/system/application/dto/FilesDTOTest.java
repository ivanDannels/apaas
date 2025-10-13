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
 * 文件DTO测试类
 *
 * @author ivan
 */
class FilesDTOTest {

    @Test
    void testGettersAndSetters() {
        // 准备测试数据
        FilesDTO dto = new FilesDTO();
        Long id = 1L;
        String fileName = "test.txt";
        String fileUrl = "http://example.com/test.txt";
        String fileType = "text/plain";
        Long fileSize = 1024L;
        String filePath = "/path/to/test.txt";
        String fileMd5 = "d41d8cd98f00b204e9800998ecf8427e";
        Long tenantId = 1L;

        // 设置属性值
        dto.setId(id);
        dto.setFileName(fileName);
        dto.setFileUrl(fileUrl);
        dto.setFileType(fileType);
        dto.setFileSize(fileSize);
        dto.setFilePath(filePath);
        dto.setFileMd5(fileMd5);
        dto.setTenantId(tenantId);

        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(fileName, dto.getFileName());
        assertEquals(fileUrl, dto.getFileUrl());
        assertEquals(fileType, dto.getFileType());
        assertEquals(fileSize, dto.getFileSize());
        assertEquals(filePath, dto.getFilePath());
        assertEquals(fileMd5, dto.getFileMd5());
        assertEquals(tenantId, dto.getTenantId());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        FilesDTO dto = new FilesDTO();
        assertNotNull(dto);
    }

    @Test
    void testToString() {
        // 准备测试数据
        FilesDTO dto = new FilesDTO();
        dto.setId(1L);
        dto.setFileName("test.txt");
        dto.setFileUrl("http://example.com/test.txt");
        dto.setFileType("text/plain");
        dto.setFileSize(1024L);
        dto.setFilePath("/path/to/test.txt");
        dto.setFileMd5("d41d8cd98f00b204e9800998ecf8427e");
        dto.setTenantId(1L);

        // 验证toString方法
        String toStringResult = dto.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("FilesDTO"));
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("fileName=test.txt"));
        assertTrue(toStringResult.contains("fileUrl=http://example.com/test.txt"));
    }
}