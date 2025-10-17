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
package org.apaas.domain.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Specification接口测试类
 * @author ivan
 */
class SpecificationTest {
    
    @Test
    void testSpecificationInterfaceExists() {
        assertNotNull(Specification.class);
    }
    
    /**
     * 测试用的实体类
     */
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestEntity {
        
        private String name;
        private int age;
    }
    
    /**
     * 测试用的名称规范
     */
    record NameSpecification(String expectedName) implements Specification<TestEntity> {
        
        @Override
        public boolean isSatisfiedBy(TestEntity candidate) {
            return candidate != null && expectedName.equals(candidate.getName());
        }
    }
    
    /**
     * 测试用的年龄规范
     */
    record AgeSpecification(int minAge) implements Specification<TestEntity> {
        
        @Override
        public boolean isSatisfiedBy(TestEntity candidate) {
            return candidate != null && candidate.getAge() >= minAge;
        }
    }
    
    @Test
    void testIsSatisfiedBy() {
        // Given
        Specification<TestEntity> nameSpec = new NameSpecification("John");
        TestEntity entity = new TestEntity("John", 25);
        
        // When
        boolean result = nameSpec.isSatisfiedBy(entity);
        
        // Then
        assertTrue(result);
    }
    
    @Test
    void testAndCombination() {
        // Given
        Specification<TestEntity> nameSpec = new NameSpecification("John");
        Specification<TestEntity> ageSpec = new AgeSpecification(18);
        Specification<TestEntity> combinedSpec = nameSpec.and(ageSpec);
        
        TestEntity entity = new TestEntity("John", 25);
        TestEntity wrongNameEntity = new TestEntity("Jane", 25);
        TestEntity wrongAgeEntity = new TestEntity("John", 15);
        
        // When & Then
        assertTrue(combinedSpec.isSatisfiedBy(entity));
        assertFalse(combinedSpec.isSatisfiedBy(wrongNameEntity));
        assertFalse(combinedSpec.isSatisfiedBy(wrongAgeEntity));
    }
    
    @Test
    void testOrCombination() {
        // Given
        Specification<TestEntity> nameSpec = new NameSpecification("John");
        Specification<TestEntity> ageSpec = new AgeSpecification(18);
        Specification<TestEntity> combinedSpec = nameSpec.or(ageSpec);
        
        TestEntity entity = new TestEntity("John", 25);
        TestEntity onlyNameEntity = new TestEntity("John", 15);
        TestEntity onlyAgeEntity = new TestEntity("Jane", 25);
        TestEntity neitherEntity = new TestEntity("Jane", 15);
        
        // When & Then
        assertTrue(combinedSpec.isSatisfiedBy(entity));
        assertTrue(combinedSpec.isSatisfiedBy(onlyNameEntity));
        assertTrue(combinedSpec.isSatisfiedBy(onlyAgeEntity));
        assertFalse(combinedSpec.isSatisfiedBy(neitherEntity));
    }
    
    @Test
    void testNotOperation() {
        // Given
        Specification<TestEntity> nameSpec = new NameSpecification("John");
        Specification<TestEntity> notNameSpec = nameSpec.not();
        
        TestEntity entity = new TestEntity("John", 25);
        TestEntity otherEntity = new TestEntity("Jane", 25);
        
        // When & Then
        assertFalse(notNameSpec.isSatisfiedBy(entity));
        assertTrue(notNameSpec.isSatisfiedBy(otherEntity));
    }
}