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
package org.apaas.form.engine.domain.model;

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
    
    /**
     * 获取表单编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置表单编码
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取表单类型
     */
    public Integer getType() {
        return type;
    }
    
    /**
     * 设置表单类型
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
    /**
     * 获取表单状态
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置表单状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取表单配置JSON
     */
    public String getConfigJson() {
        return configJson;
    }
    
    /**
     * 设置表单配置JSON
     */
    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }
    
    /**
     * 获取表单项JSON
     */
    public String getItemsJson() {
        return itemsJson;
    }
    
    /**
     * 设置表单项JSON
     */
    public void setItemsJson(String itemsJson) {
        this.itemsJson = itemsJson;
    }
    
    /**
     * 获取数据源ID
     */
    public Long getDataSourceId() {
        return dataSourceId;
    }
    
    /**
     * 设置数据源ID
     */
    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    
    /**
     * 获取关联流程ID
     */
    public Long getFlowId() {
        return flowId;
    }
    
    /**
     * 设置关联流程ID
     */
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }
    
    /**
     * 获取版本号
     */
    public Integer getVersion() {
        return version;
    }
    
    /**
     * 设置版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * 获取是否为默认版本
     */
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    /**
     * 设置是否为默认版本
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    /**
     * 获取表单名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置表单名称
     */
    public void setName(String name) {
        this.name = name;
    }
}