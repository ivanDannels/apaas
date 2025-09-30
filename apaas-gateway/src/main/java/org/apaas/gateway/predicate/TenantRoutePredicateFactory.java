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
package org.apaas.gateway.predicate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 租户路由断言工厂
 * @author ivan
 */
@Slf4j
@Component
public class TenantRoutePredicateFactory extends AbstractRoutePredicateFactory<TenantRoutePredicateFactory.Config> {
    
    private static final String TENANT_ID_HEADER = "X-Tenant-Id";
    
    public TenantRoutePredicateFactory() {
        super(Config.class);
    }
    
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("tenantId");
    }
    
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            String tenantId = exchange.getRequest().getHeaders().getFirst(TENANT_ID_HEADER);
            if (tenantId == null || tenantId.isEmpty()) {
                return false;
            }
            boolean match = config.getTenantId().equals(tenantId);
            log.debug("Tenant predicate match: {}, tenantId: {}, config: {}", match, tenantId, config.getTenantId());
            return match;
        };
    }
    
    @Data
    public static class Config {
        
        private String tenantId;
    }
}