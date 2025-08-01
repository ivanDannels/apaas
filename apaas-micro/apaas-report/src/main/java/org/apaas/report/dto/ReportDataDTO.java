package org.apaas.report.dto;

import lombok.Data;

@Data
public class ReportDataDTO {
    private String reportName;
    private String reportType;
    private String generatedBy;
    private String generatedAt;
    private Object data;
}