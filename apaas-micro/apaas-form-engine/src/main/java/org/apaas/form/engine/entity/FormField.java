package org.apaas.form.engine.entity;

import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单字段实体
 */
@Data
public class FormField extends BaseEntity {
    /**
     * 表单定义ID
     */
    private Long formId;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段编码
     */
    private String code;

    /**
     * 字段类型（0-文本，1-数字，2-日期，3-单选，4-多选，5-下拉，6-附件，7-子表单，8-关联查询选择）
     */
    private Integer type;

    /**
     * 字段配置JSON
     */
    private String configJson;

    /**
     * 校验规则JSON
     */
    private String validationJson;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否必填（0-否，1-是）
     */
    private Integer required;

    /**
     * 是否只读（0-否，1-是）
     */
    private Integer readonly;

    /**
     * 是否隐藏（0-否，1-是）
     */
    private Integer hidden;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 字段说明
     */
    private String description;
}