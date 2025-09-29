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
package org.apaas.knowledge.domain.service;

import org.apaas.knowledge.domain.model.Knowledge;

import java.util.List;

/**
 * 向量存储库接口，定义向量数据库相关操作
 * @author ivan
 */
public interface VectorStoreRepository {
    
    /**
     * 创建向量存储
     */
    void createVectorStore(List<Knowledge> knowledges);
    
    /**
     * 搜索相似文档
     */
    List<Knowledge> searchSimilar(String query, int k);
    
    /**
     * 检查向量存储是否存在
     */
    boolean hasVectorStore();
    
    /**
     * 清空向量存储
     */
    void clearVectorStore();
}