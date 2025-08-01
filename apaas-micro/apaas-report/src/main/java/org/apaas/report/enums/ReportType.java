package org.apaas.report.enums;

public enum ReportType {
    PROCESS_ANALYSIS("流程分析"),
    TASK_ANALYSIS("任务分析"),
    USER_ANALYSIS("用户分析");
    
    private final String description;
    
    ReportType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}