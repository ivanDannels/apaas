package org.apaas.auth.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户岗位关联
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_user_position")
public class UserPosition {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 是否兼职 0-否 1-是
     */
    private int isPartTime;
}
