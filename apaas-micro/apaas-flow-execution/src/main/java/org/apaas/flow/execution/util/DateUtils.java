package org.apaas.flow.execution.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    /** 日期时间格式 */
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 获取当前日期时间字符串
     *
     * @return 日期时间字符串
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
    
    /**
     * 格式化日期时间
     *
     * @param dateTime 日期时间
     * @return 日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
    
    /**
     * 解析日期时间字符串
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}