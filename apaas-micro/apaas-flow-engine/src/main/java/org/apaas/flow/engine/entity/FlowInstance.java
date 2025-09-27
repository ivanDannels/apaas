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
package org.apaas.flow.engine.entity;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import org.apaas.domain.entity.BaseEntity;
import java.time.LocalDateTime;

/**
 * 流程实例实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("flow_instance")
public class FlowInstance extends BaseEntity {
    
    /**
     * 流程定义ID
     */
    private Long definitionId;
    
    /**
     * 流程实例标题
     */
    private String title;
    
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
    
    /**
     * 流程变量
     */
    private String variables;
}