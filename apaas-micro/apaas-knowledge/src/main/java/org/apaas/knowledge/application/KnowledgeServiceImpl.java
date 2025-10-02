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

import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.domain.DocumentProcessingService;
import org.apaas.knowledge.domain.RagService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

/**
 * @author ivan
 */
@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {
    
    private final DocumentProcessingService documentProcessingService;
    private final RagService ragService;
    
    @Override
    public Mono<String> uploadDocument(MultipartFile file) {
        return Mono.fromRunnable(() -> {
            try {
                documentProcessingService.processUploadedFile(file);
            } catch (IOException e) {
                throw new RuntimeException("处理文档时发生错误: " + e.getMessage(), e);
            }
        }).then(Mono.just("文档上传并处理成功"));
    }
    
    @Override
    public Mono<String> loadDocument(Resource resource, String fileType) {
        return Mono.fromRunnable(() -> {
            try {
                var documents = documentProcessingService.parseDocument(resource, fileType);
                documentProcessingService.processAndStoreDocuments(documents);
            } catch (IOException e) {
                throw new RuntimeException("加载文档时发生错误: " + e.getMessage(), e);
            }
        }).then(Mono.just("文档加载并处理成功"));
    }
    
    @Override
    public Mono<String> askQuestion(String question) {
        return ragService.answerQuestion(question);
    }
    
    @Override
    public Mono<String> askQuestion(String question, Map<String, Object> filter) {
        return ragService.answerQuestion(question, filter);
    }
    
    @Override
    public Mono<String> regenerateAnswer(String question, String previousAnswer) {
        return ragService.regenerateAnswer(question, previousAnswer);
    }
    
    @Override
    public Flux<ChatResponse> streamAskQuestion(String question) {
        return ragService.streamAnswerQuestion(question);
    }
    
    @Override
    public Flux<ChatResponse> streamAskQuestion(String question, Map<String, Object> filter) {
        return ragService.streamAnswerQuestion(question, filter);
    }
}