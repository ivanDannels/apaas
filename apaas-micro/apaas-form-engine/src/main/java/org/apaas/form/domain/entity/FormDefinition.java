package org.apaas.form.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单定义实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("form_definition")
@Schema(description = "表单定义实体")
public class FormDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 表单编码
     */
    @Schema(description = "表单编码")
    private String formCode;

    /**
     * 表单版本
     */
    @Schema(description = "表单版本")
    private String version;

    /**
     * 表单描述
     */
    @Schema(description = "表单描述")
    private String description;

    /**
     * 表单配置JSON
     */
    @Schema(description = "表单配置JSON")
    private String formJson;

    /**
     * 表单字段配置
     */
    @Schema(description = "表单字段配置")
    private String fieldsConfig;

    /**
     * 表单样式配置
     */
    @Schema(description = "表单样式配置")
    private String styleConfig;

    /**
     * 表单验证规则
     */
    @Schema(description = "表单验证规则")
    private String validationRules;

    /**
     * 表单状态（0：草稿，1：发布，2：停用）
     */
    @Schema(description = "表单状态")
    private Integer status;

    /**
     * 是否为主版本
     */
    @Schema(description = "是否为主版本")
    private Boolean isMain;

    /**
     * 表单分类
     */
    @Schema(description = "表单分类")
    private String category;

    /**
     * 关联数据表
     */
    @Schema(description = "关联数据表")
    private String dataTable;

    /**
     * 表单权限配置
     */
    @Schema(description = "表单权限配置")
    private String permissions;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}