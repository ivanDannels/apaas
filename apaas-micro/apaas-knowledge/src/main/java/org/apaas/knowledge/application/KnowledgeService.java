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
package org.apaas.knowledge.application;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author ivan
 */
public interface KnowledgeService {
    
    /**
     * 上传并处理文档
     * @param file 上传的文件
     * @return 处理结果
     */
    Mono<String> uploadDocument(MultipartFile file);
    
    /**
     * 从资源路径加载并处理文档
     * @param resource 文档资源
     * @param fileType 文件类型
     * @return 处理结果
     */
    Mono<String> loadDocument(Resource resource, String fileType);
    
    /**
     * 回答用户问题
     * @param question 用户问题
     * @return 回答内容
     */
    Mono<String> askQuestion(String question);
    
    /**
     * 回答用户问题（带过滤条件）
     * @param question 用户问题
     * @param filter 过滤条件
     * @return 回答内容
     */
    Mono<String> askQuestion(String question, Map<String, Object> filter);
    
    /**
     * 重新生成回答
     * @param question 用户问题
     * @param previousAnswer 之前的回答
     * @return 新的回答
     */
    Mono<String> regenerateAnswer(String question, String previousAnswer);
    
    /**
     * 流式响应：回答用户问题
     * @param question 用户问题
     * @return 流式回答内容
     */
    Flux<ChatResponse> streamAskQuestion(String question);
    
    /**
     * 流式响应：回答用户问题（带过滤条件）
     * @param question 用户问题
     * @param filter 过滤条件
     * @return 流式回答内容
     */
    Flux<ChatResponse> streamAskQuestion(String question, Map<String, Object> filter);
}