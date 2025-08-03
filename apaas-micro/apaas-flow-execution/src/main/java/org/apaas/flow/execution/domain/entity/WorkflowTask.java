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
@Table("workflow_task")
public class WorkflowTask extends BaseEntity {
    
    /**
     * 活动实例ID
     */
    private Long activityInstanceId;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 任务类型
     */
    private String taskType;
    
    /**
     * 任务处理人
     */
    private Long assignee;
    
    /**
     * 候选处理人
     */
    private String candidateUsers;
    
    /**
     * 候选处理组
     */
    private String candidateGroups;
    
    /**
     * 任务状态
     */
    private String status;
    
    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 到期时间
     */
    private LocalDateTime dueTime;
    
    /**
     * 开始处理时间
     */
    private LocalDateTime startTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    private Long duration;
    
    /**
     * 表单数据ID
     */
    private Long formDataId;

}