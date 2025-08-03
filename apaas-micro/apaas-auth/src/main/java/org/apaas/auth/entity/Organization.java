package org.apaas.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 组织
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_organization")
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseEntity {

    private String name;

    private String code;

    private String description;

    private Integer status;

    private Long parentId;

    private Long ownerId;

    private Long tenantId;
}
