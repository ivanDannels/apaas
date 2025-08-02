package org.apaas.form.engine.entity;

import lombok.Data;
import org.apaas.core.domain.BaseEntity;
import java.time.LocalDateTime;

/**
 * 表单定义实体
 */
@Data
public class FormDefinition extends BaseEntity {
    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单编码
     */
    private String code;

    /**
     * 表单类型（0-普通表单，1-流程表单，2-统计表单）
     */
    private Integer type;

    /**
     * 表单状态（0-草稿，1-已发布，2-已停用）
     */
    private Integer status;

    /**
     * 表单配置JSON
     */
    private String configJson;

    /**
     * 表单项JSON
     */
    private String itemsJson;

    /**
     * 数据源ID
     */
    private Long dataSourceId;

    /**
     * 关联流程ID
     */
    private Long flowId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 是否为默认版本
     */
    private Boolean isDefault;
}