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
package org.apaas.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.domain.domain.service.AbstractDomainService;
import org.apaas.system.entity.Internationalization;
import org.apaas.system.repository.InternationalizationRepository;
import org.apaas.system.service.ReactiveInternationalizationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * 响应式国际化服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveInternationalizationServiceImpl extends AbstractApplicationService<Internationalization, Long, InternationalizationRepository> implements ReactiveInternationalizationService {
    
    private final MessageSource messageSource;
    
    public ReactiveInternationalizationServiceImpl(InternationalizationRepository repository, MessageSource messageSource) {
        super(repository);
        this.messageSource = messageSource;
    }
    
    @Override
    public Mono<String> getMessage(String code) {
        return getMessage(code, null);
    }
    
    @Override
    public Mono<String> getMessage(String code, Object[] args) {
        return getMessage(code, args, LocaleContextHolder.getLocale());
    }
    
    @Override
    public Mono<String> getMessage(String code, Object[] args, Locale locale) {
        try {
            String message = messageSource.getMessage(code, args, code, locale);
            return Mono.just(message);
        } catch (Exception e) {
            log.error("获取国际化消息失败", e);
            return Mono.just(code);
        }
    }
    
    @Override
    public Mono<Void> setLanguage(String language) {
        try {
            Locale locale;
            if (language.contains("_")) {
                String[] parts = language.split("_");
                locale = new Locale(parts[0], parts[1]);
            } else {
                locale = new Locale(language);
            }
            LocaleContextHolder.setLocale(locale);
            return Mono.empty();
        } catch (Exception e) {
            log.error("设置语言失败", e);
            return Mono.error(e);
        }
    }
    
    @Override
    public Mono<String> getCurrentLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        return Mono.just(locale.toString());
    }
}