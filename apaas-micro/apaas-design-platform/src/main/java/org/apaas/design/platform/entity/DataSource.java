package org.apaas.design.platform.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("data_source")
public class DataSource extends BaseEntity {
    private String name;
    private String type;
    private String url;
    private String username;
    private String password;
    private String driverClass;
}