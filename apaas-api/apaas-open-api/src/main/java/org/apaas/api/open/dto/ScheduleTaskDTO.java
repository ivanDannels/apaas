package org.apaas.api.open.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 调度任务DTO
 */
@Data
public class ScheduleTaskDTO {
    /**
     * 任务ID
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务编码
     */
    private String code;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务类型：1-定时任务，2-延时任务，3-周期性任务，4-依赖任务，5-分布式任务
     */
    private Integer taskType;

    /**
     * 执行器类型：1-Java方法，2-HTTP接口，3-消息队列，4-脚本
     */
    private Integer executorType;

    /**
     * 执行器配置
     */
    private String executorConfig;

    /**
     * 调度策略配置（Cron表达式或固定间隔）
     */
    private String scheduleConfig;

    /**
     * 启用状态：0-禁用，1-启用
     */
    private Integer enabled;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 重试间隔（毫秒）
     */
    private Long retryInterval;

    /**
     * 超时时间（毫秒）
     */
    private Long timeout;

    /**
     * 参数
     */
    private Map<String, Object> params;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 租户ID
     */
    private String tenantId;
}