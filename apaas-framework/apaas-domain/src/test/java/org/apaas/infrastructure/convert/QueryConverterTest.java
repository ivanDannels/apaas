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
package org.apaas.infrastructure.convert;

import org.apaas.core.query.Condition;
import org.apaas.core.query.Query;
import org.apaas.domain.entity.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QueryConverter测试类
 * @author ivan
 */
class QueryConverterTest {
    
    @Test
    void testConvertToMap() {
        // Given
        Condition condition = new Condition();
        condition.setField("name");
        condition.setValue("test");
        
        Query query = new Query();
        query.setConditions(new Condition[]{condition});
        
        // When
        Map<String, Object> result = QueryConverter.convertToMap(query);
        
        // Then
        assertNotNull(result);
        assertEquals("test", result.get("name"));
    }
    
    @Test
    void testConvertToEntity() {
        // Given
        Condition condition = new Condition();
        condition.setField("name");
        condition.setValue("testName");
        
        Query query = new Query();
        query.setConditions(new Condition[]{condition});
        
        // When
        TestEntity entity = QueryConverter.convertToEntity(query, TestEntity.class);
        
        // Then
        assertNotNull(entity);
        assertEquals("testName", entity.getName());
    }
    
    @Test
    void testConvertToExample() {
        // Given
        Condition condition = new Condition();
        condition.setField("name");
        condition.setValue("testName");
        
        Query query = new Query();
        query.setConditions(new Condition[]{condition});
        
        // When
        Example<TestEntity> example = QueryConverter.convertToExample(query, TestEntity.class);
        
        // Then
        assertNotNull(example);
        TestEntity probe = example.getProbe();
        assertEquals("testName", probe.getName());
    }
    
    @Test
    void testConvertToSort() {
        // Given
        org.apaas.core.query.Sorter sorter = new org.apaas.core.query.Sorter();
        sorter.setField("name");
        sorter.setDirection(org.apaas.core.query.Direction.ASC);
        
        Query query = new Query();
        query.setSorts(new org.apaas.core.query.Sorter[]{sorter});
        
        // When
        Sort sort = QueryConverter.convertToSort(query, TestEntity.class);
        
        // Then
        assertNotNull(sort);
        assertFalse(sort.isEmpty());
    }
    
    @Test
    void testConvertToCriteria() {
        // Given
        Condition condition = new Condition();
        condition.setField("name");
        condition.setValue("testName");
        condition.setComparison(org.apaas.core.query.Operator.Comparison.EQ);
        
        Query query = new Query();
        query.setConditions(new Condition[]{condition});
        
        // When
        Criteria criteria = QueryConverter.convertToCriteria(query, TestEntity.class);
        
        // Then
        assertNotNull(criteria);
    }
    
    /**
     * 测试用的实体类
     */
    static class TestEntity extends BaseEntity<Long> {
        
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
}