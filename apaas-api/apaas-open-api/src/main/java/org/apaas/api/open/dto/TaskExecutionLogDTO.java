/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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