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
package org.apaas.domain.aggregate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AggregateRoot接口测试类
 * @author ivan
 */
class AggregateRootTest {
    
    @Test
    void testAggregateRootInterfaceExists() {
        assertNotNull(AggregateRoot.class);
    }
    
    /**
     * 测试用的聚合根实现类
     */
    static class TestAggregateRoot implements AggregateRoot<Long> {
        
        private Long id;
        
        @Override
        public Long getId() {
            return id;
        }
        
        @Override
        public void setId(Long id) {
            this.id = id;
        }
    }
    
    @Test
    void testAggregateRootGettersAndSetters() {
        // Given
        TestAggregateRoot aggregateRoot = new TestAggregateRoot();
        Long id = 1L;
        
        // When
        aggregateRoot.setId(id);
        
        // Then
        assertEquals(id, aggregateRoot.getId());
    }
}