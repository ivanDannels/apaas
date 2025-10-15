package org.apaas.job.application.dto;

import lombok.Data;
import org.apaas.application.dto.BaseDTO;
import java.time.LocalDateTime;

@Data
// 修改类声明，继承BaseDTO
public class JobDTO extends BaseDTO<Long> {
    private String name;
    private String description;
    private String cronExpression;
    private Integer status;
    private String parameters;
    private LocalDateTime lastTriggerTime;
    // 移除已经在BaseDTO中定义的字段
    // private Long id;
    // private String createdBy;
    // private LocalDateTime createdTime;
    // private String updatedBy;
    // private LocalDateTime updatedTime;
}