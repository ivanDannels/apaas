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
package org.apaas.infrastructure.log;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.UUID;

/**
 * 日志追踪配置类
 * @author ivan
 */
@Configuration
public class TraceConfig {
    
    @PostConstruct
    public void init() {
        // 初始化时设置默认的追踪ID
        String traceId = TraceContext.traceId();
        if (traceId.isEmpty() || "N/A".equals(traceId)) {
            // 如果没有SkyWalking追踪ID，则生成一个UUID作为追踪ID
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        MDC.put("traceId", traceId);
    }
}