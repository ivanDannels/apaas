package org.apaas.design.platform.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("api_entity")
public class APIEntity extends BaseEntity<Long> {
    private String name;
    private String path;
    private String method;
    private String description;
    private String sqlScript;
    private String version;
    private Integer status;
}