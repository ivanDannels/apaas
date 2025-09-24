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

/**
 * 流程定义DTO
 */
@Data
public class ProcessDefinitionDTO {
    
    /**
     * 流程定义ID
     */
    private String id;
    
    /**
     * 流程名称
     */
    private String name;
    
    /**
     * 流程编码
     */
    private String code;
    
    /**
     * 流程描述
     */
    private String description;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 状态：0-禁用，1-激活
     */
    private Integer status;
    
    /**
     * 流程分类
     */
    private String category;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 流程XML内容
     */
    private String xmlContent;
    
    /**
     * 关联表单ID
     */
    private String formId;
    
    /**
     * 租户ID
     */
    private String tenantId;
}