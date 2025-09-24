package org.apaas.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 应用实体
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_application")
@EqualsAndHashCode(callSuper = true)
public class Application extends BaseEntity {

    private String name;

    private String code;

    private String description;

    private Integer status;

    private Long tenantId;

}
