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
package org.apaas.knowledge.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.application.service.KnowledgeApplicationService;
import org.apaas.knowledge.domain.model.Knowledge;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 知识库REST控制器，提供API接口
 * @author ivan
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class KnowledgeController {
    
    private final KnowledgeApplicationService knowledgeApplicationService;
    
    /**
     * 接收前端输入的接口，返回流式响应
     */
    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> chat(@RequestBody String query) {
        return knowledgeApplicationService.chatWithKnowledgeStream(query);
    }
    
    /**
     * 文件上传接口
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        return knowledgeApplicationService.uploadFile(file);
    }
    
    /**
     * 获取已上传文件列表的接口
     */
    @GetMapping("/files")
    public List<Knowledge> getFiles() {
        return knowledgeApplicationService.getAllFiles();
    }
    
    /**
     * 清空知识库的接口
     */
    @PostMapping("/clear-knowledge")
    public Map<String, Object> clearKnowledge() {
        return knowledgeApplicationService.clearKnowledgeBase();
    }
    
    /**
     * 选择文件的接口 - 支持选择文件和排除单个文件
     */
    @PostMapping("/select-files")
    public Map<String, Object> selectFiles(@RequestBody(required = false) Map<String, Object> requestBody) {
        Map<String, Object> result;
        if (requestBody == null) {
            requestBody = Map.of();
        }
        if (requestBody.containsKey("exclude")) {
            String excludeFile = (String) requestBody.get("exclude");
            result = knowledgeApplicationService.excludeFile(excludeFile);
        } else if (requestBody.containsKey("file_names")) {
            String fileNamesStr = (String) requestBody.get("file_names");
            List<String> fileNames = fileNamesStr != null && !fileNamesStr.isEmpty() ? Arrays.asList(fileNamesStr.split(",")) : List.of();
            result = knowledgeApplicationService.selectFiles(fileNames);
        } else {
            result = knowledgeApplicationService.selectFiles(List.of());
        }
        
        return result;
    }
    
    /**
     * 删除单个文件的接口
     */
    @PostMapping("/delete-file")
    public Map<String, Object> deleteFile(@RequestParam(required = false) String filename) {
        return knowledgeApplicationService.deleteFile(filename);
    }
    
    /**
     * 批量删除文件的接口
     */
    @PostMapping("/batch-delete-files")
    public Map<String, Object> batchDeleteFiles(@RequestParam(required = false) String fileNames) {
        return knowledgeApplicationService.batchDeleteFiles(fileNames != null && !fileNames.isEmpty() ? Arrays.asList(fileNames.split(",")) : List.of());
    }
}