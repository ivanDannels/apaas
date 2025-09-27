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
package org.apaas.core.log;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 日志追踪配置类
 * @author ivan
 */
@Configuration
public class TraceConfig implements WebFilter {
    
    private static final String TRACE_ID = "traceId";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 尝试从SkyWalking获取追踪ID
        String traceId = TraceContext.traceId();
        
        // 如果SkyWalking不可用，生成一个新的追踪ID
        if (traceId == null || traceId.isEmpty() || "N/A".equals(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        
        // 将追踪ID放入MDC中
        MDC.put(TRACE_ID, traceId);
        
        // 继续处理请求
        return chain.filter(exchange).doFinally(signalType -> {
            // 请求处理完成后清理MDC
            MDC.remove(TRACE_ID);
        });
    }
}