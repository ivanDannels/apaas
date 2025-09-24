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

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 业务异常
 * @author ivan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private Integer code;
    
    /**
     * 错误提示
     */
    private String message;
    
    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;
    
    /**
     * 空构造方法，避免反序列化问题
     */
    public BusinessException() {
    }
    
    /**
     * 构造方法
     *
     * @param message 错误提示
     */
    public BusinessException(String message) {
        this.code = 500;
        this.message = message;
    }
    
    /**
     * 构造方法
     *
     * @param message 错误提示
     * @param code    错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
    
    /**
     * 构造方法
     *
     * @param message       错误提示
     * @param detailMessage 错误明细
     */
    public BusinessException(String message, String detailMessage) {
        this.code = 500;
        this.message = message;
        this.detailMessage = detailMessage;
    }
    
    /**
     * 构造方法
     *
     * @param message       错误提示
     * @param code          错误码
     * @param detailMessage 错误明细
     */
    public BusinessException(String message, Integer code, String detailMessage) {
        this.message = message;
        this.code = code;
        this.detailMessage = detailMessage;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    /**
     * 创建一个业务异常
     *
     * @param message 错误提示
     * @return 业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
    
}