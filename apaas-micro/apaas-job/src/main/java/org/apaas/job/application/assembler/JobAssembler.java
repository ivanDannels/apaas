package org.apaas.job.application.assembler;

import org.apaas.job.application.dto.JobDTO;
import org.apaas.job.domain.model.JobEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JobAssembler {
    
    JobAssembler INSTANCE = Mappers.getMapper(JobAssembler.class);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "cronExpression", target = "cronExpression"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "parameters", target = "parameters"),
        @Mapping(source = "lastTriggerTime", target = "lastTriggerTime"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    JobDTO toDTO(JobEntity jobEntity);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "cronExpression", target = "cronExpression"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "parameters", target = "parameters"),
        @Mapping(source = "lastTriggerTime", target = "lastTriggerTime"),
        @Mapping(source = "createdBy", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    JobEntity toEntity(JobDTO jobDTO);
    
    List<JobDTO> toDTOList(List<JobEntity> jobEntities);
    
    List<JobEntity> toEntityList(List<JobDTO> jobDTOs);
}