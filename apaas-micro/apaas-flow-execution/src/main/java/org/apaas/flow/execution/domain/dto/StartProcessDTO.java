package org.apaas.flow.execution.domain.dto;

import lombok.Data;

@Data
public class StartProcessDTO {
    
    /** 流程定义ID */
    private Long processId;
    
    /** 业务主键 */
    private String businessKey;
    
    /** 启动人 */
    private Long starter;
}