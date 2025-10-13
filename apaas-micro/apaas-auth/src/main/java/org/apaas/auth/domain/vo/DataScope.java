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
package org.apaas.auth.domain.vo;

/**
 * 数据范围枚举
 * @author system
 */
public enum DataScope {
    
    /**
     * 查看所有租户数据
     */
    ALL_TENANTS("all_tenants", "查看所有租户数据"),
    
    /**
     * 查看当前租户数据
     */
    CURRENT_TENANT("current_tenant", "查看当前租户数据"),
    
    /**
     * 查看当前组织数据
     */
    CURRENT_ORGANIZATION("current_organization", "查看当前组织数据"),
    
    /**
     * 查看个人数据
     */
    CURRENT_USER("current_user", "查看个人数据"),
    
    /**
     * 查看指定部门数据
     */
    CUSTOM_ORGANIZATIONS("custom_organizations", "查看指定组织数据");
    
    private final String code;
    private final String description;
    
    // 添加构造函数
    DataScope(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 获取编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 获取描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据编码获取数据范围
     */
    public static DataScope getByCode(String code) {
        for (DataScope scope : values()) {
            if (scope.getCode().equals(code)) {
                return scope;
            }
        }
        return null;
    }
}