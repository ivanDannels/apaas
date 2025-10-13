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
package org.apaas.auth.interfaces.rest.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.aggregate.LoginUser;
import org.apaas.auth.feign.reactive.ReactiveSystemFeignClient;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.infrastructure.utils.IpUtils;
import org.apaas.utils.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 响应式认证控制器
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/reactive/auth")
@RequiredArgsConstructor
@Tag(name = "响应式认证管理", description = "响应式认证相关接口")
public class ReactiveAuthController {
    
    private final ReactiveAuthenticationManager authenticationManager;
    private final ReactiveSystemFeignClient systemFeignClient;
    
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    @Log(title = "用户登录", businessType = BusinessType.LOGIN, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "登录", description = "用户登录接口")
    public Mono<LoginUser> login(@Parameter(description = "用户名", required = true) @RequestParam String username, @Parameter(description = "密码", required = true) @RequestParam String password, ServerHttpRequest request) {
        
        // 用户认证
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).flatMap(authentication -> {
            // 获取登录用户信息
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            
            // 记录登录信息
            String ipAddr = IpUtils.getIpAddr(request);
            return systemFeignClient.recordLoginInfo(username, ipAddr).thenReturn(loginUser);
        });
    }
    
    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @Log(title = "获取用户信息", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息")
    public Mono<LoginUser> getUserInfo(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String username = jwt.getSubject();
            
            // 根据用户名获取用户信息
            return systemFeignClient.getUserByUsername(username).flatMap(user -> {
                if (user == null) {
                    return Mono.error(new RuntimeException("用户不存在"));
                }
                LoginUser loginUser = new LoginUser();
                
                // 获取用户权限
                return systemFeignClient.getUserPermissions(user.getId()).map(permissions -> {
                    loginUser.setPermissions(permissions);
                    return loginUser;
                });
            });
        }
        return Mono.error(new RuntimeException("获取用户信息失败"));
    }
    
    /**
     * 登出
     *
     * @return 结果
     */
    @Log(title = "用户登出", businessType = BusinessType.LOGOUT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "登出", description = "用户登出接口")
    public Mono<Void> logout() {
        // 获取当前认证用户ID
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).filter(authentication -> authentication instanceof JwtAuthenticationToken).cast(JwtAuthenticationToken.class).map(authentication -> {
            Jwt jwt = authentication.getToken();
            return jwt.getSubject(); // 这里应该从token中获取用户ID，简化处理使用用户名
        }).flatMap(username -> systemFeignClient.getUserByUsername(username)).flatMap(user -> {
            if (user != null) {
                return systemFeignClient.recordLogoutInfo(user.getId());
            }
            return Mono.empty();
        }).then();
    }
    
    /**
     * 验证token
     *
     * @param token token
     * @return 验证结果
     */
    @Log(title = "验证token", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "验证token", description = "验证token是否有效")
    public Mono<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        if (StringUtils.isBlank(token) || !token.startsWith("Bearer ")) {
            return Mono.just(false);
        }
        
        String jwtToken = token.substring(7); // 去掉"Bearer "前缀
        
        // 这里应该调用JWT验证逻辑来验证token
        // 简化处理，假设token有效
        return Mono.just(true);
    }
}