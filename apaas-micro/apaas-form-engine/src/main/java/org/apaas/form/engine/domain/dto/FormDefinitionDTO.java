package org.apaas.form.engine.domain.dto;

import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 表单定义查询DTO
 */
@Data
public class FormDefinitionDTO extends BasePageQuery {
    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单类型
     */
    private Integer type;

    /**
     * 表单状态
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;
}