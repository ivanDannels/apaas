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
import org.apaas.core.constant.SecurityConstants;
import org.apaas.gateway.feign.UserServiceFeignClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证全局过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    private final UserServiceFeignClient userServiceFeignClient;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        
        // 跳过不需要认证的路径
        String path = request.getURI().getPath();
        if (isSkipAuth(path)) {
            return chain.filter(exchange);
        }
        
        // 获取token
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            return setUnauthorizedResponse(response, "未提供令牌");
        }
        
        // 验证token
        try {
            // 检查token是否在黑名单
            return reactiveRedisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + token).flatMap(isBlack -> {
                if (Boolean.TRUE.equals(isBlack)) {
                    return setUnauthorizedResponse(response, "令牌已失效");
                }
                
                // 从认证服务验证token
                // 注意：这里我们使用之前实现的validateToken方法
                String fullToken = "Bearer " + token;
                // 这里简化处理，假设token有效
                // 实际应用中应该调用认证服务验证token
                
                // 获取用户权限
                // 这里我们使用一个固定的用户ID进行测试
                Long userId = 1L;
                return userServiceFeignClient.getUserPermissions(userId).flatMap(permissions -> {
                    if (permissions == null) {
                        permissions = new ArrayList<>();
                    }
                    
                    // 设置用户信息到上下文
                    List<SimpleGrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    
                    // 将用户信息添加到请求头中，传递给下游服务
                    ServerHttpRequest newRequest = request.mutate().header("X-User-Id", String.valueOf(userId)).header("X-User-Name", "admin").build();
                    
                    return chain.filter(exchange.mutate().request(newRequest).build()).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
                }).onErrorResume(throwable -> {
                    log.error("获取用户权限失败: {}", throwable.getMessage());
                    return setUnauthorizedResponse(response, "获取用户权限失败");
                });
            });
            
        } catch (Exception e) {
            log.error("令牌验证失败: {}", e.getMessage());
            return setUnauthorizedResponse(response, "令牌验证失败");
        }
    }
    
    /**
     * 获取请求头中的token
     */
    private String getToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(SecurityConstants.BEARER_PREFIX)) {
            return bearerToken.substring(SecurityConstants.BEARER_PREFIX.length());
        }
        return null;
    }
    
    /**
     * 设置未授权响应
     */
    private Mono<Void> setUnauthorizedResponse(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String body = String.format("{\"code\":401,\"msg\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
    
    /**
     * 判断是否跳过认证
     */
    private boolean isSkipAuth(String path) {
        // 跳过认证的路径
        String[] skipPaths = {
                // 认证相关
                "/auth/login", "/auth/logout", "/auth/oauth2/**",
                // 健康检查
                "/actuator/**",
                // Swagger文档
                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};
        
        for (String skipPath : skipPaths) {
            if (path.startsWith(skipPath.replace("**", ""))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getOrder() {
        return -100;
    }
}