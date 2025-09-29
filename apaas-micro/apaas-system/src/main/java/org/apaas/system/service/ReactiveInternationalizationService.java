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
package org.apaas.system.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.system.entity.Internationalization;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * 响应式国际化服务接口
 * @author ivan
 */
public interface ReactiveInternationalizationService extends ApplicationService<Internationalization, Long> {
    
    /**
     * 根据消息键获取当前语言的消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    Mono<String> getMessage(String code);
    
    /**
     * 根据消息键和参数获取当前语言的消息
     *
     * @param code 消息键
     * @param args 参数数组
     * @return 消息内容
     */
    Mono<String> getMessage(String code, Object[] args);
    
    /**
     * 根据消息键、参数和语言获取消息
     *
     * @param code   消息键
     * @param args   参数数组
     * @param locale 语言
     * @return 消息内容
     */
    Mono<String> getMessage(String code, Object[] args, Locale locale);
    
    /**
     * 设置当前语言
     *
     * @param language 语言代码，如zh_CN、en等
     */
    Mono<Void> setLanguage(String language);
    
    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    Mono<String> getCurrentLanguage();
}