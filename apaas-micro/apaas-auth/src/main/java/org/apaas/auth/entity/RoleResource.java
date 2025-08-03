package org.apaas.auth.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 角色资源关联实体类
 * @author ivan
 */
@Data
@Table("sys_role_resource")
public class RoleResource {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 租户ID
     */
    private Long tenantId;

}