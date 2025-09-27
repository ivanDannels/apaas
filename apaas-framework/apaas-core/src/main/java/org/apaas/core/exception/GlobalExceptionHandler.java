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
package org.apaas.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * @author ivan
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", e.getCode());
        result.put("error", "Business Error");
        result.put("message", e.getMessage());
        result.put("path", "/api/v1/**");
        
        return Mono.just(ResponseEntity.status(e.getCode()).body(result));
    }
    
    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationException(WebExchangeBindException e) {
        log.error("参数验证异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", HttpStatus.BAD_REQUEST.value());
        result.put("error", "Validation Error");
        result.put("message", "参数验证失败");
        result.put("path", "/api/v1/**");
        result.put("details", e.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).toArray(String[]::new));
        
        return Mono.just(ResponseEntity.badRequest().body(result));
    }
    
    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("约束违反异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", HttpStatus.BAD_REQUEST.value());
        result.put("error", "Constraint Violation");
        result.put("message", "约束验证失败");
        result.put("path", "/api/v1/**");
        result.put("details", e.getConstraintViolations().stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toArray(String[]::new));
        
        return Mono.just(ResponseEntity.badRequest().body(result));
    }
    
    /**
     * 处理Redisson锁获取失败异常
     */
    @ExceptionHandler(LockAcquisitionException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleLockAcquisitionException(LockAcquisitionException e) {
        log.error("分布式锁获取失败: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", HttpStatus.CONFLICT.value());
        result.put("error", "Lock Acquisition Failed");
        result.put("message", "获取分布式锁失败，请稍后重试");
        result.put("path", "/api/v1/**");
        
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(result));
    }
    
    /**
     * 处理缓存相关异常
     */
    @ExceptionHandler(CacheException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleCacheException(CacheException e) {
        log.error("缓存异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("error", "Cache Error");
        result.put("message", "缓存操作失败");
        result.put("path", "/api/v1/**");
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result));
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("error", "Internal Server Error");
        result.put("message", "系统内部错误");
        result.put("path", "/api/v1/**");
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result));
    }
}