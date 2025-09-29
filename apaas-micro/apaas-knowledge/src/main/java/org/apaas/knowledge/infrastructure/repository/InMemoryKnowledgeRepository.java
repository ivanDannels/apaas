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

import org.apaas.knowledge.domain.model.Knowledge;
import org.apaas.knowledge.domain.service.KnowledgeRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档存储库的内存实现
 * @author ivan
 */
@Repository
public class InMemoryKnowledgeRepository implements KnowledgeRepository {
    
    // 使用ConcurrentHashMap存储文档，确保线程安全
    private final Map<String, Knowledge> documentStore = new ConcurrentHashMap<>();
    
    @Override
    public Knowledge save(Knowledge knowledge) {
        if (knowledge.getId() == null) {
            knowledge.setId(UUID.randomUUID().toString());
        }
        documentStore.put(knowledge.getFileName(), knowledge);
        return knowledge;
    }
    
    @Override
    public Optional<Knowledge> findByFileName(String fileName) {
        return Optional.ofNullable(documentStore.get(fileName));
    }
    
    @Override
    public List<Knowledge> findAll() {
        List<Knowledge> knowledges = new ArrayList<>(documentStore.values());
        // 按修改时间排序，最新的在前
        knowledges.sort((d1, d2) -> Long.compare(d2.getLastModifiedTime(), d1.getLastModifiedTime()));
        return knowledges;
    }
    
    @Override
    public void deleteByFileName(String fileName) {
        documentStore.remove(fileName);
    }
    
    @Override
    public void deleteAllByFileNames(List<String> fileNames) {
        for (String fileName : fileNames) {
            documentStore.remove(fileName);
        }
    }
    
    @Override
    public boolean existsByFileName(String fileName) {
        return documentStore.containsKey(fileName);
    }
}