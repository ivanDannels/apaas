package org.apaas.design.platform.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("code_template")
public class CodeTemplate extends BaseEntity<Long> {
    private String name;
    private String description;
    private String content;
    private String language;
    private String type;
}