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
package org.apaas.design.platform.application.assembler;

import org.apaas.design.platform.application.dto.APIEntityDTO;
import org.apaas.design.platform.domain.model.APIEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API实体装配器
 * 负责DTO与领域对象之间的转换
 *
 * @author ivan
 */
@Mapper
public interface APIEntityAssembler {
    
    APIEntityAssembler INSTANCE = Mappers.getMapper(APIEntityAssembler.class);
    
    /**
     * 将API实体DTO转换为API实体
     */
    APIEntity convertDtoToEntity(APIEntityDTO apiEntityDTO);
    
    /**
     * 将API实体转换为API实体DTO
     */
    APIEntityDTO convertEntityToDto(APIEntity apiEntity);
    
    /**
     * 将API实体列表转换为API实体DTO列表
     */
    List<APIEntityDTO> convertEntityListToDtoList(List<APIEntity> apiEntityList);
}