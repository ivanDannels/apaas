package org.apaas.flow.engine.application.assembler;

import org.apaas.flow.engine.domain.model.FlowInstance;
import org.apaas.flow.engine.application.dto.FlowInstanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlowInstanceAssembler {
    
    FlowInstanceAssembler INSTANCE = Mappers.getMapper(FlowInstanceAssembler.class);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "definitionId", target = "definitionId"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "variables", target = "variables"),
        @Mapping(source = "startTime", target = "startTime"),
        @Mapping(source = "endTime", target = "endTime"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FlowInstanceDTO toDTO(FlowInstance flowInstance);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "definitionId", target = "definitionId"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "variables", target = "variables"),
        @Mapping(source = "startTime", target = "startTime"),
        @Mapping(source = "endTime", target = "endTime"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    FlowInstance toEntity(FlowInstanceDTO flowInstanceDTO);
    
    List<FlowInstanceDTO> toDTOList(List<FlowInstance> flowInstances);
    
    List<FlowInstance> toEntityList(List<FlowInstanceDTO> flowInstanceDTOs);
}