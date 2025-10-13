package org.apaas.integration.application.assembler;

import org.apaas.integration.domain.model.IntegrationEntity;
import org.apaas.integration.application.dto.IntegrationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IntegrationAssembler {
    
    IntegrationAssembler INSTANCE = Mappers.getMapper(IntegrationAssembler.class);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "type", target = "type"),
        @Mapping(source = "config", target = "config"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    IntegrationDTO toDTO(IntegrationEntity integrationEntity);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "type", target = "type"),
        @Mapping(source = "config", target = "config"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    IntegrationEntity toEntity(IntegrationDTO integrationDTO);
    
    List<IntegrationDTO> toDTOList(List<IntegrationEntity> integrationEntities);
    
    List<IntegrationEntity> toEntityList(List<IntegrationDTO> integrationDTOs);
}