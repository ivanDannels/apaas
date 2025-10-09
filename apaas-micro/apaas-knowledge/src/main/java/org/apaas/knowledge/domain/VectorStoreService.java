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
package org.apaas.knowledge.domain;

import org.apaas.knowledge.application.dto.KnowledgeDto;
import org.apaas.knowledge.domain.entity.Knowledge;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author ivan
 */
public interface VectorStoreService {
    
    /**
     * 将文档向量化并存储到向量数据库
     * @param document 文档对象
     */
    void storeDocument(Knowledge document);
    
    /**
     * 批量存储文档
     * @param documents 文档列表
     */
    void storeDocuments(List<Knowledge> documents);
    
    /**
     * 根据查询文本搜索相似文档
     * @param query 查询文本
     * @param limit 返回结果数量限制
     * @return 相似文档列表
     */
    Mono<List<KnowledgeDto>> searchSimilarDocuments(String query, int limit);
    
    /**
     * 根据查询文本和元数据过滤条件搜索相似文档
     * @param query 查询文本
     * @param filterMetadata 过滤条件
     * @param limit 返回结果数量限制
     * @return 相似文档列表
     */
    Mono<List<KnowledgeDto>> searchSimilarDocuments(String query, Map<String, Object> filterMetadata, int limit);
}