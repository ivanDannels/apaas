package org.apaas.flow.execution.domain.dto;

import lombok.Data;

@Data
public class CompleteTaskDTO {
    
    /** 任务ID */
    private Long taskId;
    
    /** 用户ID */
    private Long userId;
}