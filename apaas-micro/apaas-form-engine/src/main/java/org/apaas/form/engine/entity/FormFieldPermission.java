package org.apaas.form.engine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单字段权限实体
 */
@Data
@TableName("form_field_permission")
public class FormFieldPermission extends BaseEntity {
    /**
     * 表单定义ID
     */
    private Long formId;

    /**
     * 表单字段ID
     */
    private Long fieldId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限类型（0-查看，1-编辑）
     */
    private Integer permissionType;

    /**
     * 权限状态（0-允许，1-禁止）
     */
    private Integer permissionStatus;

    /**
     * 条件表达式JSON
     */
    private String conditionJson;
}