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
package org.apaas.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.auth.domain.DataScope;
import org.apaas.domain.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 角色实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Long> {
    
    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 角色编码
     */
    private String code;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;
    
    /**
     * 应用ID
     */
    private Long applicationId;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 数据范围类型
     */
    private String dataScopeType;
    
    /**
     * 数据权限列表，根据数据范围类型获取
     */
    private Long[] dataScopes;
    
    /**
     * 获取数据范围枚举
     */
    public DataScope getDataScope() {
        return DataScope.getByCode(this.dataScopeType);
    }
    
    /**
     * 设置数据范围枚举
     */
    public void setDataScope(DataScope dataScope) {
        this.dataScopeType = dataScope != null ? dataScope.getCode() : null;
    }
}