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
package org.apaas.system.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典聚合根
 * 聚合根是聚合的入口点，负责维护聚合内部的一致性
 *
 * @author ivan
 */
@Data
@SuperBuilder
@Table("data_dictionary")
@EqualsAndHashCode(callSuper = true)
public class DataDictionaryAggregate extends BaseEntity<Long> {
    
    /**
     * 字典名称
     */
    private String name;
    
    /**
     * 字典编码
     */
    private String code;
    
    /**
     * 字典类型：0-系统字典，1-业务字典
     */
    private Integer type;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 数据字典项列表
     */
    private List<DataDictionaryItem> items;
    
    /**
     * 构造函数
     */
    public DataDictionaryAggregate() {
        this.items = new ArrayList<>();
    }
    
    /**
     * 添加数据字典项
     *
     * @param item 数据字典项
     */
    public void addItem(DataDictionaryItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        // 设置字典项的字典ID
        item.setDictionaryId(this.getId());
        this.items.add(item);
    }
    
    /**
     * 移除数据字典项
     *
     * @param item 数据字典项
     */
    public void removeItem(DataDictionaryItem item) {
        if (this.items != null) {
            this.items.remove(item);
        }
    }
    
    /**
     * 获取聚合根标识
     *
     * @return 聚合根标识
     */
    @Override
    public Long getId() {
        return super.getId();
    }
    
    /**
     * 设置聚合根标识
     *
     * @param id 聚合根标识
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
        // 同步更新所有字典项的字典ID
        if (this.items != null) {
            this.items.forEach(item -> item.setDictionaryId(id));
        }
    }
    
    /**
     * 获取字典名称
     *
     * @return 字典名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置字典名称
     *
     * @param name 字典名称
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取字典编码
     *
     * @return 字典编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置字典编码
     *
     * @param code 字典编码
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取字典类型
     *
     * @return 字典类型
     */
    public Integer getType() {
        return type;
    }
    
    /**
     * 设置字典类型
     *
     * @param type 字典类型
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
    /**
     * 获取字典状态
     *
     * @return 字典状态
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置字典状态
     *
     * @param status 字典状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取字典描述
     *
     * @return 字典描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 设置字典描述
     *
     * @param description 字典描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 获取数据字典项列表
     *
     * @return 数据字典项列表
     */
    public List<DataDictionaryItem> getItems() {
        return items;
    }
    
    /**
     * 设置数据字典项列表
     *
     * @param items 数据字典项列表
     */
    public void setItems(List<DataDictionaryItem> items) {
        this.items = items;
    }
}