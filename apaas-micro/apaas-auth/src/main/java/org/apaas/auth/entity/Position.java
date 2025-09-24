package org.apaas.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 岗位实体
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_position")
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseEntity {

    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位描述
     */
    private String description;
    /**
     * 岗位状态
     */
    private Integer status;
    /**
     * 租户ID
     */
    private Long tenantId;
}
