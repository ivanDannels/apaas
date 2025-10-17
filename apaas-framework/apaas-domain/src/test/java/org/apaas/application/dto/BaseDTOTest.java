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
package org.apaas.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseDTO测试类
 * @author ivan
 */
class BaseDTOTest {
    
    @Test
    void testBaseDTOGettersAndSetters() {
        // Given
        BaseDTO<Long> dto = new BaseDTO<>();
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        dto.setId(id);
        dto.setTenantId(tenantId);
        dto.setCreator(creator);
        dto.setCreatedTime(createdTime);
        dto.setUpdater(updater);
        dto.setUpdatedTime(updatedTime);
        dto.setDeleted(deleted);
        
        // Then
        assertEquals(id, dto.getId());
        assertEquals(tenantId, dto.getTenantId());
        assertEquals(creator, dto.getCreator());
        assertEquals(createdTime, dto.getCreatedTime());
        assertEquals(updater, dto.getUpdater());
        assertEquals(updatedTime, dto.getUpdatedTime());
        assertEquals(deleted, dto.getDeleted());
    }
    
    @Test
    void testBaseDTOBuilder() {
        // Given
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        BaseDTO<Long> dto = BaseDTO.<Long>builder().id(id).tenantId(tenantId).creator(creator).createdTime(createdTime).updater(updater).updatedTime(updatedTime).deleted(deleted).build();
        
        // Then
        assertEquals(id, dto.getId());
        assertEquals(tenantId, dto.getTenantId());
        assertEquals(creator, dto.getCreator());
        assertEquals(createdTime, dto.getCreatedTime());
        assertEquals(updater, dto.getUpdater());
        assertEquals(updatedTime, dto.getUpdatedTime());
        assertEquals(deleted, dto.getDeleted());
    }
    
    @Test
    void testBaseDTONoArgsConstructor() {
        // When
        BaseDTO<Long> dto = new BaseDTO<>();
        
        // Then
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getTenantId());
        assertNull(dto.getCreator());
        assertNull(dto.getCreatedTime());
        assertNull(dto.getUpdater());
        assertNull(dto.getUpdatedTime());
        assertEquals(Integer.valueOf(0), dto.getDeleted());
    }
    
    @Test
    void testBaseDTOAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        BaseDTO<Long> dto = new BaseDTO<>(id, tenantId, creator, createdTime, updater, updatedTime, deleted);
        
        // Then
        assertEquals(id, dto.getId());
        assertEquals(tenantId, dto.getTenantId());
        assertEquals(creator, dto.getCreator());
        assertEquals(createdTime, dto.getCreatedTime());
        assertEquals(updater, dto.getUpdater());
        assertEquals(updatedTime, dto.getUpdatedTime());
        assertEquals(deleted, dto.getDeleted());
    }
    
    @Test
    void testSerializable() {
        // When
        BaseDTO<Long> dto = new BaseDTO<>();
        
        // Then
        assertTrue(dto instanceof java.io.Serializable);
    }
}