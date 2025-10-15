package org.apaas.job.application.assembler;

import org.apaas.application.assembler.BaseAssembler;
import org.apaas.job.application.dto.JobDTO;
import org.apaas.job.domain.model.JobEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
// 修改接口声明，继承BaseAssembler并指定泛型参数
public interface JobAssembler extends BaseAssembler<JobEntity, JobDTO, Long> {
    
    JobAssembler INSTANCE = Mappers.getMapper(JobAssembler.class);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "cronExpression", target = "cronExpression"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "parameters", target = "parameters"),
        @Mapping(source = "lastTriggerTime", target = "lastTriggerTime"),
        @Mapping(source = "createdBy", target = "creator"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updatedBy", target = "updater"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    // 这些方法已经在BaseAssembler中定义了，不需要重复声明
    // JobDTO toDTO(JobEntity jobEntity);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "cronExpression", target = "cronExpression"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "parameters", target = "parameters"),
        @Mapping(source = "lastTriggerTime", target = "lastTriggerTime"),
        @Mapping(source = "creator", target = "createdBy"),
        @Mapping(source = "createdTime", target = "createdTime"),
        @Mapping(source = "updater", target = "updatedBy"),
        @Mapping(source = "updatedTime", target = "updatedTime")
    })
    // JobEntity toEntity(JobDTO jobDTO);
    
    // 这些方法已经在BaseAssembler中定义了，不需要重复声明
    // List<JobDTO> toDTOList(List<JobEntity> jobEntities);
    // List<JobEntity> toEntityList(List<JobDTO> jobDTOs);
}