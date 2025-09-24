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
package org.apaas.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 降级处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FallbackHandler {
    
    private final ObjectMapper objectMapper;
    
    /**
     * 处理降级响应
     */
    public Mono<ServerResponse> handleFallback(ServerWebExchange exchange, Throwable throwable) {
        log.error("Service call failed", throwable);
        return buildFallbackResponse(exchange, throwable);
    }
    
    /**
     * 构建降级响应
     */
    private Mono<ServerResponse> buildFallbackResponse(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", HttpStatus.SERVICE_UNAVAILABLE.value());
        result.put("msg", "服务暂时不可用，请稍后再试");
        result.put("data", null);
        
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(result);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON).bodyValue(buffer);
        } catch (JsonProcessingException e) {
            log.error("Error writing fallback response", e);
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}