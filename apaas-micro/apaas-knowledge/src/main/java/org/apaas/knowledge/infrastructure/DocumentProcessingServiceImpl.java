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
import org.apaas.knowledge.domain.DocumentProcessingService;
import org.apaas.knowledge.domain.VectorStoreService;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ivan
 */
@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {
    
    private final VectorStoreService vectorStoreService;
    
    @Value("${app.upload.dir:/tmp/uploads}")
    private String uploadDir;
    
    @Override
    public List<KnowledgeDto> parseDocument(MultipartFile file) throws IOException {
        // 创建临时文件
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile);
        
        // 根据文件类型选择合适的解析器
        String contentType = file.getContentType();
        DocumentReader documentReader;
        
        if (contentType != null && contentType.contains("pdf")) {
            documentReader = new TikaDocumentReader(tempFile.toUri().toString());
        } else if (contentType != null && contentType.contains("word")) {
            documentReader = new TikaDocumentReader(tempFile.toUri().toString());
        } else if (file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".md")) {
            documentReader = new MarkdownDocumentReader(tempFile.toUri().toString());
        } else {
            // 默认使用Tika解析器
            documentReader = new TikaDocumentReader(tempFile.toUri().toString());
        }
        
        List<Document> documents = documentReader.get();
        // 清理临时文件
        Files.delete(tempFile);
        
        // 将Spring AI的Document转换为Knowledge对象
        return documents.stream().map(this::convertToKnowledge).collect(Collectors.toList());
    }
    
    @Override
    public List<KnowledgeDto> parseDocument(Resource resource, String fileType) throws IOException {
        DocumentReader documentReader;
        
        if ("pdf".equalsIgnoreCase(fileType) || "docx".equalsIgnoreCase(fileType)) {
            documentReader = new TikaDocumentReader(resource);
        } else if ("md".equalsIgnoreCase(fileType)) {
            documentReader = new MarkdownDocumentReader(resource.getURL().toString());
        } else {
            // 默认使用Tika解析器
            documentReader = new TikaDocumentReader(resource);
        }
        
        List<Document> documents = documentReader.get();
        
        // 将Spring AI的Document转换为Knowledge对象
        return documents.stream().map(this::convertToKnowledge).toList();
    }
    
    @Override
    public void processAndStoreDocuments(List<KnowledgeDto> knowledgeDtoList) {
        vectorStoreService.storeDocuments(KnowledgeAssembler.INSTANCE.convertDtoList(knowledgeDtoList));
    }
    
    @Override
    public void processUploadedFile(MultipartFile file) throws IOException {
        List<KnowledgeDto> documents = parseDocument(file);
        processAndStoreDocuments(documents);
    }
    
    private KnowledgeDto convertToKnowledge(Document document) {
        return KnowledgeAssembler.INSTANCE.convertToKnowledge(document);
    }
}