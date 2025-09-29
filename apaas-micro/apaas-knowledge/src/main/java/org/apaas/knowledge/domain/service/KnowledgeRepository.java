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
import java.util.Optional;

/**
 * 文档存储库接口，定义对文档的基本操作
 * @author ivan
 */
public interface KnowledgeRepository {
    
    /**
     * 保存文档
     */
    Knowledge save(Knowledge knowledge);
    
    /**
     * 根据文件名获取文档
     */
    Optional<Knowledge> findByFileName(String fileName);
    
    /**
     * 获取所有文档
     */
    List<Knowledge> findAll();
    
    /**
     * 删除文档
     */
    void deleteByFileName(String fileName);
    
    /**
     * 批量删除文档
     */
    void deleteAllByFileNames(List<String> fileNames);
    
    /**
     * 检查文件是否存在
     */
    boolean existsByFileName(String fileName);
}