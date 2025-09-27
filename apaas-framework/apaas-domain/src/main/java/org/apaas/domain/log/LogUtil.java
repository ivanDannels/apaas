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
package org.apaas.domain.log;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日志工具类
 * @author ivan
 */
public class LogUtil {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    /**
     * 获取带追踪信息的日志前缀
     * 
     * @return 日志前缀
     */
    public static String getLogPrefix() {
        StringBuilder prefix = new StringBuilder();
        
        // 添加时间戳
        prefix.append("[").append(LocalDateTime.now().format(FORMATTER)).append("]");
        
        // 添加追踪ID
        String traceId = TraceContext.traceId();
        if (traceId != null && !traceId.isEmpty() && !"N/A".equals(traceId)) {
            prefix.append("[TraceId:").append(traceId).append("]");
        } else {
            // 如果没有SkyWalking追踪ID，使用MDC中的追踪ID
            String mdcTraceId = MDC.get("traceId");
            if (mdcTraceId != null && !mdcTraceId.isEmpty()) {
                prefix.append("[TraceId:").append(mdcTraceId).append("]");
            }
        }
        
        // 添加线程信息
        prefix.append("[Thread:").append(Thread.currentThread().getName()).append("]");
        
        return prefix.toString();
    }
    
    /**
     * 记录信息日志
     * 
     * @param clazz 类
     * @param message 消息
     * @param args 参数
     */
    public static void info(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger.isInfoEnabled()) {
            logger.info("{} {}", getLogPrefix(), String.format(message, args));
        }
    }
    
    /**
     * 记录调试日志
     * 
     * @param clazz 类
     * @param message 消息
     * @param args 参数
     */
    public static void debug(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug("{} {}", getLogPrefix(), String.format(message, args));
        }
    }
    
    /**
     * 记录警告日志
     * 
     * @param clazz 类
     * @param message 消息
     * @param args 参数
     */
    public static void warn(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger.isWarnEnabled()) {
            logger.warn("{} {}", getLogPrefix(), String.format(message, args));
        }
    }
    
    /**
     * 记录错误日志
     * 
     * @param clazz 类
     * @param message 消息
     * @param throwable 异常
     */
    public static void error(Class<?> clazz, String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger.isErrorEnabled()) {
            logger.error("{} {}", getLogPrefix(), message, throwable);
        }
    }
    
    /**
     * 记录错误日志
     * 
     * @param clazz 类
     * @param message 消息
     * @param args 参数
     */
    public static void error(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger.isErrorEnabled()) {
            logger.error("{} {}", getLogPrefix(), String.format(message, args));
        }
    }
}