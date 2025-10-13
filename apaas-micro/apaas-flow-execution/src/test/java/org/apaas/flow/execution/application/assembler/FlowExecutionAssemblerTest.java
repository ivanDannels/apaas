package org.apaas.flow.execution.application.assembler;

import org.apaas.flow.execution.domain.model.FlowInstance;
import org.apaas.flow.execution.domain.model.WorkflowTask;
import org.apaas.flow.execution.application.dto.StartProcessDTO;
import org.apaas.flow.execution.application.dto.CompleteTaskDTO;
import org.apaas.flow.execution.application.dto.TransferTaskDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class FlowExecutionAssemblerTest {
    
    private final FlowExecutionAssembler assembler = Mappers.getMapper(FlowExecutionAssembler.class);
    
    @Test
    void testToFlowInstance() {
        // 创建StartProcessDTO对象
        StartProcessDTO startProcessDTO = new StartProcessDTO();
        startProcessDTO.setProcessId(1L);
        startProcessDTO.setBusinessKey("BUSINESS_001");
        startProcessDTO.setStarter(1001L);
        startProcessDTO.setVariables("variables");
        
        // 转换为FlowInstance实体
        FlowInstance flowInstance = assembler.toFlowInstance(startProcessDTO);
        
        // 验证转换结果
        assertNotNull(flowInstance);
        assertEquals(startProcessDTO.getProcessId(), flowInstance.getDefinitionId());
        assertEquals(startProcessDTO.getBusinessKey(), flowInstance.getBusinessKey());
        assertEquals(startProcessDTO.getStarter(), flowInstance.getStartUserId());
        assertEquals(startProcessDTO.getVariables(), flowInstance.getVariables());
    }
    
    @Test
    void testToWorkflowTaskFromCompleteTaskDTO() {
        // 创建CompleteTaskDTO对象
        CompleteTaskDTO completeTaskDTO = new CompleteTaskDTO();
        completeTaskDTO.setTaskId(1L);
        completeTaskDTO.setAssignee(1001L);
        completeTaskDTO.setVariables("variables");
        completeTaskDTO.setComment("完成任务");
        
        // 转换为WorkflowTask实体
        WorkflowTask workflowTask = assembler.toWorkflowTask(completeTaskDTO);
        
        // 验证转换结果
        assertNotNull(workflowTask);
        assertEquals(completeTaskDTO.getTaskId(), workflowTask.getId());
        assertEquals(completeTaskDTO.getAssignee(), workflowTask.getAssignee());
        assertEquals(completeTaskDTO.getVariables(), workflowTask.getVariables());
        assertEquals(completeTaskDTO.getComment(), workflowTask.getComment());
    }
    
    @Test
    void testToWorkflowTaskFromTransferTaskDTO() {
        // 创建TransferTaskDTO对象
        TransferTaskDTO transferTaskDTO = new TransferTaskDTO();
        transferTaskDTO.setTaskId(1L);
        transferTaskDTO.setAssignee(1001L);
        transferTaskDTO.setReason("转办原因");
        
        // 转换为WorkflowTask实体
        WorkflowTask workflowTask = assembler.toWorkflowTask(transferTaskDTO);
        
        // 验证转换结果
        assertNotNull(workflowTask);
        assertEquals(transferTaskDTO.getTaskId(), workflowTask.getId());
        assertEquals(transferTaskDTO.getAssignee(), workflowTask.getAssignee());
        assertEquals(transferTaskDTO.getReason(), workflowTask.getComment());
    }
}