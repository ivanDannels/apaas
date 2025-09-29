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
package org.apaas.domain.infrastructure.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apaas.domain.domain.exception.BusinessException;
import org.apaas.domain.domain.exception.CacheException;
import org.apaas.domain.domain.exception.LockAcquisitionException;

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
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", e.getCode());
        result.put("error", "Business Error");
        result.put("message", e.getMessage());
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(e.getCode()).body(result);
    }
    
    /**
     * 处理缓存异常
     */
    @ExceptionHandler(CacheException.class)
    public ResponseEntity<Map<String, Object>> handleCacheException(CacheException e) {
        log.error("缓存异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 500);
        result.put("error", "Cache Error");
        result.put("message", e.getMessage());
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(500).body(result);
    }
    
    /**
     * 处理分布式锁获取异常
     */
    @ExceptionHandler(LockAcquisitionException.class)
    public ResponseEntity<Map<String, Object>> handleLockAcquisitionException(LockAcquisitionException e) {
        log.error("分布式锁获取异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 500);
        result.put("error", "Lock Acquisition Error");
        result.put("message", e.getMessage());
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(500).body(result);
    }
    
    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 500);
        result.put("error", "Runtime Error");
        result.put("message", "系统运行时错误: " + e.getMessage());
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(500).body(result);
    }
    
    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 500);
        result.put("error", "Null Pointer Error");
        result.put("message", "系统空指针错误，请联系管理员");
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(500).body(result);
    }
    
    /**
     * 处理参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 400);
        result.put("error", "Illegal Argument Error");
        result.put("message", "参数错误: " + e.getMessage());
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(400).body(result);
    }
    
    /**
     * 处理方法参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("方法参数验证异常: {}", e.getMessage(), e);
        
        // 获取所有验证错误信息
        String errorMessage = e.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 400);
        result.put("error", "Validation Error");
        result.put("message", "参数验证失败: " + errorMessage);
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(400).body(result);
    }
    
    /**
     * 处理约束违反异常（通常用于@RequestParam或@PathVarible参数验证）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("约束违反异常: {}", e.getMessage(), e);
        
        // 获取所有验证错误信息
        String errorMessage = e.getConstraintViolations().stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).collect(Collectors.joining(", "));
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 400);
        result.put("error", "Constraint Violation Error");
        result.put("message", "参数验证失败: " + errorMessage);
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(400).body(result);
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", LocalDateTime.now());
        result.put("status", 500);
        result.put("error", "Internal Server Error");
        result.put("message", "系统内部错误");
        result.put("path", "/api/v1/**");
        
        return ResponseEntity.status(500).body(result);
    }
}