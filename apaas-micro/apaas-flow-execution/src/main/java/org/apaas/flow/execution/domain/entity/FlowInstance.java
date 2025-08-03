package org.apaas.flow.execution.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("flow_instance")
public class FlowInstance extends BaseEntity {
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 流程定义ID
     */
    private Long processId;
    
    /**
     * 业务主键
     */
    private String businessKey;
    
    /**
     * 流程实例状态
     */
    private String status;
    
    /**
     * 启动时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 启动人
     */
    private Long starter;

}