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
 * 表单字段实体
 */
@Data
@SuperBuilder
@Table("form_field")
@EqualsAndHashCode(callSuper = true)
public class FormField extends BaseEntity<Long> {
    
    /**
     * 表单定义ID
     */
    private Long formId;
    
    /**
     * 字段名称
     */
    private String name;
    
    /**
     * 字段编码
     */
    private String code;
    
    /**
     * 字段类型（0-文本，1-数字，2-日期，3-单选，4-多选，5-下拉，6-附件，7-子表单，8-关联查询选择）
     */
    private Integer type;
    
    /**
     * 字段配置JSON
     */
    private String configJson;
    
    /**
     * 校验规则JSON
     */
    private String validationJson;
    
    /**
     * 默认值
     */
    private String defaultValue;
    
    /**
     * 是否必填（0-否，1-是）
     */
    private Integer required;
    
    /**
     * 是否只读（0-否，1-是）
     */
    private Integer readonly;
    
    /**
     * 是否隐藏（0-否，1-是）
     */
    private Integer hidden;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 所属分组
     */
    private String groupName;
    
    /**
     * 字段说明
     */
    private String description;
}