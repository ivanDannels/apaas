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
package org.apaas.domain.vo;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseValueObject类测试类
 * @author ivan
 */
class BaseValueObjectTest {
    
    /**
     * 测试用的值对象实现类
     */
    @Getter
    static class TestValueObject extends BaseValueObject {
        
        private final String name;
        private final int age;
        
        public TestValueObject(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        protected boolean equalsImpl(BaseValueObject other) {
            if (!(other instanceof TestValueObject that)) {
                return false;
            }
            return name.equals(that.name) && age == that.age;
        }
        
        @Override
        public int hashCode() {
            return name.hashCode() * 31 + age;
        }
        
        @Override
        public String toString() {
            return "TestValueObject{name='" + name + "', age=" + age + "}";
        }
        
    }
    
    @Test
    void testEqualsWithSameObject() {
        // Given
        TestValueObject vo = new TestValueObject("John", 25);
        
        // When & Then
        assertEquals(vo, vo);
    }
    
    @Test
    void testEqualsWithNull() {
        // Given
        TestValueObject vo = new TestValueObject("John", 25);
        
        // When & Then
        assertNotEquals(null, vo);
    }
    
    @Test
    void testEqualsWithDifferentClass() {
        // Given
        TestValueObject vo = new TestValueObject("John", 25);
        String other = "other";
        
        // When & Then
        assertNotEquals(vo, other);
    }
    
    @Test
    void testEqualsWithSameValues() {
        // Given
        TestValueObject vo1 = new TestValueObject("John", 25);
        TestValueObject vo2 = new TestValueObject("John", 25);
        
        // When & Then
        assertEquals(vo1, vo2);
        assertEquals(vo1.hashCode(), vo2.hashCode());
    }
    
    @Test
    void testEqualsWithDifferentValues() {
        // Given
        TestValueObject vo1 = new TestValueObject("John", 25);
        TestValueObject vo2 = new TestValueObject("Jane", 25);
        TestValueObject vo3 = new TestValueObject("John", 30);
        
        // When & Then
        assertNotEquals(vo1, vo2);
        assertNotEquals(vo1, vo3);
    }
    
    @Test
    void testHashCode() {
        // Given
        TestValueObject vo1 = new TestValueObject("John", 25);
        TestValueObject vo2 = new TestValueObject("John", 25);
        TestValueObject vo3 = new TestValueObject("Jane", 25);
        
        // When & Then
        assertEquals(vo1.hashCode(), vo2.hashCode());
        assertNotEquals(vo1.hashCode(), vo3.hashCode());
    }
    
    @Test
    void testToString() {
        // Given
        TestValueObject vo = new TestValueObject("John", 25);
        
        // When
        String result = vo.toString();
        
        // Then
        assertNotNull(result);
        assertTrue(result.contains("John"));
        assertTrue(result.contains("25"));
    }
}