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
package org.apaas.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseEntity测试类
 * @author ivan
 */
class BaseEntityTest {
    
    @Test
    void testBaseEntityGettersAndSetters() {
        // Given
        BaseEntity<Long> entity = new BaseEntity<>();
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        entity.setId(id);
        entity.setTenantId(tenantId);
        entity.setCreator(creator);
        entity.setCreatedTime(createdTime);
        entity.setUpdater(updater);
        entity.setUpdatedTime(updatedTime);
        entity.setDeleted(deleted);
        
        // Then
        assertEquals(id, entity.getId());
        assertEquals(tenantId, entity.getTenantId());
        assertEquals(creator, entity.getCreator());
        assertEquals(createdTime, entity.getCreatedTime());
        assertEquals(updater, entity.getUpdater());
        assertEquals(updatedTime, entity.getUpdatedTime());
        assertEquals(deleted, entity.getDeleted());
    }
    
    @Test
    void testBaseEntityBuilder() {
        // Given
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        BaseEntity<Long> entity = BaseEntity.<Long>builder().id(id).tenantId(tenantId).creator(creator).createdTime(createdTime).updater(updater).updatedTime(updatedTime).deleted(deleted).build();
        
        // Then
        assertEquals(id, entity.getId());
        assertEquals(tenantId, entity.getTenantId());
        assertEquals(creator, entity.getCreator());
        assertEquals(createdTime, entity.getCreatedTime());
        assertEquals(updater, entity.getUpdater());
        assertEquals(updatedTime, entity.getUpdatedTime());
        assertEquals(deleted, entity.getDeleted());
    }
    
    @Test
    void testBaseEntityNoArgsConstructor() {
        // When
        BaseEntity<Long> entity = new BaseEntity<>();
        
        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getTenantId());
        assertNull(entity.getCreator());
        assertNull(entity.getCreatedTime());
        assertNull(entity.getUpdater());
        assertNull(entity.getUpdatedTime());
        assertEquals(Integer.valueOf(0), entity.getDeleted());
    }
    
    @Test
    void testBaseEntityAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long tenantId = 100L;
        String creator = "testCreator";
        LocalDateTime createdTime = LocalDateTime.now();
        String updater = "testUpdater";
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer deleted = 0;
        
        // When
        BaseEntity<Long> entity = new BaseEntity<>(id, tenantId, creator, createdTime, updater, updatedTime, deleted);
        
        // Then
        assertEquals(id, entity.getId());
        assertEquals(tenantId, entity.getTenantId());
        assertEquals(creator, entity.getCreator());
        assertEquals(createdTime, entity.getCreatedTime());
        assertEquals(updater, entity.getUpdater());
        assertEquals(updatedTime, entity.getUpdatedTime());
        assertEquals(deleted, entity.getDeleted());
    }
    
    @Test
    void testSerializable() {
        // When
        BaseEntity<Long> entity = new BaseEntity<>();
        
        // Then
        assertTrue(entity instanceof java.io.Serializable);
    }
    
    @Test
    void testHasDeletedInterface() {
        // When
        BaseEntity<Long> entity = new BaseEntity<>();
        entity.setDeleted(1);
        
        // Then
        assertEquals(Integer.valueOf(1), entity.getDeleted());
    }
    
    @Test
    void testHasTenantIdInterface() {
        // When
        BaseEntity<Long> entity = new BaseEntity<>();
        entity.setTenantId(100L);
        
        // Then
        assertEquals(Long.valueOf(100L), entity.getTenantId());
    }
}