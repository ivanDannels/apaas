package org.apaas.flow.execution.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_task")
public class WorkflowTask extends BaseEntity {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 活动实例ID
     */
    @TableField("activity_instance_id")
    private Long activityInstanceId;
    
    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;
    
    /**
     * 任务类型
     */
    @TableField("task_type")
    private String taskType;
    
    /**
     * 任务处理人
     */
    @TableField("assignee")
    private Long assignee;
    
    /**
     * 候选处理人
     */
    @TableField("candidate_users")
    private String candidateUsers;
    
    /**
     * 候选处理组
     */
    @TableField("candidate_groups")
    private String candidateGroups;
    
    /**
     * 任务状态
     */
    @TableField("status")
    private String status;
    
    /**
     * 优先级
     */
    @TableField("priority")
    private Integer priority;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    
    /**
     * 到期时间
     */
    @TableField("due_time")
    private LocalDateTime dueTime;
    
    /**
     * 开始处理时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    
    /**
     * 完成时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    @TableField("duration")
    private Long duration;
    
    /**
     * 表单数据ID
     */
    @TableField("form_data_id")
    private Long formDataId;
    
    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;
    
    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    @TableField("updater")
    private String updater;
    
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;
}