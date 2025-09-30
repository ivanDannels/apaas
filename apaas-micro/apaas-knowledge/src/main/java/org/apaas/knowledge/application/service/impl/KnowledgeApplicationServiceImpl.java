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
package org.apaas.knowledge.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.application.service.KnowledgeApplicationService;
import org.apaas.knowledge.domain.model.Knowledge;
import org.apaas.knowledge.domain.service.KnowledgeRepository;
import org.apaas.knowledge.domain.service.LLMService;
import org.apaas.knowledge.domain.service.VectorStoreRepository;
//import org.apaas.knowledge.infrastructure.client.FileStorageClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 知识库应用服务的实现类
 * @author ivan
 */
@Service
@RequiredArgsConstructor
public class KnowledgeApplicationServiceImpl implements KnowledgeApplicationService {
    
    // 配置存放知识库的目录
    private static final String CONTRACTS_DIR = "text_directory";
    
    // 设置每次向量数据库检索获取的前3条
    private static final int K_INDEX = 3;
    
    // 用于存储当前选择使用的文件列表
    private final List<String> selectedFiles = new ArrayList<>();
    
    private final KnowledgeRepository knowledgeRepository;
    
    private final VectorStoreRepository vectorStoreRepository;
    
    private final LLMService llmService;
    
    // private final FileStorageClient fileStorageClient;
    
    @Override
    public Map<String, Object> uploadFile(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 验证文件类型
            String fileName = file.getOriginalFilename();
            if (fileName == null || !(fileName.endsWith(".txt") || fileName.endsWith(".md"))) {
                result.put("success", false);
                result.put("error", "只支持.txt和.md格式的文件");
                return result;
            }
            
            // 验证文件大小（限制为10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                result.put("success", false);
                result.put("error", "文件大小不能超过10MB");
                return result;
            }
            
            // 调用远程文件存储服务上传文件
            // fileStorageClient.uploadFile(file, CONTRACTS_DIR);
            
            // 读取文件内容
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            
            // 创建文档对象
            Knowledge knowledge = new Knowledge(content, fileName, file.getSize(), System.currentTimeMillis());
            
            // 保存文档信息
            knowledgeRepository.save(knowledge);
            
            // 将新上传的文件添加到selected_files中
            if (!selectedFiles.contains(fileName)) {
                selectedFiles.add(fileName);
                knowledge.setSelected(true);
            }
            
            // 重新加载选择的文件并创建向量数据库
            List<Knowledge> knowledgeList = loadSelectedDocuments();
            if (!knowledgeList.isEmpty()) {
                vectorStoreRepository.createVectorStore(knowledgeList);
            }
            
            result.put("success", true);
            result.put("message", "文件上传成功");
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public List<Knowledge> getAllFiles() {
        return knowledgeRepository.findAll();
    }
    
    @Override
    public Map<String, Object> deleteFile(String fileName) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (fileName == null || fileName.isEmpty()) {
                result.put("success", false);
                result.put("error", "文件名不能为空");
                return result;
            }
            
            // 检查文件是否存在
            if (!knowledgeRepository.existsByFileName(fileName)) {
                result.put("success", false);
                result.put("error", "文件不存在");
                return result;
            }
            
            // 验证文件类型
            if (!(fileName.endsWith(".txt") || fileName.endsWith(".md"))) {
                result.put("success", false);
                result.put("error", "只能删除.txt和.md格式的文件");
                return result;
            }
            
            // 删除文件
            // fileStorageClient.deleteFile(fileName, CONTRACTS_DIR);
            knowledgeRepository.deleteByFileName(fileName);
            
            // 如果该文件在已选择列表中，移除它
            selectedFiles.remove(fileName);
            
            // 重新加载选择的文件
            List<Knowledge> loadSelectedDocuments = loadSelectedDocuments();
            
            // 更新向量数据库
            if (!loadSelectedDocuments.isEmpty()) {
                vectorStoreRepository.createVectorStore(loadSelectedDocuments);
            } else {
                vectorStoreRepository.clearVectorStore();
            }
            
            result.put("success", true);
            result.put("message", "文件 \"" + fileName + "\" 已成功删除");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> batchDeleteFiles(List<String> fileNames) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (fileNames == null || fileNames.isEmpty()) {
                result.put("success", false);
                result.put("error", "文件名列表不能为空");
                return result;
            }
            
            int deletedCount = 0;
            
            for (String fileName : fileNames) {
                if (knowledgeRepository.existsByFileName(fileName) && (fileName.endsWith(".txt") || fileName.endsWith(".md"))) {
                    
                    // fileStorageClient.deleteFile(fileName, CONTRACTS_DIR);
                    knowledgeRepository.deleteByFileName(fileName);
                    deletedCount++;
                    
                    // 如果该文件在已选择列表中，移除它
                    selectedFiles.remove(fileName);
                }
            }
            
