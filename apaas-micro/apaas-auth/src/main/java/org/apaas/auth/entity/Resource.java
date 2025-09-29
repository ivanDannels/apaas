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

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.domain.entity.BaseEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import lombok.Data;

/**
 * 资源实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_resource")
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity<Long> {
    
    /**
     * 资源名称
     */
    private String name;
    
    /**
     * 资源类型：0-菜单，1-按钮
     */
    private Integer type;
    
    /**
     * 路径
     */
    private String path;
    
    /**
     * 组件
     */
    private String component;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 排序
     */
    private Integer sequence;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;
    
    /**
     * 应用ID
     */
    private Long applicationId;
    
    /**
     * 子资源列表（非数据库字段）
     */
    @Transient
    private List<Resource> children;
    
    /**
     * 是否选中（用于角色资源分配，非数据库字段）
     */
    @Transient
    private Boolean selected;
}