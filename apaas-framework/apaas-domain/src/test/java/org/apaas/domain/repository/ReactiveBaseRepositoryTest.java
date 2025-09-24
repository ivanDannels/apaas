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

import org.apaas.domain.entity.TestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import org.springframework.test.context.ContextConfiguration;
import org.apaas.domain.config.TestConfig;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@ContextConfiguration(classes = TestConfig.class)
class ReactiveBaseRepositoryTest {
    
    @Autowired
    private TestEntityRepository testEntityRepository;
    
    @Test
    void testSaveAndFind() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        Mono<TestEntity> savedMono = testEntityRepository.save(entity);
        StepVerifier.create(savedMono).expectNextMatches(saved -> {
            assertNotNull(saved.getId());
            assertEquals("Test Entity", saved.getName());
            return true;
        }).verifyComplete();
        
        // 获取保存的实体
        TestEntity savedEntity = savedMono.block();
        
        // 根据ID查找
        testEntityRepository.findById(savedEntity.getId()).as(StepVerifier::create).expectNextMatches(found -> {
            assertEquals(savedEntity.getId(), found.getId());
            assertEquals("Test Entity", found.getName());
            return true;
        }).verifyComplete();
    }
    
    @Test
    void testFindAllByTenantIdAndDeletedFalse() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        testEntityRepository.save(entity).block();
        
        // 查找所有未删除的实体
        testEntityRepository.findAllByTenantIdAndDeletedFalse(1L, null).as(StepVerifier::create).expectNextMatches(found -> {
            assertEquals("Test Entity", found.getName());
            return true;
        }).verifyComplete();
    }
    
    @Test
    void testDeleteByIdAndTenantId() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        TestEntity savedEntity = testEntityRepository.save(entity).block();
        
        // 删除实体
        testEntityRepository.deleteByIdAndTenantId(savedEntity.getId(), 1L).as(StepVerifier::create).verifyComplete();
        
        // 验证实体已被删除
        testEntityRepository.findById(savedEntity.getId()).as(StepVerifier::create).verifyComplete();
    }
}