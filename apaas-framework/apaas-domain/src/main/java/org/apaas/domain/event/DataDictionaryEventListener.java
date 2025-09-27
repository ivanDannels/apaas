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

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 数据字典事件监听器
 * @author ivan
 */
@Slf4j
@Component
public class DataDictionaryEventListener implements StreamListener<String, ObjectRecord<String, DataDictionaryEvent>> {
    
    @Override
    public void onMessage(ObjectRecord<String, DataDictionaryEvent> message) {
        DataDictionaryEvent dataDictionaryEvent = message.getValue();
        log.info("收到数据字典事件: EventType={}, DataDictionaryId={}, DataDictionaryName={}, OperationType={}, Description={}", dataDictionaryEvent.getEventType(), dataDictionaryEvent.getDataDictionaryId(), dataDictionaryEvent.getDataDictionaryName(), dataDictionaryEvent.getOperationType(), dataDictionaryEvent.getDescription());
        
        // 根据事件类型处理不同的业务逻辑
        switch (dataDictionaryEvent.getEventType()) {
            case "DATA_DICTIONARY_CREATED":
                handleDataDictionaryCreated(dataDictionaryEvent);
                break;
            case "DATA_DICTIONARY_UPDATED":
                handleDataDictionaryUpdated(dataDictionaryEvent);
                break;
            case "DATA_DICTIONARY_DELETED":
                handleDataDictionaryDeleted(dataDictionaryEvent);
                break;
            case "DATA_DICTIONARY_IMPORTED":
                handleDataDictionaryImported(dataDictionaryEvent);
                break;
            default:
                log.warn("未知的数据字典事件类型: {}", dataDictionaryEvent.getEventType());
        }
    }
    
    /**
     * 处理数据字典创建事件
     */
    private void handleDataDictionaryCreated(DataDictionaryEvent dataDictionaryEvent) {
        log.info("处理数据字典创建事件: 数据字典ID={}, 数据字典名称={}", dataDictionaryEvent.getDataDictionaryId(), dataDictionaryEvent.getDataDictionaryName());
        // 在这里可以添加具体的业务逻辑
    }
    
    /**
     * 处理数据字典更新事件
     */
    private void handleDataDictionaryUpdated(DataDictionaryEvent dataDictionaryEvent) {
        log.info("处理数据字典更新事件: 数据字典ID={}, 数据字典名称={}", dataDictionaryEvent.getDataDictionaryId(), dataDictionaryEvent.getDataDictionaryName());
        // 在这里可以添加具体的业务逻辑
    }
    
    /**
     * 处理数据字典删除事件
     */
    private void handleDataDictionaryDeleted(DataDictionaryEvent dataDictionaryEvent) {
        log.info("处理数据字典删除事件: 数据字典ID={}, 数据字典名称={}", dataDictionaryEvent.getDataDictionaryId(), dataDictionaryEvent.getDataDictionaryName());
        // 在这里可以添加具体的业务逻辑
    }
    
    /**
     * 处理数据字典导入事件
     */
    private void handleDataDictionaryImported(DataDictionaryEvent dataDictionaryEvent) {
        log.info("处理数据字典导入事件: 数据字典ID={}, 数据字典名称={}", dataDictionaryEvent.getDataDictionaryId(), dataDictionaryEvent.getDataDictionaryName());
        // 在这里可以添加具体的业务逻辑
    }
}