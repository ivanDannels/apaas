package org.apaas.job.application.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobDTO {
    private Long id;
    private String name;
    private String description;
    private String cronExpression;
    private Integer status;
    private String parameters;
    private LocalDateTime lastTriggerTime;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
}