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

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apaas.knowledge.domain.model.Knowledge;
import org.apaas.knowledge.domain.service.VectorStoreRepository;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 向量存储库的Ollama实现
 * @author ivan
 */
@Repository
@RequiredArgsConstructor
public class OllamaVectorStoreRepository implements VectorStoreRepository {

    
    private boolean vectorStoreCreated = false;
    
    @Override
    public void createVectorStore(List<Knowledge> knowledgeList) {
        // 将自定义Document转换为Spring AI的Document
        List<Document> aiDocuments = knowledgeList.stream().map(doc -> new Document(doc.getContent(), Map.of("fileName", doc.getFileName()))).collect(Collectors.toList());
        
        // 清空现有向量存储并添加新文档
//        vectorStore.delete(aiDocuments.stream().map(Document::getId).toList());
//        vectorStore.add(aiDocuments);
        
        vectorStoreCreated = true;
    }
    
    @Override
    public List<Knowledge> searchSimilar(String query, int k) {
        // 搜索相似文档
//        List<Document> similarDocs = vectorStore.similaritySearch(query);
        
        // 将Spring AI的Document转换回自定义Document
//        return similarDocs.stream().map(aiDoc -> {
//            Knowledge doc = new Knowledge();
//            doc.setContent(aiDoc.getFormattedContent());
//            doc.setFileName((String) aiDoc.getMetadata().get("fileName"));
//            return doc;
//        }).toList();
        return List.of();
    }
    
    @Override
    public boolean hasVectorStore() {
        return vectorStoreCreated;
    }
    
    @Override
    public void clearVectorStore() {
//        vectorStore.delete(List.of());
        vectorStoreCreated = false;
    }
}