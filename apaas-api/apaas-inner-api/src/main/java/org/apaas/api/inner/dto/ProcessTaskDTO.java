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
package org.apaas.api.inner.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 流程任务DTO
 */
@Data
public class ProcessTaskDTO {
    
    /**
     * 任务ID
     */
    private String id;
    
    /**
     * 任务名称
     */
    private String name;
    
    /**
     * 任务定义ID
     */
    private String taskDefinitionId;
    
    /**
     * 流程实例ID
     */
    private String instanceId;
    
    /**
     * 流程定义ID
     */
    private String definitionId;
    
    /**
     * 分配人ID
     */
    private String assigneeId;
    
    /**
     * 分配人名称
     */
    private String assigneeName;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 到期时间
     */
    private Date dueDate;
    
    /**
     * 状态：0-未认领，1-已认领，2-已完成，3-已取消
     */
    private Integer status;
    
    /**
     * 优先级
     */
    private Integer priority;
    
    /**
     * 任务变量
     */
    private Map<String, Object> variables;
    
    /**
     * 任务表单
     */
    private String formKey;
    
    /**
     * 处理意见
     */
    private String comment;
    
    /**
     * 处理时间
     */
    private Date handleTime;
    
    /**
     * 租户ID
     */
    private String tenantId;
}