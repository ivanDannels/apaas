package org.apaas.flow.engine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apaas.core.domain.BaseEntity;
import java.time.LocalDateTime;

/**
 * 流程定义实体
 */
@Data
@TableName("flow_definition")
public class FlowDefinition extends BaseEntity {
    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程编码
     */
    private String code;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程版本
     */
    private Integer version;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 流程JSON定义
     */
    private String flowJson;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 流程状态（0-草稿，1-已发布，2-已停用）
     */
    private Integer status;

    /**
     * 是否为默认版本
     */
    private Boolean isDefault;
}