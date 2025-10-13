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
import org.apaas.domain.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 数据可视化实体类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("visualization")
public class Visualization extends BaseEntity<Long> {
    
    /**
     * 可视化名称
     */
    private String name;
    
    /**
     * 可视化类型：0-折线图，1-柱状图，2-饼图，3-散点图，4-热力图
     */
    private Integer type;
    
    /**
     * 数据源ID
     */
    private Long dataSourceId;
    
    /**
     * 查询SQL
     */
    private String querySql;
    
    /**
     * 配置信息（JSON格式）
     */
    private String config;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
}