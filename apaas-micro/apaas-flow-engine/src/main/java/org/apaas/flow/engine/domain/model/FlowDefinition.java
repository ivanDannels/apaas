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

/**
 * 流程定义实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("flow_definition")
public class FlowDefinition extends BaseEntity<Long> {
    
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
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private java.time.LocalDateTime updatedTime;
}