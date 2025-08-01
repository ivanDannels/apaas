package org.apaas.form.engine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单验证规则实体
 */
@Data
@TableName("form_validation_rule")
public class FormValidationRule extends BaseEntity {
    /**
     * 表单字段ID
     */
    private Long fieldId;

    /**
     * 规则类型（0-必填，1-长度，2-正则，3-自定义脚本，4-跨字段校验）
     */
    private Integer type;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则值
     */
    private String value;

    /**
     * 错误提示信息
     */
    private String errorMessage;

    /**
     * 规则配置JSON
     */
    private String configJson;

    /**
     * 规则状态（0-启用，1-禁用）
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;
}