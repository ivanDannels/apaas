package org.apaas.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据范围枚举
 * @author system
 */
@Getter
@AllArgsConstructor
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
     * 查看当前部门数据
     */
    CURRENT_DEPT("current_dept", "查看当前部门数据"),

    /**
     * 查看个人数据
     */
    CURRENT_USER("current_user", "查看个人数据"),

    /**
     * 查看指定部门数据
     */
    CUSTOM_DEPTS("custom_depts", "查看指定部门数据");

    private final String code;
    private final String description;

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