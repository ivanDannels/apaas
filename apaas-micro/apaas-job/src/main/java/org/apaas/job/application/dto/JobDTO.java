package org.apaas.job.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.application.dto.BaseDTO;
import java.time.LocalDateTime;

/**
 * @author ivan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobDTO extends BaseDTO<Long> {
    private String name;
    private String description;
    private String cronExpression;
    private Integer status;
    private String parameters;
    private LocalDateTime lastTriggerTime;
}