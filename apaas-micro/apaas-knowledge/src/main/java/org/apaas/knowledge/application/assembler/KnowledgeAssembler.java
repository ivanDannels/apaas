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
package org.apaas.knowledge.application.assembler;

import org.apaas.knowledge.application.dto.KnowledgeDto;
import org.apaas.knowledge.domain.entity.Knowledge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * @author ivan
 */
@Mapper
public interface KnowledgeAssembler {
    
    KnowledgeAssembler INSTANCE = Mappers.getMapper(KnowledgeAssembler.class);
    
    Knowledge convertDto(KnowledgeDto knowledgeDto);
    
    KnowledgeDto convertEntity(Knowledge entity);
    
    List<KnowledgeDto> convertEntityList(List<Knowledge> list);
    
    List<Knowledge> convertDtoList(List<KnowledgeDto> list);
    
    default KnowledgeDto convertToKnowledge(Document document) {
        KnowledgeDto knowledgeDto = new KnowledgeDto();
        knowledgeDto.setContent(document.getText());
        knowledgeDto.setFilePath(document.getMetadata().get("filePath").toString());
        knowledgeDto.setFileType(document.getMetadata().get("fileType").toString());
        knowledgeDto.setTitle(document.getMetadata().get("title").toString());
        return knowledgeDto;
    }
    
}
