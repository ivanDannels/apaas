package org.apaas.report.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("process_analysis")
public class ProcessAnalysis extends BaseEntity {
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