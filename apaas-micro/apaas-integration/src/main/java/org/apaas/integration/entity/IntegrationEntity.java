package org.apaas.integration.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("integration_entities")
public class IntegrationEntity extends BaseEntity {
    private String name;
    private String description;
}