package org.apaas.flow.engine.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 启动流程实例DTO
 */
@Data
public class StartInstanceDTO {
    /**
     * 流程定义ID
     */
    @NotNull(message = "流程定义ID不能为空")
    private Long definitionId;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 业务表单数据
     */
    private String businessData;

    /**
     * 发起人ID
     */
    private Long startUserId;
}