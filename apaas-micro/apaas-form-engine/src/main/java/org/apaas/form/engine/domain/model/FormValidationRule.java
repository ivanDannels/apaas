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
import org.apaas.domain.entity.BaseEntity;

/**
 * 表单验证规则实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_validation_rule")
public class FormValidationRule extends BaseEntity<Long> {
    
    /**
     * 表单字段ID
     */
    private Long fieldId;
    
    /**
     * 规则类型（0-必填，1-长度，2-正则，3-自定义脚本，4-跨字段校验）
     */
    private Integer type;
    
    /**
     * 规则名称
     */
    private String name;
    
    /**
     * 规则值
     */
    private String value;
    
    /**
     * 错误提示信息
     */
    private String errorMessage;
    
    /**
     * 规则配置JSON
     */
    private String configJson;
    
    /**
     * 规则状态（0-启用，1-禁用）
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
}