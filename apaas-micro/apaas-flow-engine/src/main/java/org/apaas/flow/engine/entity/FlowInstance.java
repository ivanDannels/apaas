package org.apaas.flow.engine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apaas.core.domain.BaseEntity;
import java.time.LocalDateTime;

/**
 * 流程实例实体
 */
@Data
@TableName("flow_instance")
public class FlowInstance extends BaseEntity {
    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程定义名称
     */
    private String definitionName;

    /**
     * 流程定义编码
     */
    private String definitionCode;

    /**
     * 流程定义版本
     */
    private Integer definitionVersion;

    /**
     * 业务主键ID
     */
    private String businessKey;

    /**
     * 业务表单数据
     */
    private String businessData;

    /**
     * 当前节点ID
     */
    private String currentNodeId;

    /**
     * 当前节点名称
     */
    private String currentNodeName;

    /**
     * 流程状态（0-运行中，1-已完成，2-已终止，3-已暂停）
     */
    private Integer status;

    /**
     * 发起人ID
     */
    private Long startUserId;

    /**
     * 发起人名称
     */
    private String startUserName;

    /**
     * 发起时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}