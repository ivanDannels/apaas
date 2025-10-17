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
package org.apaas.interfaces.event.listener;

import org.apaas.domain.event.DataDictionaryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.stream.ObjectRecord;

import java.time.LocalDateTime;

/**
 * DataDictionaryEventListener 测试类
 * @author ivan
 */
class DataDictionaryEventListenerTest {
    
    private DataDictionaryEventListener listener;
    
    @BeforeEach
    public void setUp() {
        listener = new DataDictionaryEventListener();
    }
    
    @Test
    void testOnMessageWithCreatedEvent() {
        // 准备测试数据
        DataDictionaryEvent event = DataDictionaryEvent.builder().eventType("DATA_DICTIONARY_CREATED").dataDictionaryId(1L).dataDictionaryName("testDict").operationType("CREATE").description("Test dictionary creation").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, DataDictionaryEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果 - 由于是日志输出，我们只能验证方法执行不抛异常
        // 在实际项目中，可能需要验证是否调用了特定的业务方法
    }
    
    @Test
    void testOnMessageWithUpdatedEvent() {
        // 准备测试数据
        DataDictionaryEvent event = DataDictionaryEvent.builder().eventType("DATA_DICTIONARY_UPDATED").dataDictionaryId(1L).dataDictionaryName("testDict").operationType("UPDATE").description("Test dictionary update").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, DataDictionaryEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithDeletedEvent() {
        // 准备测试数据
        DataDictionaryEvent event = DataDictionaryEvent.builder().eventType("DATA_DICTIONARY_DELETED").dataDictionaryId(1L).dataDictionaryName("testDict").operationType("DELETE").description("Test dictionary deletion").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, DataDictionaryEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithImportedEvent() {
        // 准备测试数据
        DataDictionaryEvent event = DataDictionaryEvent.builder().eventType("DATA_DICTIONARY_IMPORTED").dataDictionaryId(1L).dataDictionaryName("testDict").operationType("IMPORT").description("Test dictionary import").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, DataDictionaryEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
    
    @Test
    void testOnMessageWithUnknownEvent() {
        // 准备测试数据
        DataDictionaryEvent event = DataDictionaryEvent.builder().eventType("UNKNOWN_EVENT").dataDictionaryId(1L).dataDictionaryName("testDict").operationType("UNKNOWN").description("Unknown event").eventTime(LocalDateTime.now()).build();
        
        ObjectRecord<String, DataDictionaryEvent> record = ObjectRecord.create("test-stream", event);
        
        // 执行测试
        listener.onMessage(record);
        
        // 验证结果
    }
}