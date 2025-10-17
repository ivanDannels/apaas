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
package org.apaas.interfaces.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apaas.application.dto.BaseDTO;
import org.apaas.application.service.ApplicationService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * BaseRest 控制器测试类
 * @author ivan
 */
@ExtendWith(MockitoExtension.class)
class BaseRestTest {
    
    @Mock
    private ApplicationService<TestDTO, Long> service;
    
    private TestBaseRest baseRest;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        baseRest = new TestBaseRest(service); // 实例化被测试类
    }
    
    @Test
    void testPage() {
        // 准备测试数据
        Query query = new Query();
        PageResult<TestDTO> pageResult = new PageResult<>();
        
        // 配置mock行为
        when(service.selectPage(any(Query.class))).thenReturn(Mono.just(pageResult));
        
        // 执行测试
        Mono<PageResult<TestDTO>> result = baseRest.page(query);
        
        // 验证结果
        StepVerifier.create(result).expectNext(pageResult).verifyComplete();
        
        // 验证方法调用
        verify(service).selectPage(query);
    }
    
    @Test
    void testGet() {
        // 准备测试数据
        Long id = 1L;
        TestDTO testDTO = new TestDTO();
        
        // 配置mock行为
        when(service.findById(id)).thenReturn(Mono.just(testDTO));
        
        // 执行测试
        Mono<TestDTO> result = baseRest.get(id);
        
        // 验证结果
        StepVerifier.create(result).expectNext(testDTO).verifyComplete();
        
        // 验证方法调用
        verify(service).findById(id);
    }
    
    @Test
    void testAdd() {
        // 准备测试数据
        TestDTO testDTO = new TestDTO();
        
        // 配置mock行为
        when(service.save(any(TestDTO.class))).thenReturn(Mono.just(testDTO));
        
        // 执行测试
        Mono<TestDTO> result = baseRest.add(testDTO);
        
        // 验证结果
        StepVerifier.create(result).expectNext(testDTO).verifyComplete();
        
        // 验证方法调用
        verify(service).save(testDTO);
    }
    
    @Test
    void testAddBatch() {
        // 准备测试数据
        TestDTO dto1 = new TestDTO();
        TestDTO dto2 = new TestDTO();
        Flux<TestDTO> dtoFlux = Flux.just(dto1, dto2);
        
        // 配置mock行为
        when(service.saveBatch(any(Flux.class))).thenReturn(Flux.just(dto1, dto2));
        
        // 执行测试
        Flux<TestDTO> result = baseRest.addBatch(dtoFlux);
        
        // 验证结果
        StepVerifier.create(result).expectNext(dto1, dto2).verifyComplete();
        
        // 验证方法调用
        verify(service).saveBatch(any(Flux.class));
    }
    
    @Test
    void testUpdate() {
        // 准备测试数据
        TestDTO testDTO = new TestDTO();
        
        // 配置mock行为
        when(service.save(any(TestDTO.class))).thenReturn(Mono.just(testDTO));
        
        // 执行测试
        Mono<TestDTO> result = baseRest.update(testDTO);
        
        // 验证结果
        StepVerifier.create(result).expectNext(testDTO).verifyComplete();
        
        // 验证方法调用
        verify(service).save(testDTO);
    }
    
    @Test
    void testUpdateBatch() {
        // 准备测试数据
        TestDTO dto1 = new TestDTO();
        TestDTO dto2 = new TestDTO();
        Flux<TestDTO> dtoFlux = Flux.just(dto1, dto2);
        
        // 配置mock行为
        when(service.updateBatch(any(Flux.class))).thenReturn(Flux.just(dto1, dto2));
        
        // 执行测试
        Flux<TestDTO> result = baseRest.updateBatch(dtoFlux);
        
        // 验证结果
        StepVerifier.create(result).expectNext(dto1, dto2).verifyComplete();
        
        // 验证方法调用
        verify(service).updateBatch(any(Flux.class));
    }
    
    @Test
    void testDelete() {
        // 准备测试数据
        Long id = 1L;
        
        // 配置mock行为
        when(service.deleteById(id)).thenReturn(Mono.empty());
        
        // 执行测试
        Mono<Void> result = baseRest.delete(id);
        
        // 验证结果
        StepVerifier.create(result).verifyComplete();
        
        // 验证方法调用
        verify(service).deleteById(id);
    }
    
    @Test
    void testDeleteBatch() {
        // 准备测试数据
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        
        // 配置mock行为
        when(service.deleteAllById(ids)).thenReturn(Mono.empty());
        
        // 执行测试
        Mono<Void> result = baseRest.deleteBatch(ids);
        
        // 验证结果
        StepVerifier.create(result).verifyComplete();
        
        // 验证方法调用
        verify(service).deleteAllById(ids);
    }
    
    @Test
    void testExport() {
        // 准备测试数据
        Query query = new Query();
        TestDTO testDTO1 = TestDTO.builder().testField("test1").id(1L).build();
        
        TestDTO testDTO2 = TestDTO.builder().testField("test2").id(2L).build();
        List<TestDTO> dataList = Arrays.asList(testDTO1, testDTO2);
        
        // 配置mock行为
        when(service.export(any(Query.class))).thenReturn(Mono.just(dataList));
        when(service.getDtoClass()).thenReturn(TestDTO.class);
        
        // 执行测试
        Mono<Resource> result = baseRest.export(query);
        
        // 验证结果
        StepVerifier.create(result).expectNextMatches(resource -> resource instanceof ByteArrayResource).verifyComplete();
        
        // 验证方法调用
        verify(service).export(query);
    }
    
    @Test
    void testImportDataWithNullFile() {
        // 执行测试
        Mono<Void> result = baseRest.importData(null);
        
        // 验证结果
        StepVerifier.create(result).expectError(BusinessException.class).verify();
    }
    
    @Test
    void testImportData() {
        // 准备测试数据
        FilePart filePart = mock(FilePart.class);
        
        // 配置mock行为
        when(service.importData(any(FilePart.class))).thenReturn(Mono.empty());
        
        // 执行测试
        Mono<Void> result = baseRest.importData(filePart);
        
        // 验证结果
        StepVerifier.create(result).verifyComplete();
        
        // 验证方法调用
        verify(service).importData(filePart);
    }
    
    /**
     * 测试用的DTO类
     */
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class TestDTO extends BaseDTO<Long> implements Serializable {
        
        @Serial
        private static final long serialVersionUID = 1L;
        
        private Long id;
        
        private String testField;
    }
    
    /**
     * 测试用的BaseRest实现类
     */
    static class TestBaseRest extends BaseRest<TestDTO, Long, ApplicationService<TestDTO, Long>> {
        
        public TestBaseRest(ApplicationService<TestDTO, Long> service) {
            super(service);
        }
    }
}