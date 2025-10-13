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
package org.apaas.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.apaas.domain.aggregate.AggregateRoot;

/**
 * 基础实体类，所有实体都需要继承
 * @author ivan
 */
@Data
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<ID extends Serializable> implements Serializable, HasDeleted, HasTenantId, AggregateRoot<ID> {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @Id
    private ID id;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 创建人
     */
    @CreatedBy
    private String creator;
    
    /**
     * 创建时间
     */
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    @LastModifiedBy
    private String updater;
    
    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    
    /**
     * 是否删除(1:是，0:否)
     */
    @Column("deleted")
    private Integer deleted = 0;
    
    @Override
    public Integer getDeleted() {
        return deleted;
    }
    
}