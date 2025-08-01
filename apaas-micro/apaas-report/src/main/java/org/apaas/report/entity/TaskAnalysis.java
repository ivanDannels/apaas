package org.apaas.report.entity;

import org.apaas.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("task_analysis")
public class TaskAnalysis extends BaseEntity<Long> {
    private String taskName;
    private Long taskId;
    private Integer pendingCount;
    private Integer completedCount;
    private Integer overdueCount;
    private Double averageProcessingTime;
    private Double maxProcessingTime;
    private Double minProcessingTime;
}