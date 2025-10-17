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
package org.apaas.design.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apaas.application.dto.BaseDTO;

/**
 * API定义DTO
 *
 * @author ivan
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiDefinitionDTO extends BaseDTO<Long> {
    
    /**
     * API ID
     */
    private Long id;
    
    /**
     * API名称
     */
    private String name;
    
    /**
     * API路径
     */
    private String path;
    
    /**
     * HTTP方法
     */
    private String method;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * SQL脚本
     */
    private String sqlScript;
    
    /**
     * 数据源ID
     */
    private Long dataSourceId;
    
    /**
     * 请求参数
     */
    private String requestParams;
    
    /**
     * 响应结构
     */
    private String responseStructure;
    
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}