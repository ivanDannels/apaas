package org.apaas.report.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("report")
public class Report extends BaseEntity<Long> {
    private String name;
    private String description;
    private String type;
    private String content;
    private String status;
}