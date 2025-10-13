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
package org.apaas.flow.engine.application.assembler;

import org.apaas.flow.engine.domain.model.FlowDefinition;
import org.apaas.flow.engine.application.dto.FlowDefinitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlowDefinitionAssembler {
    
    FlowDefinitionAssembler INSTANCE = Mappers.getMapper(FlowDefinitionAssembler.class);
    
    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"), @Mapping(source = "description", target = "description"), @Mapping(source = "version", target = "version"), @Mapping(source = "flowJson", target = "flowJson"), @Mapping(source = "status", target = "status"), @Mapping(source = "createdBy", target = "createdBy"), @Mapping(source = "createdTime", target = "createdTime"), @Mapping(source = "updatedBy", target = "updatedBy"), @Mapping(source = "updatedTime", target = "updatedTime")})
    FlowDefinitionDTO toDTO(FlowDefinition flowDefinition);
    
    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"), @Mapping(source = "description", target = "description"), @Mapping(source = "version", target = "version"), @Mapping(source = "flowJson", target = "flowJson"), @Mapping(source = "status", target = "status"), @Mapping(source = "createdBy", target = "createdBy"), @Mapping(source = "createdTime", target = "createdTime"), @Mapping(source = "updatedBy", target = "updatedBy"), @Mapping(source = "updatedTime", target = "updatedTime")})
    FlowDefinition toEntity(FlowDefinitionDTO flowDefinitionDTO);
    
    List<FlowDefinitionDTO> toDTOList(List<FlowDefinition> flowDefinitions);
    
    List<FlowDefinition> toEntityList(List<FlowDefinitionDTO> flowDefinitionDTOs);
}