            // 重新加载选择的文件
            List<Knowledge> knowledges = loadSelectedDocuments();
            
            // 更新向量数据库
            if (!knowledges.isEmpty()) {
                vectorStoreRepository.createVectorStore(knowledges);
            } else {
                vectorStoreRepository.clearVectorStore();
            }
            
            result.put("success", true);
            result.put("message", "已成功删除 " + deletedCount + " 个文件");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> clearKnowledgeBase() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 清空选择的文件列表
            selectedFiles.clear();
            
            // 删除所有文件
            List<Knowledge> allKnowledges = knowledgeRepository.findAll();
            List<String> fileNames = allKnowledges.stream().map(Knowledge::getFileName).toList();
            
            // fileStorageClient.deleteAllFiles(CONTRACTS_DIR);
            knowledgeRepository.deleteAllByFileNames(fileNames);
            
            // 清空向量存储
            vectorStoreRepository.clearVectorStore();
            
            result.put("success", true);
            result.put("message", "知识库已清空");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> selectFiles(List<String> fileNames) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 更新选择的文件列表
            selectedFiles.clear();
            if (fileNames != null && !fileNames.isEmpty()) {
                selectedFiles.addAll(fileNames);
            }
            
            // 更新文档的选择状态
            List<Knowledge> knowledgeRepositoryAll = knowledgeRepository.findAll();
            for (Knowledge knowledge : knowledgeRepositoryAll) {
                knowledge.setSelected(selectedFiles.contains(knowledge.getFileName()));
            }
            
            // 加载选择的文件
            List<Knowledge> loadSelectedDocuments = loadSelectedDocuments();
            
            // 更新向量数据库
            if (!loadSelectedDocuments.isEmpty()) {
                vectorStoreRepository.createVectorStore(loadSelectedDocuments);
            } else {
                vectorStoreRepository.clearVectorStore();
            }
            
            if (!selectedFiles.isEmpty()) {
                result.put("success", true);
                result.put("message", "已成功选择 " + selectedFiles.size() + " 个文件作为知识库");
            } else {
                result.put("success", true);
                result.put("message", "未找到有效的文件");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> excludeFile(String fileName) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 从已选择列表中移除文件
            if (fileName != null) {
                selectedFiles.remove(fileName);
            }
            
            // 更新文档的选择状态
            Optional<Knowledge> documentOpt = knowledgeRepository.findByFileName(fileName);
            documentOpt.ifPresent(document -> document.setSelected(false));
            
            // 加载选择的文件
            List<Knowledge> loadSelectedDocuments = loadSelectedDocuments();
            
            // 更新向量数据库
            if (!loadSelectedDocuments.isEmpty()) {
                vectorStoreRepository.createVectorStore(loadSelectedDocuments);
            } else {
                vectorStoreRepository.clearVectorStore();
            }
            
            result.put("success", true);
            result.put("message", "文件已从知识库中排除");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public String chatWithKnowledge(String query) {
        // 构建提示词
        String prompt = buildPrompt(query);
        
        // 调用LLM生成响应
        return llmService.generateText(prompt);
    }
    
    @Override
    public Flux<ChatResponse> chatWithKnowledgeStream(String query) {
        // 构建提示词
        String prompt = buildPrompt(query);
        
        // 调用LLM流式生成响应
        return llmService.generateTextStream(prompt);
    }
    
    /**
     * 加载已选择的文档
     */
    private List<Knowledge> loadSelectedDocuments() {
        List<Knowledge> knowledgeList = new ArrayList<>();
        for (String fileName : selectedFiles) {
            Optional<Knowledge> documentOpt = knowledgeRepository.findByFileName(fileName);
            documentOpt.ifPresent(knowledgeList::add);
        }
        return knowledgeList;
    }
    
    /**
     * 构建提示词
     */
    private String buildPrompt(String query) {
        StringBuilder promptBuilder = new StringBuilder();
        
        // 只有当用户选择了文件时才使用向量数据库检索
        if (!selectedFiles.isEmpty() && vectorStoreRepository.hasVectorStore()) {
            List<Knowledge> similarDocs = vectorStoreRepository.searchSimilar(query, K_INDEX);
            for (Knowledge doc : similarDocs) {
                promptBuilder.append(doc.getContent()).append("\n");
            }
        }
        
        promptBuilder.append("Question: ").append(query).append("\nAnswer:");
        
        return promptBuilder.toString();
    }
}