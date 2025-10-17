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
package org.apaas.domain.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseRepository接口测试类
 * @author ivan
 */
class BaseRepositoryTest {
    
    @Test
    void testBaseRepositoryInterfaceExists() {
        assertNotNull(BaseRepository.class);
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
     * 测试用的BaseRepository实现类（空实现，仅用于验证接口定义）
     */
    @Repository
    interface TestBaseRepository extends BaseRepository<TestEntity, Long> {
        
    }
}