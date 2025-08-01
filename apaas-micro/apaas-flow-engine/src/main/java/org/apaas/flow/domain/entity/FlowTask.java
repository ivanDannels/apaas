package org.apaas.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 流程任务实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_task")
@Schema(description = "流程任务实体")
public class FlowTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private Long instanceId;

    /**
     * 流程定义ID
     */
    @Schema(description = "流程定义ID")
    private Long flowDefinitionId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String taskName;

    /**
     * 任务编码
     */
    @Schema(description = "任务编码")
    private String taskCode;

    /**
     * 任务类型（0：用户任务，1：系统任务，2：脚本任务）
     */
    @Schema(description = "任务类型")
    private Integer taskType;

    /**
     * 处理人ID
     */
    @Schema(description = "处理人ID")
    private Long assigneeId;

    /**
     * 处理人姓名
     */
    @Schema(description = "处理人姓名")
    private String assigneeName;

    /**
     * 候选人
     */
    @Schema(description = "候选人")
    private String candidateUsers;

    /**
     * 候选组
     */
    @Schema(description = "候选组")
    private String candidateGroups;

    /**
     * 任务状态（0：待处理，1：处理中，2：已完成，3：已拒绝，4：已转办，5：已委派）
     */
    @Schema(description = "任务状态")
    private Integer status;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 到期时间
     */
    @Schema(description = "到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueTime;

    /**
     * 处理意见
     */
    @Schema(description = "处理意见")
    private String comment;

    /**
     * 表单数据
     */
    @Schema(description = "表单数据")
    private String formData;

    /**
     * 任务变量
     */
    @Schema(description = "任务变量")
    private String variables;

    /**
     * 优先级
     */
    @Schema(description = "优先级")
    private Integer priority;

    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID")
    private Long parentTaskId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}