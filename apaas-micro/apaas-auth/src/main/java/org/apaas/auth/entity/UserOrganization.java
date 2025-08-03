package org.apaas.auth.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户组织关系
 * @author ivan
 */
@SuperBuilder
@Data
@Table("sys_user_organization")
public class UserOrganization {

    private Long userId;

    private Long organizationId;

    private Long tenantId;

}
