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
package org.apaas.design.platform.domain.model;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 数据同步任务实体类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("data_sync_task")
public class DataSyncTask extends BaseEntity<Long> {
    
    /**
     * 任务名称
     */
    private String name;
    
    /**
     * 源数据源ID
     */
    private Long sourceDataSourceId;
    
    /**
     * 目标数据源ID
     */
    private Long targetDataSourceId;
    
    /**
     * 同步SQL脚本
     */
    private String syncSql;
    
    /**
     * 同步周期（Cron表达式）
     */
    private String cronExpression;
    
    /**
     * 最后执行时间
     */
    private LocalDateTime lastExecuteTime;
    
    /**
     * 下次执行时间
     */
    private LocalDateTime nextExecuteTime;
    
    /**
     * 执行状态：0-待执行，1-执行中，2-执行成功，3-执行失败
     */
    private Integer executeStatus;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
    
    /**
     * 失败原因
     */
    private String failReason;
}