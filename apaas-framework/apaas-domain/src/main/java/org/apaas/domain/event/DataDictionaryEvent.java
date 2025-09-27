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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典事件类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DataDictionaryEvent extends BaseEvent {
    
    /**
     * 数据字典ID
     */
    private Long dataDictionaryId;
    
    /**
     * 数据字典名称
     */
    private String dataDictionaryName;
    
    /**
     * 操作类型
     */
    private String operationType;
    
    public DataDictionaryEvent() {
        super();
    }
    
    public DataDictionaryEvent(String eventType, Long dataDictionaryId, String dataDictionaryName, String operationType, String description) {
        super(eventType, "data-dictionary-service", description);
        this.dataDictionaryId = dataDictionaryId;
        this.dataDictionaryName = dataDictionaryName;
        this.operationType = operationType;
    }
}