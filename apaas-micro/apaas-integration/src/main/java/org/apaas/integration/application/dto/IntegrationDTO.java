package org.apaas.integration.application.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class IntegrationDTO {
    private Long id;
    private String name;
    private String type;
    private Map<String, Object> config;
    private String status;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
}