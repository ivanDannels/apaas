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
package org.apaas.domain.factory;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * EntityFactory接口测试类
 * @author ivan
 */
class EntityFactoryTest {
    
    @Test
    void testEntityFactoryInterfaceExists() {
        assertNotNull(EntityFactory.class);
    }
    
    /**
     * 测试用的实体类
     */
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class TestEntity extends BaseEntity<Long> {
        
        private String name;
    }
    
    /**
     * 测试用的EntityFactory实现类
     */
    static class TestEntityFactory implements EntityFactory<TestEntity, Long> {
        
        @Override
        public TestEntity create(Long id, Object... args) {
            if (args.length > 0 && args[0] instanceof String) {
                return TestEntity.builder().id(id).name((String) args[0]).build();
            }
            return TestEntity.builder().id(id).build();
        }
        
        @Override
        public TestEntity create() {
            return new TestEntity();
        }
        
        @Override
        public TestEntity createFrom(TestEntity prototype) {
            TestEntity entity = new TestEntity();
            entity.setId(prototype.getId());
            entity.setName(prototype.getName());
            return entity;
        }
    }
    
    @Test
    void testCreateWithIdAndArgs() {
        // Given
        TestEntityFactory factory = new TestEntityFactory();
        Long id = 1L;
        String name = "testName";
        
        // When
        TestEntity entity = factory.create(id, name);
        
        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }
    
    @Test
    void testCreateWithoutArgs() {
        // Given
        TestEntityFactory factory = new TestEntityFactory();
        
        // When
        TestEntity entity = factory.create();
        
        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
    }
    
    @Test
    void testCreateFromPrototype() {
        // Given
        TestEntityFactory factory = new TestEntityFactory();
        TestEntity prototype = TestEntity.builder().id(1L).name("prototypeName").build();
        
        // When
        TestEntity entity = factory.createFrom(prototype);
        
        // Then
        assertNotNull(entity);
        assertEquals(prototype.getId(), entity.getId());
        assertEquals(prototype.getName(), entity.getName());
    }
}