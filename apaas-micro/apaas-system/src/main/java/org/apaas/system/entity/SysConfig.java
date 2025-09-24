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
package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 参数配置实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_config")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseEntity {
    
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 参数键
     */
    private String configKey;
    
    /**
     * 参数编码
     */
    private String code;
    
    /**
     * 参数值
     */
    private String value;
    
    /**
     * 参数类型：0-系统参数，1-业务参数
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
}