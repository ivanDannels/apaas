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

import java.time.LocalDateTime;

/**
 * 操作日志
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_oper_log")
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends BaseEntity<Long> {
    
    private String title;
    
    private Integer businessType;
    
    private String method;
    
    private String requestMethod;
    
    private String operatorType;
    
    private String operName;
    
    private String deptName;
    
    private String operUrl;
    
    private String operIp;
    
    private String operLocation;
    
    private String operParam;
    
    private String jsonResult;
    
    private Integer status;
    
    private String errorMsg;
    
    private LocalDateTime operTime;
    
}