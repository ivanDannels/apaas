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

import java.time.LocalDateTime;

/**
 * 基础事件类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode
public abstract class BaseEvent {
    
    /**
     * 事件类型
     */
    private String eventType;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 事件源
     */
    private String source;
    
    /**
     * 事件描述
     */
    private String description;
    
    public BaseEvent() {
        this.eventTime = LocalDateTime.now();
    }
    
    public BaseEvent(String eventType, String source, String description) {
        this();
        this.eventType = eventType;
        this.source = source;
        this.description = description;
    }
}