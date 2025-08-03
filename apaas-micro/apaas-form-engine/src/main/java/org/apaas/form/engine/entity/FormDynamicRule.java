package org.apaas.form.engine.entity;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单动态规则实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_dynamic_rule")
public class FormDynamicRule extends BaseEntity {
    /**
     * 表单定义ID
     */
    private Long formId;

    /**
     * 目标字段ID
     */
    private Long targetFieldId;

    /**
     * 规则类型（0-显示/隐藏规则，1-必填规则，2-计算字段，3-联动加载）
     */
    private Integer type;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 触发条件JSON
     */
    private String conditionJson;

    /**
     * 执行动作JSON
     */
    private String actionJson;

    /**
     * 规则状态（0-启用，1-禁用）
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;
}