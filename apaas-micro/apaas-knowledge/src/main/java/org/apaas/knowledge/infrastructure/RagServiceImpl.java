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
package org.apaas.knowledge.infrastructure;

import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.application.dto.KnowledgeDto;
import org.apaas.knowledge.domain.RagService;
import org.apaas.knowledge.domain.VectorStoreService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ivan
 */
@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {
    
    private final VectorStoreService vectorStoreService;
    private final OllamaChatModel ollamaChatModel;
    
    @Value("${app.rag.prompt.template}")
    private String promptTemplate;
    
    @Value("${app.rag.search.limit:5}")
    private int searchLimit;
    
    @Override
    public Mono<String> answerQuestion(String question) {
        return answerQuestion(question, new HashMap<>());
    }
    
    @Override
    public Mono<String> answerQuestion(String question, Map<String, Object> filterMetadata) {
        return createPrompt(question, filterMetadata).mapNotNull(prompt -> ollamaChatModel.call(prompt).getResult().getOutput().getText());
    }
    
    @Override
    public Mono<String> regenerateAnswer(String question, String previousAnswer) {
        String regeneratePrompt = """
                原问题: {question}
                
                之前回答: {previousAnswer}
                
                请重新组织语言，提供一个更清晰、更准确的回答。
                """;
        
        PromptTemplate template = new PromptTemplate(regeneratePrompt);
        Map<String, Object> model = new HashMap<>();
        model.put("question", question);
        model.put("previousAnswer", previousAnswer);
        
        Prompt prompt = template.create(model);
        
        return Mono.just(Objects.requireNonNull(ollamaChatModel.call(prompt).getResult().getOutput().getText()));
    }
    
    @Override
    public Flux<ChatResponse> streamAnswerQuestion(String question) {
        return streamAnswerQuestion(question, new HashMap<>());
    }
    
    @Override
    public Flux<ChatResponse> streamAnswerQuestion(String question, Map<String, Object> filterMetadata) {
        // 3. 调用大模型生成流式回答
        return createPrompt(question, filterMetadata).flatMapMany(ollamaChatModel::stream);
    }
    
    private Mono<Prompt> createPrompt(String question, Map<String, Object> filterMetadata) {
        // 1. 搜索相似文档
        return vectorStoreService.searchSimilarDocuments(question, filterMetadata, searchLimit).map(similarDocuments -> {
            // 2. 构建Prompt
            PromptTemplate template = new PromptTemplate(promptTemplate);
            Map<String, Object> model = new HashMap<>();
            model.put("question", question);
            model.put("context", formatDocuments(similarDocuments));
            return template.create(model);
        });
    }
    
    private String formatDocuments(List<KnowledgeDto> knowledgeDtoList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < knowledgeDtoList.size(); i++) {
            KnowledgeDto doc = knowledgeDtoList.get(i);
            sb.append("片段 ").append(i + 1).append(":\n");
            sb.append(doc.getContent()).append("\n\n");
        }
        return sb.toString();
    }
}