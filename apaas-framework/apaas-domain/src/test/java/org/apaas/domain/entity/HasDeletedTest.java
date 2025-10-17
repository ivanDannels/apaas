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

import static org.junit.jupiter.api.Assertions.*;

/**
 * HasDeleted接口测试类
 * @author ivan
 */
class HasDeletedTest {
    
    @Test
    void testHasDeletedInterface() {
        // 创建一个实现HasDeleted接口的测试类
        TestHasDeletedEntity entity = new TestHasDeletedEntity();
        
        // 测试默认值
        assertNull(entity.getDeleted());
        
        // 设置值并验证
        Integer deletedValue = 1;
        entity.setDeleted(deletedValue);
        assertEquals(deletedValue, entity.getDeleted());
    }
    
    /**
     * 测试用的实现类
     */
    static class TestHasDeletedEntity implements HasDeleted {
        
        private Integer deleted;
        
        public Integer getDeleted() {
            return deleted;
        }
        
        public void setDeleted(Integer deleted) {
            this.deleted = deleted;
        }
    }
}