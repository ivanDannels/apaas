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
package org.apaas.domain.domain.event;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 事件发布服务
 * @author ivan
 */
@Service
public class EventPublisherService {
    
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    
    public EventPublisherService(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }
    
    /**
     * 发布事件
     * 
     * @param topic 事件主题
     * @param event 事件内容
     * @return 发布结果
     */
    public <T> Mono<Boolean> publishEvent(String topic, T event) {
        return reactiveRedisTemplate.convertAndSend(topic, event).map(result -> true).onErrorReturn(false);
    }
}