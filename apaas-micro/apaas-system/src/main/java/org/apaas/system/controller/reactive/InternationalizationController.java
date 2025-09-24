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
package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.system.entity.Internationalization;
import org.apaas.system.entity.Notification;
import org.apaas.system.service.reactive.ReactiveInternationalizationService;
import org.apaas.system.service.reactive.ReactiveNotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

/**
 * 国际化控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/i18n")
@Tag(name = "国际化管理", description = "国际化管理API")
public class InternationalizationController extends ReactiveBaseController<Internationalization, Long, ReactiveInternationalizationService> {
    
    public InternationalizationController(ReactiveInternationalizationService service) {
        super(service);
    }
    
    /**
     * 切换语言
     *
     * @param language 语言代码，如zh_CN、en等
     * @return 结果
     */
    @GetMapping(value = "/changeLanguage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "切换语言", description = "切换系统语言")
    @Parameter(name = "language", description = "语言代码，如zh_CN、en等", required = true)
    public Mono<Void> changeLanguage(@RequestParam String language) {
        return service.setLanguage(language);
    }
    
    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    @GetMapping(value = "/currentLanguage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取当前语言", description = "获取当前系统语言")
    public Mono<String> getCurrentLanguage() {
        return service.getCurrentLanguage();
    }
    
    /**
     * 获取国际化消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    @GetMapping(value = "/getMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取国际化消息", description = "根据消息键获取当前语言的消息")
    @Parameter(name = "code", description = "消息键", required = true)
    public Mono<String> getMessage(@RequestParam String code) {
        return service.getMessage(code).defaultIfEmpty("Message not found: " + code);
    }
    
    /**
     * 批量获取国际化消息
     *
     * @param codes 消息键数组
     * @return 消息内容映射
     */
    @PostMapping(value = "/getMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量获取国际化消息", description = "批量获取国际化消息")
    @Parameter(name = "codes", description = "消息键数组", required = true)
    public Mono<Map<String, String>> getMessages(@RequestBody String[] codes) {
        return Mono.fromCallable(() -> {
            Map<String, String> messages = new HashMap<>();
            for (String code : codes) {
                messages.put(code, service.getMessage(code).block());
            }
            return messages;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}