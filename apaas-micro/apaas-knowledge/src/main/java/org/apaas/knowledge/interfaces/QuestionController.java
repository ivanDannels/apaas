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
package org.apaas.knowledge.interfaces;

import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.application.KnowledgeService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author ivan
 */
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    
    private final KnowledgeService knowledgeService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> askQuestion(@RequestBody Map<String, Object> request) {
        String question = (String) request.get("question");
        Map<String, Object> filter = (Map<String, Object>) request.get("filter");
        
        if (filter != null) {
            return knowledgeService.askQuestion(question, filter).map(ResponseEntity::ok).onErrorReturn(ResponseEntity.internalServerError().body("回答问题时发生错误"));
        } else {
            return knowledgeService.askQuestion(question).map(ResponseEntity::ok).onErrorReturn(ResponseEntity.internalServerError().body("回答问题时发生错误"));
        }
    }
    
    @PostMapping(value = "/stream", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> streamAskQuestion(@RequestBody Map<String, Object> request) {
        String question = (String) request.get("question");
        Map<String, Object> filter = (Map<String, Object>) request.get("filter");
        
        if (filter != null) {
            return knowledgeService.streamAskQuestion(question, filter);
        } else {
            return knowledgeService.streamAskQuestion(question);
        }
    }
    
    @PostMapping("/regenerate")
    public Mono<ResponseEntity<String>> regenerateAnswer(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String previousAnswer = request.get("previousAnswer");
        
        return knowledgeService.regenerateAnswer(question, previousAnswer).map(ResponseEntity::ok).onErrorReturn(ResponseEntity.internalServerError().body("重新生成回答时发生错误"));
    }
}