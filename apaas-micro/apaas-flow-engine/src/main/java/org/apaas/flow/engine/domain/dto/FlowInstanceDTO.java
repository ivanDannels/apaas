package org.apaas.flow.engine.domain.dto;

import lombok.Data;

/**
 * 流程实例查询DTO
 */
@Data
public class FlowInstanceDTO {
    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程实例状态
     */
    private Integer status;

    /**
     * 发起人ID
     */
    private Long startUserId;

    /**
     * 租户ID
     */
    private Long tenantId;
}