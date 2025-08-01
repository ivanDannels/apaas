package org.apaas.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 流程实例实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_instance")
@Schema(description = "流程实例实体")
public class FlowInstance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    @Schema(description = "流程定义ID")
    private Long flowDefinitionId;

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
     * 实例标题
     */
    @Schema(description = "实例标题")
    private String title;

    /**
     * 业务键
     */
    @Schema(description = "业务键")
    private String businessKey;

    /**
     * 发起人ID
     */
    @Schema(description = "发起人ID")
    private Long startUserId;

    /**
     * 发起人姓名
     */
    @Schema(description = "发起人姓名")
    private String startUserName;

    /**
     * 发起部门ID
     */
    @Schema(description = "发起部门ID")
    private Long startDeptId;

    /**
     * 发起部门名称
     */
    @Schema(description = "发起部门名称")
    private String startDeptName;

    /**
     * 发起时间
     */
    @Schema(description = "发起时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 流程状态（0：运行中，1：已完成，2：已终止，3：已挂起）
     */
    @Schema(description = "流程状态")
    private Integer status;

    /**
     * 当前节点
     */
    @Schema(description = "当前节点")
    private String currentNode;

    /**
     * 当前处理人
     */
    @Schema(description = "当前处理人")
    private String currentAssignee;

    /**
     * 表单数据
     */
    @Schema(description = "表单数据")
    private String formData;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private String variables;

    /**
     * 优先级
     */
    @Schema(description = "优先级")
    private Integer priority;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}