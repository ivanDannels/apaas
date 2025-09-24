package org.apaas.flow.execution.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("activity_instance")
public class ActivityInstance extends BaseEntity {

    /**
     * 流程实例ID
     */
    private Long flowInstanceId;
    
    /**
     * 节点ID
     */
    private String nodeId;
    
    /**
     * 节点名称
     */
    private String nodeName;
    
    /**
     * 节点类型
     */
    private String nodeType;
    
    /**
     * 活动实例状态
     */
    private String status;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    private Long duration;
}