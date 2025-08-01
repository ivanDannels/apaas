package org.apaas.design.platform.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("data_source")
public class DataSource extends BaseEntity<Long> {
    private String name;
    private String type;
    private String url;
    private String username;
    private String password;
    private String driverClass;
}