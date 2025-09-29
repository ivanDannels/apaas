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
 * 表单字段权限实体
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_field_permission")
public class FormFieldPermission extends BaseEntity<Long> {
    
    /**
     * 表单定义ID
     */
    private Long formId;
    
    /**
     * 表单字段ID
     */
    private Long fieldId;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 权限类型（0-查看，1-编辑）
     */
    private Integer permissionType;
    
    /**
     * 权限状态（0-允许，1-禁止）
     */
    private Integer permissionStatus;
    
    /**
     * 条件表达式JSON
     */
    private String conditionJson;
}