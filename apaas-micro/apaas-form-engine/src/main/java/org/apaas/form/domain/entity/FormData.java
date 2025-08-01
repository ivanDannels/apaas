package org.apaas.form.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

/**
 * 表单数据实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("form_data")
@Schema(description = "表单数据实体")
public class FormData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表单定义ID
     */
    @Schema(description = "表单定义ID")
    private Long formDefinitionId;

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
     * 业务键
     */
    @Schema(description = "业务键")
    private String businessKey;

    /**
     * 表单数据JSON
     */
    @Schema(description = "表单数据JSON")
    private String dataJson;

    /**
     * 数据状态（0：草稿，1：已提交，2：已审核）
     */
    @Schema(description = "数据状态")
    private Integer status;

    /**
     * 提交人ID
     */
    @Schema(description = "提交人ID")
    private Long submitUserId;

    /**
     * 提交人姓名
     */
    @Schema(description = "提交人姓名")
    private String submitUserName;

    /**
     * 提交部门ID
     */
    @Schema(description = "提交部门ID")
    private Long submitDeptId;

    /**
     * 提交部门名称
     */
    @Schema(description = "提交部门名称")
    private String submitDeptName;

    /**
     * 关联流程实例ID
     */
    @Schema(description = "关联流程实例ID")
    private Long flowInstanceId;

    /**
     * 数据来源（0：手工录入，1：接口导入，2：流程提交）
     */
    @Schema(description = "数据来源")
    private Integer dataSource;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}