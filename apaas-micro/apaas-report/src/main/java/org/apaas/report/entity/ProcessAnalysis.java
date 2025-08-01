package org.apaas.report.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("process_analysis")
public class ProcessAnalysis extends BaseEntity<Long> {
    private String processName;
    private Long processId;
    private Double averageDuration;
    private Double maxDuration;
    private Double minDuration;
    private Integer totalInstances;
    private Integer completedInstances;
    private Integer failedInstances;
    private String bottleneckNode;
}