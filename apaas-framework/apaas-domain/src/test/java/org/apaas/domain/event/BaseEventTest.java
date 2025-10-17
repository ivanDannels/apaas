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

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseEvent类测试类
 * @author ivan
 */
class BaseEventTest {
    
    @Test
    void testBaseEventNoArgsConstructor() {
        // When
        TestBaseEvent event = TestBaseEvent.builder().eventTime(LocalDateTime.now()).build();
        
        // Then
        assertNotNull(event);
        assertNotNull(event.getEventTime());
        assertNull(event.getEventType());
        assertNull(event.getSource());
        assertNull(event.getDescription());
    }
    
    @Test
    void testBaseEventAllArgsConstructor() {
        // Given
        String eventType = "TEST_EVENT";
        String source = "test-source";
        String description = "Test event description";
        
        // When
        TestBaseEvent event = TestBaseEvent.builder().eventTime(LocalDateTime.now()).eventType(eventType).source(source).description(description).build();
        
        // Then
        assertNotNull(event);
        assertNotNull(event.getEventTime());
        assertEquals(eventType, event.getEventType());
        assertEquals(source, event.getSource());
        assertEquals(description, event.getDescription());
    }
    
    @Test
    void testBaseEventSettersAndGetters() {
        // Given
        TestBaseEvent event = TestBaseEvent.builder().build();
        String eventType = "TEST_EVENT";
        LocalDateTime eventTime = LocalDateTime.now();
        String source = "test-source";
        String description = "Test event description";
        
        // When
        event.setEventType(eventType);
        event.setEventTime(eventTime);
        event.setSource(source);
        event.setDescription(description);
        
        // Then
        assertEquals(eventType, event.getEventType());
        assertEquals(eventTime, event.getEventTime());
        assertEquals(source, event.getSource());
        assertEquals(description, event.getDescription());
    }
    
    @Test
    void testBaseEventBuilder() {
        // Given
        String eventType = "TEST_EVENT";
        LocalDateTime eventTime = LocalDateTime.now();
        String source = "test-source";
        String description = "Test event description";
        
        // When
        TestBaseEvent event = TestBaseEvent.builder().eventType(eventType).eventTime(eventTime).source(source).description(description).build();
        
        // Then
        assertEquals(eventType, event.getEventType());
        assertEquals(eventTime, event.getEventTime());
        assertEquals(source, event.getSource());
        assertEquals(description, event.getDescription());
    }
    
}