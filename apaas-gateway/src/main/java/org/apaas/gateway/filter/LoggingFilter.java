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
package org.apaas.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 日志过滤器
 * @author ivan
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    
    private static final String START_TIME = "startTime";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().name();
        HttpHeaders headers = request.getHeaders();
        
        // 记录请求开始时间
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        
        // 打印请求日志
        log.debug("Request: {} {}, Headers: {}", method, path, headers);
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 计算请求耗时
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                long executeTime = System.currentTimeMillis() - startTime;
                log.debug("Response: {} {} completed in {}ms, Status: {}", method, path, executeTime, exchange.getResponse().getStatusCode());
            }
        }));
    }
    
    @Override
    public int getOrder() {
        return -200;
    }
    
}