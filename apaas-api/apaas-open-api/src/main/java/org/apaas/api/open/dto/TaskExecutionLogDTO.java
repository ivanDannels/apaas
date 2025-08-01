package org.apaas.api.open.dto;

import lombok.Data;

import java.util.Date;

/**
 * 任务执行日志DTO
 */
@Data
public class TaskExecutionLogDTO {
    /**
     * 日志ID
     */
    private String id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 执行批次
     */
    private String batchId;

    /**
     * 执行状态：0-待执行，1-执行中，2-执行成功，3-执行失败，4-执行超时
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 执行耗时（毫秒）
     */
    private Long executionTime;

    /**
     * 执行参数
     */
    private String params;

    /**
     * 执行结果
     */
    private String result;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 执行节点
     */
    private String executionNode;
}