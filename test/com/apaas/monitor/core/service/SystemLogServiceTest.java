package com.apaas.monitor.core.service;

import com.apaas.monitor.core.domain.SysSystemLog;
import com.apaas.monitor.core.mapper.SysSystemLogMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SystemLogServiceTest {

    @Mock
    private SysSystemLogMapper systemLogMapper;

    @InjectMocks
    private SystemLogServiceImpl systemLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试记录系统日志
    @Test
    void testRecordSystemLog() {
        // Given
        SysSystemLog systemLog = new SysSystemLog();
        systemLog.setLogLevel("INFO");
        systemLog.setModule("系统启动");
        systemLog.setMessage("系统启动成功");
        systemLog.setCreateTime(LocalDateTime.now());

        // When
        when(systemLogMapper.insertSystemLog(systemLog)).thenReturn(1);

        boolean result = systemLogService.recordSystemLog(systemLog);

        // Then
        assertTrue(result);
        verify(systemLogMapper, times(1)).insertSystemLog(systemLog);
    }

    // 测试根据ID查询系统日志
    @Test
    void testGetSystemLogById() {
        // Given
        Long logId = 1L;
        SysSystemLog expected = new SysSystemLog();
        expected.setId(logId);
        expected.setLogLevel("INFO");
        expected.setModule("系统启动");
        expected.setMessage("系统启动成功");

        // When
        when(systemLogMapper.selectSystemLogById(logId)).thenReturn(expected);

        SysSystemLog result = systemLogService.getSystemLogById(logId);

        // Then
        assertNotNull(result);
        assertEquals(logId, result.getId());
        assertEquals("INFO", result.getLogLevel());
        assertEquals("系统启动", result.getModule());
        assertEquals("系统启动成功", result.getMessage());
    }

    // 测试查询系统日志列表
    @Test
    void testListSystemLogs() {
        // Given
        SysSystemLog log1 = new SysSystemLog();
        log1.setId(1L);
        log1.setLogLevel("INFO");
        log1.setModule("系统启动");
        log1.setMessage("系统启动成功");

        SysSystemLog log2 = new SysSystemLog();
        log2.setId(2L);
        log2.setLogLevel("ERROR");
        log2.setModule("数据库连接");
        log2.setMessage("数据库连接失败");

        List<SysSystemLog> expected = Arrays.asList(log1, log2);

        // When
        when(systemLogMapper.selectSystemLogList(any())).thenReturn(expected);

        List<SysSystemLog> result = systemLogService.listSystemLogs(new SysSystemLog());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("INFO", result.get(0).getLogLevel());
        assertEquals("ERROR", result.get(1).getLogLevel());
    }

    // 测试分页查询系统日志
    @Test
    void testListSystemLogsByPage() {
        // Given
        SysSystemLog query = new SysSystemLog();
        query.setLogLevel("ERROR");
        int pageNum = 1;
        int pageSize = 10;

        SysSystemLog log1 = new SysSystemLog();
        log1.setId(1L);
        log1.setLogLevel("ERROR");
        log1.setModule("数据库连接");
        log1.setMessage("数据库连接失败");

        SysSystemLog log2 = new SysSystemLog();
        log2.setId(2L);
        log2.setLogLevel("ERROR");
        log2.setModule("文件上传");
        log2.setMessage("文件上传失败");

        List<SysSystemLog> logList = Arrays.asList(log1, log2);
        int total = 2;

        // When
        when(systemLogMapper.selectSystemLogCount(query)).thenReturn(total);
        when(systemLogMapper.selectSystemLogListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(logList);

        Map<String, Object> result = systemLogService.listSystemLogsByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(logList, result.get("rows"));
        verify(systemLogMapper, times(1)).selectSystemLogCount(query);
        verify(systemLogMapper, times(1)).selectSystemLogListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试删除系统日志
    @Test
    void testDeleteSystemLog() {
        // Given
        Long logId = 1L;

        // When
        when(systemLogMapper.deleteSystemLog(logId)).thenReturn(1);

        boolean result = systemLogService.deleteSystemLog(logId);

        // Then
        assertTrue(result);
        verify(systemLogMapper, times(1)).deleteSystemLog(logId);
    }

    // 测试批量删除系统日志
    @Test
    void testBatchDeleteSystemLogs() {
        // Given
        List<Long> logIds = Arrays.asList(1L, 2L, 3L);

        // When
        when(systemLogMapper.batchDeleteSystemLogs(logIds)).thenReturn(3);

        boolean result = systemLogService.batchDeleteSystemLogs(logIds);

        // Then
        assertTrue(result);
        verify(systemLogMapper, times(1)).batchDeleteSystemLogs(logIds);
    }

    // 测试根据日志级别查询系统日志
    @Test
    void testListSystemLogsByLevel() {
        // Given
        String logLevel = "ERROR";
        SysSystemLog log1 = new SysSystemLog();
        log1.setId(1L);
        log1.setLogLevel(logLevel);
        log1.setModule("数据库连接");
        log1.setMessage("数据库连接失败");

        SysSystemLog log2 = new SysSystemLog();
        log2.setId(2L);
        log2.setLogLevel(logLevel);
        log2.setModule("文件上传");
        log2.setMessage("文件上传失败");

        List<SysSystemLog> expected = Arrays.asList(log1, log2);

        // When
        when(systemLogMapper.selectSystemLogsByLevel(logLevel)).thenReturn(expected);

        List<SysSystemLog> result = systemLogService.listSystemLogsByLevel(logLevel);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(logLevel, result.get(0).getLogLevel());
        assertEquals(logLevel, result.get(1).getLogLevel());
    }

    // 测试根据模块查询系统日志
    @Test
    void testListSystemLogsByModule() {
        // Given
        String module = "数据库操作";
        SysSystemLog log1 = new SysSystemLog();
        log1.setId(1L);
        log1.setModule(module);
        log1.setLogLevel("INFO");
        log1.setMessage("查询数据成功");

        SysSystemLog log2 = new SysSystemLog();
        log2.setId(2L);
        log2.setModule(module);
        log2.setLogLevel("ERROR");
        log2.setMessage("插入数据失败");

        List<SysSystemLog> expected = Arrays.asList(log1, log2);

        // When
        when(systemLogMapper.selectSystemLogsByModule(module)).thenReturn(expected);

        List<SysSystemLog> result = systemLogService.listSystemLogsByModule(module);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(module, result.get(0).getModule());
        assertEquals(module, result.get(1).getModule());
    }

    // 测试根据时间范围查询系统日志
    @Test
    void testListSystemLogsByTimeRange() {
        // Given
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 1, 31, 23, 59, 59);

        SysSystemLog log1 = new SysSystemLog();
        log1.setId(1L);
        log1.setCreateTime(LocalDateTime.of(2023, 1, 10, 10, 0, 0));
        log1.setModule("系统启动");
        log1.setMessage("系统启动成功");

        SysSystemLog log2 = new SysSystemLog();
        log2.setId(2L);
        log2.setCreateTime(LocalDateTime.of(2023, 1, 20, 15, 0, 0));
        log2.setModule("数据备份");
        log2.setMessage("数据备份完成");

        List<SysSystemLog> expected = Arrays.asList(log1, log2);

        // When
        when(systemLogMapper.selectSystemLogsByTimeRange(startTime, endTime)).thenReturn(expected);

        List<SysSystemLog> result = systemLogService.listSystemLogsByTimeRange(startTime, endTime);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getCreateTime().isAfter(startTime) && result.get(0).getCreateTime().isBefore(endTime));
        assertTrue(result.get(1).getCreateTime().isAfter(startTime) && result.get(1).getCreateTime().isBefore(endTime));
    }
}