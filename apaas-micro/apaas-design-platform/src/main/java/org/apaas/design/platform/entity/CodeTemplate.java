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
@Table("code_template")
public class CodeTemplate extends BaseEntity {
    private String name;
    private String description;
    private String content;
    private String language;
    private String type;
}