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
package org.apaas.knowledge.infrastructure.repository;

import org.apaas.knowledge.domain.service.LLMService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 语言模型服务的Ollama实现
 * @author ivan
 */
@Service
public class OllamaLLMService implements LLMService {
    
    @Autowired
    private ChatClient chatClient;
    
    @Override
    public String generateText(String prompt) {
        // 创建提示模板
        PromptTemplate promptTemplate = new PromptTemplate("{prompt}");
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("prompt", prompt);
        
        // 生成提示
        Prompt finalPrompt = promptTemplate.create(promptParameters);
        
        // 调用ChatClient生成文本响应
        return chatClient.call(finalPrompt).getResult().getOutput().getContent();
    }
    
    @Override
    public void generateTextStream(String prompt, Consumer<String> callback) {
        // 创建提示模板
        PromptTemplate promptTemplate = new PromptTemplate("{prompt}");
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("prompt", prompt);
        
        // 生成提示
        Prompt finalPrompt = promptTemplate.create(promptParameters);
        
        // 调用ChatClient流式生成文本响应
        chatClient.stream(finalPrompt).forEach(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            if (content != null && !content.isEmpty()) {
                callback.accept(content);
            }
        });
    }
}