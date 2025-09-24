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
package org.apaas.domain.service.impl;

import org.apaas.domain.config.TestConfig;
import org.apaas.domain.entity.TestEntity;
import org.apaas.domain.repository.TestEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.apaas.core.context.TenantContext;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestConfig.class)
class BaseServiceImplTest {
    
    @Mock
    private TestEntityRepository testEntityRepository;
    
    @InjectMocks
    private TestEntityServiceImpl testEntityService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 设置测试租户ID
        TenantContext.setTenantId(1L);
    }
    
    @AfterEach
    void tearDown() {
    }
    
    @Test
    void testSave() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 设置mock行为
        when(testEntityRepository.save(any(TestEntity.class))).thenReturn(Mono.just(entity));
        
        // 执行测试
        testEntityService.save(entity).as(StepVerifier::create).expectNextMatches(saved -> {
            Assertions.assertEquals("Test Entity", saved.getName());
            return true;
        }).verifyComplete();
        
        // 验证方法调用
        verify(testEntityRepository, times(1)).save(any(TestEntity.class));
    }
    
    @Test
    void testFindById() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setName("Test Entity");
        
        // 设置mock行为
        when(testEntityRepository.findByIdAndTenantId(anyLong(), anyLong())).thenReturn(Mono.just(entity));
        
        // 执行测试
        testEntityService.findById(1L).as(StepVerifier::create).expectNextMatches(found -> {
            Assertions.assertEquals(1L, found.getId());
            Assertions.assertEquals("Test Entity", found.getName());
            return true;
        }).verifyComplete();
        
        // 验证方法调用
        verify(testEntityRepository, times(1)).findByIdAndTenantId(1L, 1L);
    }
    
    @Test
    void testFindAll() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setName("Test Entity");
        
        // 设置mock行为
        when(testEntityRepository.findAllByTenantIdAndDeletedFalse(anyLong(), eq(null))).thenReturn(Flux.just(entity));
        
        // 执行测试
        testEntityService.findAll().as(StepVerifier::create).expectNextMatches(found -> {
            Assertions.assertEquals(1L, found.getId());
            Assertions.assertEquals("Test Entity", found.getName());
            return true;
        }).verifyComplete();
        
        // 验证方法调用
        verify(testEntityRepository, times(1)).findAllByTenantIdAndDeletedFalse(1L, null);
    }
    
    @Test
    void testDeleteById() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setName("Test Entity");
        
        // 设置mock行为
        when(testEntityRepository.findByIdAndTenantId(anyLong(), anyLong())).thenReturn(Mono.just(entity));
        when(testEntityRepository.deleteByIdAndTenantId(anyLong(), anyLong())).thenReturn(Mono.empty());
        
        // 执行测试
        testEntityService.deleteById(1L).as(StepVerifier::create).verifyComplete();
        
        // 验证方法调用
        verify(testEntityRepository, times(1)).findByIdAndTenantId(1L, 1L);
        verify(testEntityRepository, times(1)).deleteByIdAndTenantId(1L, 1L);
    }
}