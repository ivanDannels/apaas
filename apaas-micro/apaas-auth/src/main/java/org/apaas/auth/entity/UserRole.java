package org.apaas.auth.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户角色关联实体类
 * @author ivan
 */
@Data
@Table("sys_user_role")
public class UserRole {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 租户ID
     */
    private Long tenantId;
}