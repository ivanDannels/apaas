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
package org.apaas.form.engine.application.dto;

import lombok.Data;

/**
 * 表单定义查询DTO
 */
@Data
public class FormDefinitionDTO {
    
    /**
     * 表单ID
     */
    private Long id;
    
    /**
     * 表单名称
     */
    private String name;
    
    /**
     * 表单类型
     */
    private Integer type;
    
    /**
     * 表单状态
     */
    private Integer status;
    
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