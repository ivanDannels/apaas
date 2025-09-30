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
package org.apaas.knowledge.application.service;

import org.apaas.knowledge.domain.model.Knowledge;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 知识库应用服务接口，定义知识库的核心业务操作
 * @author ivan
 */
public interface KnowledgeApplicationService {
    
    /**
     * 上传文件到知识库
     */
    Map<String, Object> uploadFile(MultipartFile file);
    
    /**
     * 获取所有已上传的文件
     */
    List<Knowledge> getAllFiles();
    
    /**
     * 删除指定文件
     */
    Map<String, Object> deleteFile(String fileName);
    
    /**
     * 批量删除文件
     */
    Map<String, Object> batchDeleteFiles(List<String> fileNames);
    
    /**
     * 清空知识库
     */
    Map<String, Object> clearKnowledgeBase();
    
    /**
     * 选择文件用于问答
     */
    Map<String, Object> selectFiles(List<String> fileNames);
    
    /**
     * 排除单个文件
     */
    Map<String, Object> excludeFile(String fileName);
    
    /**
     * 与知识库交互进行问答
     */
    String chatWithKnowledge(String query);
    
    /**
     * 流式与知识库交互进行问答
     */
    Flux<ChatResponse> chatWithKnowledgeStream(String query);
}