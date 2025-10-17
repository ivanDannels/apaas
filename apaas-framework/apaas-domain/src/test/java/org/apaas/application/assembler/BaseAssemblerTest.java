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
package org.apaas.application.assembler;

import org.apaas.application.dto.BaseDTO;
import org.apaas.domain.entity.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseAssembler测试类
 * @author ivan
 */
class BaseAssemblerTest {
    
    private TestAssembler assembler;
    
    @BeforeEach
    void setUp() {
        assembler = Mappers.getMapper(TestAssembler.class);
    }
    
    @Test
    void testToDTO() {
        // Given
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(100L);
        entity.setCreator("testCreator");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUpdater");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setDeleted(0);
        entity.setName("testName");
        
        // When
        TestDTO dto = assembler.toDTO(entity);
        
        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTenantId(), dto.getTenantId());
        assertEquals(entity.getCreator(), dto.getCreator());
        assertEquals(entity.getCreatedTime(), dto.getCreatedTime());
        assertEquals(entity.getUpdater(), dto.getUpdater());
        assertEquals(entity.getUpdatedTime(), dto.getUpdatedTime());
        assertEquals(entity.getDeleted(), dto.getDeleted());
        assertEquals(entity.getName(), dto.getName());
    }
    
    @Test
    void testToEntity() {
        // Given
        TestDTO dto = new TestDTO();
        dto.setId(1L);
        dto.setTenantId(100L);
        dto.setCreator("testCreator");
        dto.setCreatedTime(LocalDateTime.now());
        dto.setUpdater("testUpdater");
        dto.setUpdatedTime(LocalDateTime.now());
        dto.setDeleted(0);
        dto.setName("testName");
        
        // When
        TestEntity entity = assembler.toEntity(dto);
        
        // Then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTenantId(), entity.getTenantId());
        assertEquals(dto.getCreator(), entity.getCreator());
        assertEquals(dto.getCreatedTime(), entity.getCreatedTime());
        assertEquals(dto.getUpdater(), entity.getUpdater());
        assertEquals(dto.getUpdatedTime(), entity.getUpdatedTime());
        assertEquals(dto.getDeleted(), entity.getDeleted());
        assertEquals(dto.getName(), entity.getName());
    }
    
    @Test
    void testToDTOList() {
        // Given
        TestEntity entity1 = new TestEntity();
        entity1.setId(1L);
        entity1.setName("testName1");
        
        TestEntity entity2 = new TestEntity();
        entity2.setId(2L);
        entity2.setName("testName2");
        
        List<TestEntity> entities = Arrays.asList(entity1, entity2);
        
        // When
        List<TestDTO> dtos = assembler.toDTOList(entities);
        
        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entity1.getId(), dtos.get(0).getId());
        assertEquals(entity1.getName(), dtos.get(0).getName());
        assertEquals(entity2.getId(), dtos.get(1).getId());
        assertEquals(entity2.getName(), dtos.get(1).getName());
    }
    
    @Test
    void testToEntityList() {
        // Given
        TestDTO dto1 = new TestDTO();
        dto1.setId(1L);
        dto1.setName("testName1");
        
        TestDTO dto2 = new TestDTO();
        dto2.setId(2L);
        dto2.setName("testName2");
        
        List<TestDTO> dtos = Arrays.asList(dto1, dto2);
        
        // When
        List<TestEntity> entities = assembler.toEntityList(dtos);
        
        // Then
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dto1.getId(), entities.get(0).getId());
        assertEquals(dto1.getName(), entities.get(0).getName());
        assertEquals(dto2.getId(), entities.get(1).getId());
        assertEquals(dto2.getName(), entities.get(1).getName());
    }
    
    // 测试用的实体类
    public static class TestEntity extends BaseEntity<Long> {
        
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    // 测试用的DTO类
    public static class TestDTO extends BaseDTO<Long> {
        
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    // 测试用的Assembler实现
    @Mapper
    public interface TestAssembler extends BaseAssembler<TestEntity, TestDTO, Long> {
        
        TestAssembler INSTANCE = Mappers.getMapper(TestAssembler.class);
    }
}