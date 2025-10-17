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
package org.apaas.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BusinessException测试类
 * @author ivan
 */
class BusinessExceptionTest {
    
    @Test
    void testConstructorWithMessage() {
        // Given
        String message = "Test exception message";
        
        // When
        BusinessException exception = new BusinessException(message);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(500, exception.getCode());
        assertNull(exception.getCause());
    }
    
    @Test
    void testConstructorWithCodeAndMessage() {
        // Given
        int code = 400;
        String message = "Test exception message";
        
        // When
        BusinessException exception = new BusinessException(code, message);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(code, exception.getCode());
        assertNull(exception.getCause());
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        // Given
        String message = "Test exception message";
        Throwable cause = new RuntimeException("Cause");
        
        // When
        BusinessException exception = new BusinessException(message, cause);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(500, exception.getCode());
        assertEquals(cause, exception.getCause());
    }
    
    @Test
    void testConstructorWithCodeMessageAndCause() {
        // Given
        int code = 400;
        String message = "Test exception message";
        Throwable cause = new RuntimeException("Cause");
        
        // When
        BusinessException exception = new BusinessException(code, message, cause);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(code, exception.getCode());
        assertEquals(cause, exception.getCause());
    }
    
    @Test
    void testSetCode() {
        // Given
        BusinessException exception = new BusinessException("Test message");
        int newCode = 404;
        
        // When
        exception.setCode(newCode);
        
        // Then
        assertEquals(newCode, exception.getCode());
    }
    
    @Test
    void testGetCode() {
        // Given
        int code = 403;
        BusinessException exception = new BusinessException(code, "Test message");
        
        // When
        int result = exception.getCode();
        
        // Then
        assertEquals(code, result);
    }
}