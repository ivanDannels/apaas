package org.apaas.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

/**
 * 流程定义实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_definition")
@Schema(description = "流程定义实体")
public class FlowDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String flowName;

    /**
     * 流程编码
     */
    @Schema(description = "流程编码")
    private String flowCode;

    /**
     * 流程版本
     */
    @Schema(description = "流程版本")
    private String version;

    /**
     * 流程描述
     */
    @Schema(description = "流程描述")
    private String description;

    /**
     * 流程定义JSON
     */
    @Schema(description = "流程定义JSON")
    private String flowJson;

    /**
     * 流程图XML
     */
    @Schema(description = "流程图XML")
    private String flowXml;

    /**
     * 流程状态（0：草稿，1：发布，2：停用）
     */
    @Schema(description = "流程状态")
    private Integer status;

    /**
     * 是否为主版本
     */
    @Schema(description = "是否为主版本")
    private Boolean isMain;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    private String category;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}