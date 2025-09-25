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
package org.apaas.auth.utils;

import org.apaas.auth.domain.DataScope;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限工具类
 * @author system
 */
public class DataScopeUtils {
    
    /**
     * 根据数据范围获取部门ID列表
     * @param dataScope 数据范围
     * @param customOrganizationIds 自定义部门ID列表
     * @return 部门ID列表
     */
    public static List<Long> getDeptIdsByDataScope(DataScope dataScope, List<Long> customOrganizationIds) {
        return switch (dataScope) {
            case ALL_TENANTS ->
                // 查看所有租户数据，返回null表示不限制部门
                null;
            case CURRENT_TENANT ->
                // 查看当前租户数据，返回null表示不限制部门，但会通过租户ID过滤
                null;
            case CURRENT_ORGANIZATION ->
                // 查看当前部门数据
                null;
            case CURRENT_USER ->
                // 查看个人数据，通过用户ID过滤，这里返回空列表
                List.of();
            case CUSTOM_ORGANIZATIONS ->
                // 查看指定部门数据
                CollectionUtils.isEmpty(customOrganizationIds) ? List.of() : customOrganizationIds;
        };
    }
    
    /**
     * 构建数据范围SQL条件
     * @param dataScope 数据范围
     * @param organizationIds 部门ID列表
     * @param userId 当前用户ID
     * @param tenantId 当前租户ID
     * @param organizationColumn 部门字段名
     * @param userIdColumn 用户字段名
     * @param tenantIdColumn 租户字段名
     * @return SQL条件字符串
     */
    public static String buildDataScopeSql(DataScope dataScope, List<Long> organizationIds, Long userId, Long tenantId, String organizationColumn, String userIdColumn, String tenantIdColumn) {
        StringBuilder sql = new StringBuilder();
        
        // 租户过滤（除了ALL_TENANTS）
        if (dataScope != DataScope.ALL_TENANTS && tenantId != null && tenantId > 0) {
            sql.append(" AND ").append(tenantIdColumn).append(" = '").append(tenantId).append("'");
        }
        
        // 部门过滤
        if (!CollectionUtils.isEmpty(organizationIds)) {
            String deptIdsStr = organizationIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            sql.append(" AND ").append(organizationColumn).append(" IN (").append(deptIdsStr).append(")");
        }
        
        // 用户过滤（仅CURRENT_USER）
        if (dataScope == DataScope.CURRENT_USER && userId != null && userId > 0) {
            sql.append(" AND ").append(userIdColumn).append(" = '").append(userId).append("'");
        }
        
        return !sql.isEmpty() ? sql.substring(5) : "";
    }
}