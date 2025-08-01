package org.apaas.design.platform.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("model")
public class Model extends BaseEntity<Long> {
    private String name;
    private String description;
    private String content;
    private String version;
    private String type;
}