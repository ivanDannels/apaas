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
package org.apaas.domain.infrastructure.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.constant.Constants;
import org.apaas.core.context.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 多租户WebFlux拦截器
 * @author ivan
 */
@Slf4j
@Component
public class TenantWebFluxInterceptor implements WebFilter {
    
    private static final String TENANT_ID_HEADER = "X-Tenant-Id";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            // 从请求头中获取租户ID
            String tenantIdStr = exchange.getRequest().getHeaders().getFirst(TENANT_ID_HEADER);
            Long tenantId = null;
            
            if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
                try {
                    tenantId = Long.parseLong(tenantIdStr);
                } catch (NumberFormatException e) {
                    log.warn("Invalid tenant ID format: {}", tenantIdStr);
                }
            }
            
            // 如果没有获取到租户ID，则使用默认租户ID
            if (tenantId == null) {
                tenantId = Constants.DEFAULT_TENANT_ID;
            }
            
            // 设置租户ID到上下文中
            TenantContext.setTenantId(tenantId);
            log.debug("Set tenant ID: {}", tenantId);
            
        } catch (Exception e) {
            log.error("Error in tenant interceptor", e);
            // 出现异常时使用默认租户ID
            TenantContext.setTenantId(Constants.DEFAULT_TENANT_ID);
        }
        
        // 继续处理请求
        return chain.filter(exchange).doFinally(signalType -> {
            // 请求结束后清除租户ID
            log.debug("Clear tenant ID");
        });
    }
}