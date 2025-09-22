package org.apaas.core.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("test_entity")
public class TestEntity extends BaseEntity {
    private String name;
    private String description;
}