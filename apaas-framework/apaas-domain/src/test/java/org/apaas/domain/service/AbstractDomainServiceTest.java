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
import org.apaas.domain.repository.BaseRepository;
import org.apaas.domain.specification.Specification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * AbstractDomainService测试类
 * @author ivan
 */
class AbstractDomainServiceTest {
    
    @Mock
    private TestRepository repository;
    
    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    
    private TestDomainService domainService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        domainService = new TestDomainService(repository, r2dbcEntityTemplate);
    }
    
    @Test
    void testCheckSpecification() {
        // Given
        TestEntity entity = new TestEntity();
        TestSpecification specification = new TestSpecification(true);
        
        // When
        Mono<Boolean> result = domainService.checkSpecificationPublic(entity, specification);
        
        // Then
        StepVerifier.create(result).expectNext(true).verifyComplete();
    }
    
    @Test
    void testValidateAndSaveSuccess() {
        // Given
        TestEntity entity = new TestEntity();
        TestSpecification specification = new TestSpecification(true);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        
        // When
        Mono<TestEntity> result = domainService.validateAndSavePublic(entity, specification);
        
        // Then
        StepVerifier.create(result).expectNext(entity).verifyComplete();
        
        verify(repository).save(entity);
    }
    
    @Test
    void testValidateAndSaveFailure() {
        // Given
        TestEntity entity = new TestEntity();
        TestSpecification specification = new TestSpecification(false);
        
        // When
        Mono<TestEntity> result = domainService.validateAndSavePublic(entity, specification);
        
        // Then
        StepVerifier.create(result).expectError().verify();
        
        verify(repository, never()).save(entity);
    }
    
    // 测试用的实体类
    static class TestEntity extends BaseEntity<Long> {
    }
    
    // 测试用的仓库接口
    interface TestRepository extends BaseRepository<TestEntity, Long> {
    }
    
    // 测试用的规范类
    static class TestSpecification implements Specification<TestEntity> {
        
        private final boolean satisfied;
        
        public TestSpecification(boolean satisfied) {
            this.satisfied = satisfied;
        }
        
        @Override
        public boolean isSatisfiedBy(TestEntity entity) {
            return satisfied;
        }
    }
    
    // 测试用的服务实现类
    static class TestDomainService extends AbstractDomainService<TestEntity, Long, TestRepository> {
        
        public TestDomainService(TestRepository repository, R2dbcEntityTemplate r2dbcEntityTemplate) {
            super(repository, r2dbcEntityTemplate);
        }
        
        public Mono<Boolean> checkSpecificationPublic(TestEntity entity, Specification<TestEntity> specification) {
            return checkSpecification(entity, specification);
        }
        
        public Mono<TestEntity> validateAndSavePublic(TestEntity entity, Specification<TestEntity> specification) {
            return validateAndSave(entity, specification);
        }
    }
}