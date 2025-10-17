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
package org.apaas.application.service;

import org.apaas.application.assembler.BaseAssembler;
import org.apaas.application.dto.BaseDTO;
import org.apaas.domain.entity.BaseEntity;
import org.apaas.domain.service.DomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * AbstractApplicationService测试类
 * @author ivan
 */
class AbstractApplicationServiceTest {
    
    @Mock
    private TestDomainService domainService;
    
    private TestApplicationService applicationService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationService = new TestApplicationService(domainService);
    }
    
    @Test
    void testSave() {
        // Given
        TestDTO dto = new TestDTO();
        dto.setId(1L);
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        TestDTO savedDTO = new TestDTO();
        savedDTO.setId(1L);
        
        when(domainService.save(entity)).thenReturn(Mono.just(entity));
        
        // When
        Mono<TestDTO> result = applicationService.save(dto);
        
        // Then
        StepVerifier.create(result).expectNext(savedDTO).verifyComplete();
        
        verify(domainService).save(entity);
    }
    
    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        TestEntity entity = new TestEntity();
        entity.setId(id);
        TestDTO dto = new TestDTO();
        dto.setId(id);
        
        when(domainService.findById(id)).thenReturn(Mono.just(entity));
        
        // When
        Mono<TestDTO> result = applicationService.findById(id);
        
        // Then
        StepVerifier.create(result).expectNext(dto).verifyComplete();
        
        verify(domainService).findById(id);
    }
    
    @Test
    void testFindAll() {
        // Given
        TestEntity entity1 = new TestEntity();
        entity1.setId(1L);
        TestEntity entity2 = new TestEntity();
        entity2.setId(2L);
        TestDTO dto1 = new TestDTO();
        dto1.setId(1L);
        TestDTO dto2 = new TestDTO();
        dto2.setId(2L);
        
        when(domainService.findAll()).thenReturn(Flux.just(entity1, entity2));
        
        // When
        Flux<TestDTO> result = applicationService.findAll();
        
        // Then
        StepVerifier.create(result).expectNext(dto1).expectNext(dto2).verifyComplete();
        
        verify(domainService).findAll();
    }
    
    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;
        when(domainService.deleteById(id)).thenReturn(Mono.empty());
        
        // When
        Mono<Void> result = applicationService.deleteById(id);
        
        // Then
        StepVerifier.create(result).verifyComplete();
        
        verify(domainService).deleteById(id);
    }
    
    // 测试用的实体类
    static class TestEntity extends BaseEntity<Long> {
    }
    
    // 测试用的DTO类
    static class TestDTO extends BaseDTO<Long> {
    }
    
    // 测试用的Assembler实现
    @Mapper
    interface TestAssembler extends BaseAssembler<TestEntity, TestDTO, Long> {
        
        TestAssembler INSTANCE = Mappers.getMapper(TestAssembler.class);
    }
    
    // 测试用的领域服务
    interface TestDomainService extends DomainService<TestEntity, Long> {
    }
    
    // 测试用的应用服务实现类
    static class TestApplicationService extends AbstractApplicationService<TestEntity, TestDTO, Long, TestAssembler, TestDomainService> {
        
        protected TestApplicationService(TestDomainService domainService) {
            super(domainService);
        }
        
        @Override
        protected TestAssembler getAssemblerInstance() {
            return TestAssembler.INSTANCE;
        }
        
        @Override
        public Class<TestDTO> getDtoClass() {
            return TestDTO.class;
        }
    }
}