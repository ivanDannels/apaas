package org.apaas.design.platform.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("metadata")
public class Metadata extends BaseEntity {
    private String name;
    private String description;
    private String type;
    private String content;
    private String version;
}