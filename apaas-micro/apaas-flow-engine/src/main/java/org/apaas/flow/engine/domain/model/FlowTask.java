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
package org.apaas.flow.engine.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 流程任务实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Table("flow_task")
public class FlowTask extends BaseEntity<Long> {
    
    /**
     * 流程实例ID
     */
    private Long instanceId;
    
    /**
     * 流程实例名称
     */
    private String instanceName;
    
    /**
     * 流程定义ID
     */
    private Long definitionId;
    
    /**
     * 流程定义版本
     */
    private Integer definitionVersion;
    
    /**
     * 节点ID
     */
    private String nodeId;
    
    /**
     * 节点名称
     */
    private String nodeName;
    
    /**
     * 节点类型（0-开始节点，1-审批节点，2-条件节点，3-并行节点，4-结束节点）
     */
    private Integer nodeType;
    
    /**
     * 任务状态（0-未开始，1-处理中，2-已完成，3-已终止，4-已退回）
     */
    private Integer status;
    
    /**
     * 任务优先级（0-普通，1-紧急，2-非常紧急）
     */
    private Integer priority;
    
    /**
     * 处理人ID
     */
    private Long assigneeId;
    
    /**
     * 处理人名称
     */
    private String assigneeName;
    
    /**
     * 候选处理人IDs
     */
    private String candidateIds;
    
    /**
     * 候选处理人Names
     */
    private String candidateNames;
    
    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 任务处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 任务完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 处理人ID
     */
    private Long handleUserId;
    
    /**
     * 处理人名称
     */
    private String handleUserName;
    
    /**
     * 任务意见
     */
    private String comment;
    
    /**
     * 任务变量
     */
    private String variables;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
    
}