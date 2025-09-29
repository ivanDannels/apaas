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

import org.apaas.knowledge.application.service.KnowledgeApplicationService;
import org.apaas.knowledge.domain.model.Knowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 知识库REST控制器，提供API接口
 */
@RestController
@RequestMapping("/")
public class KnowledgeController {
    
    @Autowired
    private KnowledgeApplicationService knowledgeApplicationService;
    
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    /**
     * 接收前端输入的接口，返回流式响应
     */
    @PostMapping("/chat")
    public SseEmitter chat(@RequestBody String query) {
        SseEmitter emitter = new SseEmitter();
        
        executorService.execute(() -> {
            try {
                knowledgeApplicationService.chatWithKnowledgeStream(query, content -> {
                    try {
                        emitter.send(content);
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                });
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
    
    /**
     * 文件上传接口
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = knowledgeApplicationService.uploadFile(file);
        if ((Boolean) result.getOrDefault("success", false)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 获取已上传文件列表的接口
     */
    @GetMapping("/files")
    public ResponseEntity<List<Knowledge>> getFiles() {
        List<Knowledge> files = knowledgeApplicationService.getAllFiles();
        return ResponseEntity.ok(files);
    }
    
    /**
     * 清空知识库的接口
     */
    @PostMapping("/clear-knowledge")
    public ResponseEntity<Map<String, Object>> clearKnowledge() {
        Map<String, Object> result = knowledgeApplicationService.clearKnowledgeBase();
        if ((Boolean) result.getOrDefault("success", false)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 选择文件的接口 - 支持选择文件和排除单个文件
     */
    @PostMapping("/select-files")
    public ResponseEntity<Map<String, Object>> selectFiles(@RequestBody(required = false) Map<String, Object> requestBody) {
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
        
        if ((Boolean) result.getOrDefault("success", false)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 删除单个文件的接口
     */
    @PostMapping("/delete-file")
    public ResponseEntity<Map<String, Object>> deleteFile(@RequestParam(required = false) String filename) {
        Map<String, Object> result = knowledgeApplicationService.deleteFile(filename);
        if ((Boolean) result.getOrDefault("success", false)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 批量删除文件的接口
     */
    @PostMapping("/batch-delete-files")
    public ResponseEntity<Map<String, Object>> batchDeleteFiles(@RequestParam(required = false) String file_names) {
        List<String> fileNames = file_names != null && !file_names.isEmpty() ? Arrays.asList(file_names.split(",")) : List.of();
        
        Map<String, Object> result = knowledgeApplicationService.batchDeleteFiles(fileNames);
        if ((Boolean) result.getOrDefault("success", false)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}