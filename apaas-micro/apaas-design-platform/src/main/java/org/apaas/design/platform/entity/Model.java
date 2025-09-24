package org.apaas.design.platform.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("model")
public class Model extends BaseEntity {
    private String name;
    private String description;
    private String content;
    private String version;
    private String type;
}