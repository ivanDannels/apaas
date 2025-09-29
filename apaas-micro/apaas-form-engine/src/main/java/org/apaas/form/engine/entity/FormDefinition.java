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
package org.apaas.form.engine.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 表单定义实体
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_definition")
public class FormDefinition extends BaseEntity<Long> {
    
    /**
     * 表单名称
     */
    private String name;
    
    /**
     * 表单编码
     */
    private String code;
    
    /**
     * 表单类型（0-普通表单，1-流程表单，2-统计表单）
     */
    private Integer type;
    
    /**
     * 表单状态（0-草稿，1-已发布，2-已停用）
     */
    private Integer status;
    
    /**
     * 表单配置JSON
     */
    private String configJson;
    
    /**
     * 表单项JSON
     */
    private String itemsJson;
    
    /**
     * 数据源ID
     */
    private Long dataSourceId;
    
    /**
     * 关联流程ID
     */
    private Long flowId;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 是否为默认版本
     */
    private Boolean isDefault;
}