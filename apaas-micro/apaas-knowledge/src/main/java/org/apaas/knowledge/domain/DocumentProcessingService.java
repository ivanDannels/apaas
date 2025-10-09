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
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ivan
 */
public interface DocumentProcessingService {
    
    /**
     * 解析多格式文档并提取文本内容
     * @param file 上传的文件
     * @return 文档对象列表
     * @throws IOException 文件读取异常
     */
    List<KnowledgeDto> parseDocument(MultipartFile file) throws IOException;
    
    /**
     * 解析资源文件并提取文本内容
     * @param resource 资源文件
     * @param fileType 文件类型 (pdf, docx, md等)
     * @return 文档对象列表
     * @throws IOException 文件读取异常
     */
    List<KnowledgeDto> parseDocument(Resource resource, String fileType) throws IOException;
    
    /**
     * 向量化并存储文档
     * @param documents 文档列表
     */
    void processAndStoreDocuments(List<KnowledgeDto> documents);
    
    /**
     * 处理上传的文件：解析、向量化并存储
     * @param file 上传的文件
     * @throws IOException 文件处理异常
     */
    void processUploadedFile(MultipartFile file) throws IOException;
}