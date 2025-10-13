package org.apaas.form.engine.application.assembler;

import org.apaas.form.engine.domain.model.FormDefinition;
import org.apaas.form.engine.application.dto.FormDefinitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormDefinitionAssembler {
    
    FormDefinitionAssembler INSTANCE = Mappers.getMapper(FormDefinitionAssembler.class);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "type", target = "type"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "configJson", target = "configJson"),
        @Mapping(source = "itemsJson", target = "itemsJson"),
        @Mapping(source = "dataSourceId", target = "dataSourceId"),
        @Mapping(source = "flowId", target = "flowId"),
        @Mapping(source = "version", target = "version"),
        @Mapping(source = "isDefault", target = "isDefault"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FormDefinitionDTO toDTO(FormDefinition formDefinition);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "type", target = "type"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "configJson", target = "configJson"),
        @Mapping(source = "itemsJson", target = "itemsJson"),
        @Mapping(source = "dataSourceId", target = "dataSourceId"),
        @Mapping(source = "flowId", target = "flowId"),
        @Mapping(source = "version", target = "version"),
        @Mapping(source = "isDefault", target = "isDefault"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FormDefinition toEntity(FormDefinitionDTO formDefinitionDTO);
    
    List<FormDefinitionDTO> toDTOList(List<FormDefinition> formDefinitions);
    
    List<FormDefinition> toEntityList(List<FormDefinitionDTO> formDefinitionDTOs);
}