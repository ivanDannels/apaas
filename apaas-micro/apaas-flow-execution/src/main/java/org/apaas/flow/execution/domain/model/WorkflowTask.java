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
package org.apaas.flow.execution.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("workflow_task")
public class WorkflowTask extends BaseEntity<Long> {
    
    /**
     * 活动实例ID
     */
    private Long activityInstanceId;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 任务类型
     */
    private String taskType;
    
    /**
     * 任务处理人
     */
    private Long assignee;
    
    /**
     * 候选处理人
     */
    private String candidateUsers;
    
    /**
     * 候选处理组
     */
    private String candidateGroups;
    
    /**
     * 任务状态
     */
    private String status;
    
    /**
     * 优先级
     */
    private Integer priority;
    
    /**
     * 到期时间
     */
    private LocalDateTime dueTime;
    
    /**
     * 开始处理时间
     */
    private LocalDateTime startTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    private Long duration;
    
    /**
     * 表单数据ID
     */
    private Long formDataId;
    
}