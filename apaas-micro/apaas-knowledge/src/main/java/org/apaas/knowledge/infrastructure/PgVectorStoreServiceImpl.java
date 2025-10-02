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
import org.apaas.knowledge.application.assembler.KnowledgeAssembler;
import org.apaas.knowledge.application.dto.KnowledgeDto;
import org.apaas.knowledge.domain.VectorStoreService;
import org.apaas.knowledge.domain.entity.Knowledge;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ivan
 */
@Service
@RequiredArgsConstructor
public class PgVectorStoreServiceImpl implements VectorStoreService {
    
    private final VectorStore vectorStore;
    
    @Override
    public void storeDocument(Knowledge knowledge) {
        Document document = convertToSpringDocument(knowledge);
        vectorStore.add(List.of(document));
    }
    
    @Override
    public void storeDocuments(List<Knowledge> knowledgeList) {
        List<Document> documents = knowledgeList.stream().map(this::convertToSpringDocument).collect(Collectors.toList());
        vectorStore.add(documents);
    }
    
    @Override
    public Mono<List<KnowledgeDto>> searchSimilarDocuments(String query, int limit) {
        return Mono.fromCallable(() -> {
            SearchRequest request = SearchRequest.builder().query(query).topK(limit).build();
            List<Document> documents = vectorStore.similaritySearch(request);
            return documents.stream().map(KnowledgeAssembler.INSTANCE::convertToKnowledge).collect(Collectors.toList());
        });
    }
    
    @Override
    public Mono<List<KnowledgeDto>> searchSimilarDocuments(String query, Map<String, Object> filterMetadata, int limit) {
        return Mono.fromCallable(() -> {
            SearchRequest.Builder builder = SearchRequest.builder().query(query).topK(limit);
            
            if (filterMetadata != null && !filterMetadata.isEmpty()) {
                // 将Map转换为过滤表达式字符串
                StringBuilder filterExpr = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, Object> entry : filterMetadata.entrySet()) {
                    if (!first) {
                        filterExpr.append(" AND ");
                    }
                    filterExpr.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                    first = false;
                }
                builder.filterExpression(filterExpr.toString());
            }
            
            SearchRequest request = builder.build();
            List<Document> documents = vectorStore.similaritySearch(request);
            return documents.stream().map(KnowledgeAssembler.INSTANCE::convertToKnowledge).collect(Collectors.toList());
        });
    }
    
    private Document convertToSpringDocument(Knowledge knowledge) {
        Map<String, Object> metadata = Map.of("filePath", knowledge.getFilePath(), "fileType", knowledge.getFileType(), "title", knowledge.getTitle());
        return new Document(knowledge.getContent(), metadata);
    }
    
}