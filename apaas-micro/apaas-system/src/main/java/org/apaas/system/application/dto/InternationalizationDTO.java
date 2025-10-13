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

/**
 * 国际化DTO
 *
 * @author ivan
 */
@Data
public class InternationalizationDTO {
    
    /**
     * 国际化ID
     */
    private Long id;
    
    /**
     * 国际化编码
     */
    private String code;
    
    /**
     * 国际化消息
     */
    private String message;
    
    /**
     * 语言
     */
    private String language;
    
    /**
     * 国家
     */
    private String country;
    
    /**
     * 地区
     */
    private String region;
    
    /**
     * 区域设置
     */
    private String locale;
    
    /**
     * 时区
     */
    private String timeZone;
    
    /**
     * 货币
     */
    private String currency;
    
    /**
     * 货币符号
     */
    private String currencySymbol;
    
    /**
     * 货币编码
     */
    private String currencyCode;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}