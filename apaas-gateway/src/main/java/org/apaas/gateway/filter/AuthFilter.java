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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.utils.StringUtils;
import org.apaas.gateway.feign.AuthServiceFeignClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 认证过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    
    private final AuthServiceFeignClient authServiceFeignClient;
    
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";
    
    /**
     * 白名单路径，不需要认证
     */
    private static final List<String> WHITE_LIST = Arrays.asList("/auth/login", "/auth/captcha", "/auth/register", "/actuator", "/swagger-ui", "/swagger-resources", "/v3/api-docs", "/webjars", "/doc.html");
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        
        // 白名单路径直接放行
        if (isWhitePath(path)) {
            return chain.filter(exchange);
        }
        
        // 获取token
        String token = request.getHeaders().getFirst(AUTH_HEADER);
        if (StringUtils.isBlank(token) || !token.startsWith(TOKEN_PREFIX)) {
            log.warn("Token is missing or invalid: {}", token);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        
        // 调用认证服务验证token
        return authServiceFeignClient.validateToken(token).flatMap(isValid -> {
            if (isValid) {
                // Token有效，解析用户信息
                // 这里简化处理，实际应该从token中解析用户ID和用户名
                String userId = "1";
                String userName = "admin";
                
                // 将用户信息添加到请求头中，传递给下游服务
                ServerHttpRequest newRequest = request.mutate().header(USER_ID_HEADER, userId).header(USER_NAME_HEADER, userName).build();
                
                return chain.filter(exchange.mutate().request(newRequest).build());
            } else {
                // Token无效
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }).onErrorResume(throwable -> {
            // 处理验证过程中的异常
            log.error("Token validation failed", throwable);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        });
    }
    
    @Override
    public int getOrder() {
        return -50;
    }
    
    /**
     * 判断是否为白名单路径
     */
    private boolean isWhitePath(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }
}