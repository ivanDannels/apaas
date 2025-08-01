package org.apaas.report.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("user_analysis")
public class UserAnalysis extends BaseEntity<Long> {
    private Long userId;
    private String userName;
    private Integer activeDays;
    private Integer approvalCount;
    private Double averageResponseTime;
    private Double maxResponseTime;
    private Double minResponseTime;
}