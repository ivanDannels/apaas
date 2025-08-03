package org.apaas.flow.engine.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 流程任务实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Table("flow_task")
public class FlowTask extends BaseEntity {

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 流程实例名称
     */
    private String instanceName;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程定义版本
     */
    private Integer definitionVersion;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型（0-开始节点，1-审批节点，2-条件节点，3-并行节点，4-结束节点）
     */
    private Integer nodeType;

    /**
     * 任务状态（0-未开始，1-处理中，2-已完成，3-已终止，4-已退回）
     */
    private Integer status;

    /**
     * 任务优先级（0-普通，1-紧急，2-非常紧急）
     */
    private Integer priority;

    /**
     * 处理人ID
     */
    private Long assigneeId;

    /**
     * 处理人名称
     */
    private String assigneeName;

    /**
     * 候选处理人IDs
     */
    private String candidateIds;

    /**
     * 候选处理人Names
     */
    private String candidateNames;

    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;

    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;

    /**
     * 任务处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 任务完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 处理人ID
     */
    private Long handleUserId;

    /**
     * 处理人名称
     */
    private String handleUserName;

    /**
     * 任务意见
     */
    private String comment;

    /**
     * 任务变量
     */
    private String variables;

    /**
     * 租户ID
     */
    private Long tenantId;

}