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
package org.apaas.design.platform.domain.model;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 代码生成器实体类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("code_generator")
public class CodeGenerator extends BaseEntity<Long> {
    
    /**
     * 生成器名称
     */
    private String name;
    
    /**
     * 生成器描述
     */
    private String description;
    
    /**
     * 模板ID
     */
    private Long templateId;
    
    /**
     * 元数据ID
     */
    private Long metadataId;
    
    /**
     * 输出路径
     */
    private String outputPath;
    
    /**
     * 生成类型：0-Java代码，1-前端代码，2-全栈代码
     */
    private Integer type;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
}