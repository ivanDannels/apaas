package org.apaas.flow.execution.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_instance")
public class FlowInstance extends BaseEntity {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
    
    /**
     * 流程定义ID
     */
    @TableField("process_id")
    private Long processId;
    
    /**
     * 业务主键
     */
    @TableField("business_key")
    private String businessKey;
    
    /**
     * 流程实例状态
     */
    @TableField("status")
    private String status;
    
    /**
     * 启动时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    
    /**
     * 启动人
     */
    @TableField("starter")
    private Long starter;
    
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