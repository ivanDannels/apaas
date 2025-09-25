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
package org.apaas.auth.feign;

import org.apaas.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统服务Feign客户端
 * @author ivan
 */
@FeignClient(name = "apaas-system", path = "/system")
public interface SystemFeignClient {
    
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/user/info")
    User getUserByUsername(@RequestParam("username") String username);
    
    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetMapping("/user/permissions/{userId}")
    String[] getUserPermissions(@PathVariable("userId") Long userId);
    
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ip       IP地址
     * @return 结果
     */
    @GetMapping("/user/login")
    void recordLoginInfo(@RequestParam("username") String username, @RequestParam("ip") String ip);
}