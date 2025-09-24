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
 * 流程实例DTO
 */
@Data
public class ProcessInstanceDTO {
    
    /**
     * 流程实例ID
     */
    private String id;
    
    /**
     * 流程定义ID
     */
    private String definitionId;
    
    /**
     * 流程定义名称
     */
    private String definitionName;
    
    /**
     * 流程定义编码
     */
    private String definitionCode;
    
    /**
     * 流程实例名称
     */
    private String name;
    
    /**
     * 启动人ID
     */
    private String startUserId;
    
    /**
     * 启动人名称
     */
    private String startUserName;
    
    /**
     * 启动时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 状态：0-未启动，1-运行中，2-已完成，3-已暂停，4-已终止
     */
    private Integer status;
    
    /**
     * 业务键
     */
    private String businessKey;
    
    /**
     * 流程变量
     */
    private Map<String, Object> variables;
    
    /**
     * 租户ID
     */
    private String tenantId;
}