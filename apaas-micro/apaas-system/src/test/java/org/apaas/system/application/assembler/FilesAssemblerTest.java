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

import org.apaas.system.application.dto.FilesDTO;
import org.apaas.system.domain.model.Files;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文件装配器测试类
 *
 * @author ivan
 */
class FilesAssemblerTest {

    @Test
    void testConvertDtoToEntity() {
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

        // 执行转换
        Files entity = FilesAssembler.INSTANCE.convertDtoToEntity(dto);

        // 验证结果
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFileName(), entity.getFileName());
        assertEquals(dto.getFileUrl(), entity.getFileUrl());
        assertEquals(dto.getFileType(), entity.getFileType());
        assertEquals(dto.getFileSize(), entity.getFileSize());
        assertEquals(dto.getFilePath(), entity.getFilePath());
        assertEquals(dto.getFileMd5(), entity.getFileMd5());
        assertEquals(dto.getTenantId(), entity.getTenantId());
    }

    @Test
    void testConvertEntityToDto() {
        // 准备测试数据
        Files entity = new Files();
        entity.setId(1L);
        entity.setFileName("test.txt");
        entity.setFileUrl("http://example.com/test.txt");
        entity.setFileType("text/plain");
        entity.setFileSize(1024L);
        entity.setFilePath("/path/to/test.txt");
        entity.setFileMd5("d41d8cd98f00b204e9800998ecf8427e");
        entity.setTenantId(1L);

        // 执行转换
        FilesDTO dto = FilesAssembler.INSTANCE.convertEntityToDto(entity);

        // 验证结果
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getFileName(), dto.getFileName());
        assertEquals(entity.getFileUrl(), dto.getFileUrl());
        assertEquals(entity.getFileType(), dto.getFileType());
        assertEquals(entity.getFileSize(), dto.getFileSize());
        assertEquals(entity.getFilePath(), dto.getFilePath());
        assertEquals(entity.getFileMd5(), dto.getFileMd5());
        assertEquals(entity.getTenantId(), dto.getTenantId());
    }

    @Test
    void testConvertEntityListToDtoList() {
        // 准备测试数据
        Files entity1 = new Files();
        entity1.setId(1L);
        entity1.setFileName("test1.txt");
        entity1.setFileUrl("http://example.com/test1.txt");
        entity1.setFileType("text/plain");
        entity1.setFileSize(1024L);
        entity1.setFilePath("/path/to/test1.txt");
        entity1.setFileMd5("d41d8cd98f00b204e9800998ecf8427e");
        entity1.setTenantId(1L);

        Files entity2 = new Files();
        entity2.setId(2L);
        entity2.setFileName("test2.txt");
        entity2.setFileUrl("http://example.com/test2.txt");
        entity2.setFileType("text/plain");
        entity2.setFileSize(2048L);
        entity2.setFilePath("/path/to/test2.txt");
        entity2.setFileMd5("c41d8cd98f00b204e9800998ecf8427e");
        entity2.setTenantId(1L);

        List<Files> entityList = Arrays.asList(entity1, entity2);

        // 执行转换
        List<FilesDTO> dtoList = FilesAssembler.INSTANCE.convertEntityListToDtoList(entityList);

        // 验证结果
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(entity1.getId(), dtoList.get(0).getId());
        assertEquals(entity1.getFileName(), dtoList.get(0).getFileName());
        assertEquals(entity2.getId(), dtoList.get(1).getId());
        assertEquals(entity2.getFileName(), dtoList.get(1).getFileName());
    }
}