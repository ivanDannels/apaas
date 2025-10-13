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
package org.apaas.auth.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证配置属性
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    
    /**
     * 令牌有效期（秒）
     */
    private Integer tokenValiditySeconds = 1800;
    
    /**
     * 刷新令牌有效期（秒）
     */
    private Integer refreshTokenValiditySeconds = 604800;
    
    /**
     * 客户端配置
     */
    private List<Client> clients = new ArrayList<>();
    
    /**
     * 客户端配置
     */
    @Data
    public static class Client {
        
        /**
         * 客户端ID
         */
        private String clientId;
        
        /**
         * 客户端密钥
         */
        private String clientSecret;
        
        /**
         * 重定向URI
         */
        private String redirectUris;
        
        /**
         * 授权范围
         */
        private String scopes;
    }
}