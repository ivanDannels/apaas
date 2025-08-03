package org.apaas.auth.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 应用租户关系
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_application_tenant")
public class ApplicationTenant {

    /**
     * 应用ID
     */
    private Long applicationId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 状态
     * 1：申请
     * 2：通过
     * 3：拒绝
     */
    private Integer status;

}
