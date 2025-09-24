package org.apaas.design.platform.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("api_entity")
public class APIEntity extends BaseEntity {
    private String name;
    private String path;
    private String method;
    private String description;
    private String sqlScript;
    private String version;
    private Integer status;
}