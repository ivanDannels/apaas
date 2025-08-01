package org.apaas.flow.execution.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("activity_instance")
public class ActivityInstance extends BaseEntity {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 流程实例ID
     */
    @TableField("flow_instance_id")
    private Long flowInstanceId;
    
    /**
     * 节点ID
     */
    @TableField("node_id")
    private String nodeId;
    
    /**
     * 节点名称
     */
    @TableField("node_name")
    private String nodeName;
    
    /**
     * 节点类型
     */
    @TableField("node_type")
    private String nodeType;
    
    /**
     * 活动实例状态
     */
    @TableField("status")
    private String status;
    
    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    @TableField("duration")
    private Long duration;
    
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