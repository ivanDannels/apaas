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
package org.apaas.flow.engine.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * 启动流程实例DTO
 */
@Data
public class StartInstanceDTO {
    
    /**
     * 流程定义ID
     */
    @NotNull(message = "流程定义ID不能为空")
    private Long definitionId;
    
    /**
     * 流程实例标题
     */
    private String title;
    
    /**
     * 业务主键
     */
    private String businessKey;
    
    /**
     * 业务表单数据
     */
    private String businessData;
    
    /**
     * 发起人ID
     */
    private Long starterId;
    
    /**
     * 流程变量
     */
    private Map<String, Object> variables;
}