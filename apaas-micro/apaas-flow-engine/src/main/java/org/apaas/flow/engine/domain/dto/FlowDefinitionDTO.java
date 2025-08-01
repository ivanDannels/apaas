package org.apaas.flow.engine.domain.dto;

import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 流程定义查询DTO
 */
@Data
public class FlowDefinitionDTO extends BasePageQuery {
    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程状态
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;
}