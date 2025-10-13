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

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import org.apaas.domain.domain.entity.BaseEntity;

/**
 * 表单布局实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_layout")
public class FormLayout extends BaseEntity<Long> {
    
    /**
     * 表单定义ID
     */
    private Long formId;
    
    /**
     * 布局类型（0-响应式布局，1-自由布局，2-栅格布局，3-分组布局）
     */
    private Integer type;
    
    /**
     * 布局名称
     */
    private String name;
    
    /**
     * 布局配置JSON
     */
    private String configJson;
    
    /**
     * 布局状态（0-启用，1-禁用）
     */
    private Integer status;
    
    /**
     * 是否默认布局（0-否，1-是）
     */
    private Integer isDefault;
    
    /**
     * 适用终端（0-PC，1-移动，2-全部）
     */
    private Integer terminal;
    
    /**
     * 排序
     */
    private Integer sort;
}