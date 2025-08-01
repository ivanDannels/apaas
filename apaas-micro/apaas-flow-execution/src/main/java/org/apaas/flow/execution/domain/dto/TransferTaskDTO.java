package org.apaas.flow.execution.domain.dto;

import lombok.Data;

@Data
public class TransferTaskDTO {
    
    /** 任务ID */
    private Long taskId;
    
    /** 原处理人 */
    private Long fromUserId;
    
    /** 新处理人 */
    private Long toUserId;
}