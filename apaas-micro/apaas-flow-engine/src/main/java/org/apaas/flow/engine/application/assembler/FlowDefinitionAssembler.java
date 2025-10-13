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
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "version", target = "version"),
        @Mapping(source = "flowJson", target = "flowJson"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FlowDefinitionDTO toDTO(FlowDefinition flowDefinition);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "version", target = "version"),
        @Mapping(source = "flowJson", target = "flowJson"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FlowDefinition toEntity(FlowDefinitionDTO flowDefinitionDTO);
    
    List<FlowDefinitionDTO> toDTOList(List<FlowDefinition> flowDefinitions);
    
    List<FlowDefinition> toEntityList(List<FlowDefinitionDTO> flowDefinitionDTOs);
}