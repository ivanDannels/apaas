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

/**
 * 数据字典项实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("data_dictionary_item")
@EqualsAndHashCode(callSuper = true)
public class DataDictionaryItem extends BaseEntity<Long> {
    
    /**
     * 字典ID
     */
    private Long dictionaryId;
    
    /**
     * 字典项编码
     */
    private String code;
    
    /**
     * 字典项名称
     */
    private String name;
    
    /**
     * 字典项值
     */
    private String value;
    
    /**
     * 排序
     */
    private Integer sequence;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 获取字典ID
     */
    public Long getDictionaryId() {
        return dictionaryId;
    }
    
    /**
     * 设置字典ID
     */
    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
    
    /**
     * 获取字典项编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置字典项编码
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取字典项名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置字典项名称
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取字典项值
     */
    public String getValue() {
        return value;
    }
    
    /**
     * 设置字典项值
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * 获取排序
     */
    public Integer getSequence() {
        return sequence;
    }
    
    /**
     * 设置排序
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    
    /**
     * 获取状态
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 设置描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}