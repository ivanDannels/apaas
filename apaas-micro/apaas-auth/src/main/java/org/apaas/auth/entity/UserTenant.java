package org.apaas.auth.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户租户关系
 * @author ivan
 */
@Data
@Table("sys_user_tenant")
public class UserTenant {

    private Long userId;

    private Long tenantId;

}
