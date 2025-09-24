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
package org.apaas.flow.engine.domain.dto;

import lombok.Data;

/**
 * 流程实例查询DTO
 */
@Data
public class FlowInstanceDTO {
    
    /**
     * 流程定义ID
     */
    private Long definitionId;
    
    /**
     * 流程实例状态
     */
    private Integer status;
    
    /**
     * 发起人ID
     */
    private Long startUserId;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}