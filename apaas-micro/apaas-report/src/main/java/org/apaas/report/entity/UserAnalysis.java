package org.apaas.report.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("user_analysis")
public class UserAnalysis extends BaseEntity {
    private Long userId;
    private String userName;
    private Integer activeDays;
    private Integer approvalCount;
    private Double averageResponseTime;
    private Double maxResponseTime;
    private Double minResponseTime;
}