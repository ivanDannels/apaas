package org.apaas.report.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportUtil {
    
    public static String generateReportName(String baseName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return baseName + "_" + timestamp;
    }
    
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}