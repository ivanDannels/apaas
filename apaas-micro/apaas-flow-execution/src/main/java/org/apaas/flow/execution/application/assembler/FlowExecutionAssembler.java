package org.apaas.flow.execution.application.assembler;

import org.apaas.flow.execution.domain.model.FlowInstance;
import org.apaas.flow.execution.domain.model.WorkflowTask;
import org.apaas.flow.execution.application.dto.StartProcessDTO;
import org.apaas.flow.execution.application.dto.CompleteTaskDTO;
import org.apaas.flow.execution.application.dto.TransferTaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlowExecutionAssembler {
    
    FlowExecutionAssembler INSTANCE = Mappers.getMapper(FlowExecutionAssembler.class);
    
    @Mappings({
        @Mapping(source = "processDefinitionId", target = "definitionId"),
        @Mapping(source = "variables", target = "variables"),
        @Mapping(source = "starter", target = "createdBy")
    })
    FlowInstance toFlowInstance(StartProcessDTO startProcessDTO);
    
    @Mappings({
        @Mapping(source = "taskId", target = "id"),
        @Mapping(source = "assignee", target = "assignee"),
        @Mapping(source = "variables", target = "variables"),
        @Mapping(source = "comment", target = "comment")
    })
    WorkflowTask toWorkflowTask(CompleteTaskDTO completeTaskDTO);
    
    @Mappings({
        @Mapping(source = "taskId", target = "id"),
        @Mapping(source = "assignee", target = "assignee"),
        @Mapping(source = "reason", target = "comment")
    })
    WorkflowTask toWorkflowTask(TransferTaskDTO transferTaskDTO);
}