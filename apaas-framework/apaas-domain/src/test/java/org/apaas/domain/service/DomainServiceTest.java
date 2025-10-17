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
package org.apaas.domain.service;

import org.apaas.domain.entity.BaseEntity;
import org.junit.jupiter.api.Test;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainService接口测试类
 * @author ivan
 */
class DomainServiceTest {
    
    @Test
    void testDomainServiceInterfaceExists() {
        // This test simply verifies that the DomainService interface exists and can be referenced
        assertNotNull(DomainService.class);
    }
    
    /**
     * 测试用的实体类
     */
    static class TestEntity extends BaseEntity<Long> {
    }
    
    /**
     * 测试用的DomainService实现类（空实现，仅用于验证接口定义）
     */
    static class TestDomainService implements DomainService<TestEntity, Long> {
        
        @Override
        public Mono<TestEntity> save(TestEntity entity) {
            return null;
        }
        
        @Override
        public Mono<TestEntity> findById(Long id) {
            return null;
        }
        
        @Override
        public Flux<TestEntity> findAll() {
            return null;
        }
        
        @Override
        public Flux<TestEntity> findAll(Query query) {
            return null;
        }
        
        @Override
        public Mono<Void> deleteById(Long id) {
            return null;
        }
        
        @Override
        public Flux<TestEntity> saveAll(List<TestEntity> ts) {
            return null;
        }
        
        @Override
        public Mono<Void> deleteAllById(Iterable<Long> ids) {
            return null;
        }
        
        @Override
        public Mono<PageResult<TestEntity>> selectPage(Query query) {
            return null;
        }
    }
}