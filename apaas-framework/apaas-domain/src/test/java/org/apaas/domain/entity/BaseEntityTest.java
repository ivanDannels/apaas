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

class BaseEntityTest {
    
    @Test
    void testBaseEntityFields() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(100L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setDeleted(0);
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 验证基础字段
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getTenantId());
        assertEquals("testUser", entity.getCreator());
        assertNotNull(entity.getCreatedTime());
        assertEquals("testUser", entity.getUpdater());
        assertNotNull(entity.getUpdatedTime());
        assertEquals(0, entity.getDeleted());
        
        // 验证实体特定字段
        assertEquals("Test Entity", entity.getName());
        assertEquals("Test Description", entity.getDescription());
    }
}