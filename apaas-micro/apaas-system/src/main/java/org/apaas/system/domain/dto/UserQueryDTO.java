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
package org.apaas.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户查询DTO
 */
@Data
@Schema(description = "用户查询DTO")
public class UserQueryDTO {
    
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;
    
    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;
    
    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;
    
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String beginTime;
    
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;
}