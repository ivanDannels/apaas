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
package org.apaas.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * EventPublisherService测试类
 * @author ivan
 */
class EventPublisherServiceTest {
    
    @Mock
    private ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    
    @Mock
    private ReactiveValueOperations<String, Object> valueOperations;
    
    private EventPublisherService eventPublisherService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventPublisherService = new EventPublisherService(reactiveRedisTemplate);
    }
    
    @Test
    void testPublishEventSuccess() {
        // Given
        String topic = "testTopic";
        String event = "testEvent";
        when(reactiveRedisTemplate.convertAndSend(topic, event)).thenReturn(Mono.just(1L));
        
        // When
        Mono<Boolean> result = eventPublisherService.publishEvent(topic, event);
        
        // Then
        StepVerifier.create(result).expectNext(true).verifyComplete();
        
        verify(reactiveRedisTemplate).convertAndSend(topic, event);
    }
    
    @Test
    void testPublishEventFailure() {
        // Given
        String topic = "testTopic";
        String event = "testEvent";
        when(reactiveRedisTemplate.convertAndSend(topic, event)).thenReturn(Mono.error(new RuntimeException("Redis error")));
        
        // When
        Mono<Boolean> result = eventPublisherService.publishEvent(topic, event);
        
        // Then
        StepVerifier.create(result).expectNext(false).verifyComplete();
        
        verify(reactiveRedisTemplate).convertAndSend(topic, event);
    }
}