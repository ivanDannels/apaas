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
package org.apaas.system.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志DTO
 *
 * @author ivan
 */
@Data
public class SysOperLogDTO {
    
    /**
     * 日志ID
     */
    private Long id;
    
    /**
     * 操作模块
     */
    private String title;
    
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;
    
    /**
     * 方法名称
     */
    private String method;
    
    /**
     * 请求方式
     */
    private String requestMethod;
    
    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private String operatorType;
    
    /**
     * 操作人员
     */
    private String operName;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 请求URL
     */
    private String operUrl;
    
    /**
     * 主机地址
     */
    private String operIp;
    
    /**
     * 操作地点
     */
    private String operLocation;
    
    /**
     * 请求参数
     */
    private String operParam;
    
    /**
     * 返回参数
     */
    private String jsonResult;
    
    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;
    
    /**
     * 错误消息
     */
    private String errorMsg;
    
    /**
     * 操作时间
     */
    private LocalDateTime operTime;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}