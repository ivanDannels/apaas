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
package org.apaas.auth.feign.reactive;

import org.apaas.auth.domain.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * 响应式系统服务Feign客户��?
 * @author ivan
 */
@Component
@HttpExchange(url = "/api/v1/reactive/system")
public interface ReactiveSystemFeignClient {
    
    /**
     * 根据用户名获取用户信��?
     *
     * @param username 用户��?
     * @return 用户信息
     */
    @GetExchange("/user/username/{username}")
    Mono<User> getUserByUsername(@PathVariable("username") String username);
    
    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetExchange("/user/{userId}/permissions")
    Mono<String[]> getUserPermissions(@PathVariable("userId") Long userId);
    
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ipAddr   IP地址
     * @return 结果
     */
    @PostExchange("/user/login/record")
    Mono<Void> recordLoginInfo(@RequestParam("username") String username, @RequestParam("ipAddr") String ipAddr);
    
    /**
     * 记录登出信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @PostExchange("/user/logout/record")
    Mono<Void> recordLogoutInfo(@RequestParam("userId") Long userId);
}
