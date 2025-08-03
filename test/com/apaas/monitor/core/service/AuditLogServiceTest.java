package com.apaas.monitor.core.service;

import com.apaas.monitor.core.domain.SysAuditLog;
import com.apaas.monitor.core.mapper.SysAuditLogMapper;
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

public class AuditLogServiceTest {

    @Mock
    private SysAuditLogMapper auditLogMapper;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试记录审计日志
    @Test
    void testRecordAuditLog() {
        // Given
        SysAuditLog auditLog = new SysAuditLog();
        auditLog.setUserId(1L);
        auditLog.setUsername("testuser");
        auditLog.setOperation("登录系统");
        auditLog.setModule("系统登录");
        auditLog.setIpAddress("192.168.1.1");
        auditLog.setOperationTime(LocalDateTime.now());

        // When
        when(auditLogMapper.insertAuditLog(auditLog)).thenReturn(1);

        boolean result = auditLogService.recordAuditLog(auditLog);

        // Then
        assertTrue(result);
        verify(auditLogMapper, times(1)).insertAuditLog(auditLog);
    }

    // 测试根据ID查询审计日志
    @Test
    void testGetAuditLogById() {
        // Given
        Long logId = 1L;
        SysAuditLog expected = new SysAuditLog();
        expected.setId(logId);
        expected.setUserId(1L);
        expected.setUsername("testuser");
        expected.setOperation("登录系统");

        // When
        when(auditLogMapper.selectAuditLogById(logId)).thenReturn(expected);

        SysAuditLog result = auditLogService.getAuditLogById(logId);

        // Then
        assertNotNull(result);
        assertEquals(logId, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("登录系统", result.getOperation());
    }

    // 测试查询审计日志列表
    @Test
    void testListAuditLogs() {
        // Given
        SysAuditLog log1 = new SysAuditLog();
        log1.setId(1L);
        log1.setUserId(1L);
        log1.setUsername("testuser1");
        log1.setOperation("登录系统");

        SysAuditLog log2 = new SysAuditLog();
        log2.setId(2L);
        log2.setUserId(2L);
        log2.setUsername("testuser2");
        log2.setOperation("退出系统");

        List<SysAuditLog> expected = Arrays.asList(log1, log2);

        // When
        when(auditLogMapper.selectAuditLogList(any())).thenReturn(expected);

        List<SysAuditLog> result = auditLogService.listAuditLogs(new SysAuditLog());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("testuser1", result.get(0).getUsername());
        assertEquals("登录系统", result.get(0).getOperation());
        assertEquals("testuser2", result.get(1).getUsername());
        assertEquals("退出系统", result.get(1).getOperation());
    }

    // 测试分页查询审计日志
    @Test
    void testListAuditLogsByPage() {
        // Given
        SysAuditLog query = new SysAuditLog();
        query.setUsername("test");
        int pageNum = 1;
        int pageSize = 10;

        SysAuditLog log1 = new SysAuditLog();
        log1.setId(1L);
        log1.setUserId(1L);
        log1.setUsername("testuser1");
        log1.setOperation("登录系统");

        SysAuditLog log2 = new SysAuditLog();
        log2.setId(2L);
        log2.setUserId(2L);
        log2.setUsername("testuser2");
        log2.setOperation("退出系统");

        List<SysAuditLog> logList = Arrays.asList(log1, log2);
        int total = 2;

        // When
        when(auditLogMapper.selectAuditLogCount(query)).thenReturn(total);
        when(auditLogMapper.selectAuditLogListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(logList);

        Map<String, Object> result = auditLogService.listAuditLogsByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(logList, result.get("rows"));
        verify(auditLogMapper, times(1)).selectAuditLogCount(query);
        verify(auditLogMapper, times(1)).selectAuditLogListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试删除审计日志
    @Test
    void testDeleteAuditLog() {
        // Given
        Long logId = 1L;

        // When
        when(auditLogMapper.deleteAuditLog(logId)).thenReturn(1);

        boolean result = auditLogService.deleteAuditLog(logId);

        // Then
        assertTrue(result);
        verify(auditLogMapper, times(1)).deleteAuditLog(logId);
    }

    // 测试批量删除审计日志
    @Test
    void testBatchDeleteAuditLogs() {
        // Given
        List<Long> logIds = Arrays.asList(1L, 2L, 3L);

        // When
        when(auditLogMapper.batchDeleteAuditLogs(logIds)).thenReturn(3);

        boolean result = auditLogService.batchDeleteAuditLogs(logIds);

        // Then
        assertTrue(result);
        verify(auditLogMapper, times(1)).batchDeleteAuditLogs(logIds);
    }

    // 测试根据用户ID查询审计日志
    @Test
    void testListAuditLogsByUserId() {
        // Given
        Long userId = 1L;
        SysAuditLog log1 = new SysAuditLog();
        log1.setId(1L);
        log1.setUserId(userId);
        log1.setUsername("testuser");
        log1.setOperation("登录系统");

        SysAuditLog log2 = new SysAuditLog();
        log2.setId(2L);
        log2.setUserId(userId);
        log2.setUsername("testuser");
        log2.setOperation("查看用户列表");

        List<SysAuditLog> expected = Arrays.asList(log1, log2);

        // When
        when(auditLogMapper.selectAuditLogsByUserId(userId)).thenReturn(expected);

        List<SysAuditLog> result = auditLogService.listAuditLogsByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
    }

    // 测试根据操作模块查询审计日志
    @Test
    void testListAuditLogsByModule() {
        // Given
        String module = "用户管理";
        SysAuditLog log1 = new SysAuditLog();
        log1.setId(1L);
        log1.setModule(module);
        log1.setOperation("创建用户");

        SysAuditLog log2 = new SysAuditLog();
        log2.setId(2L);
        log2.setModule(module);
        log2.setOperation("删除用户");

        List<SysAuditLog> expected = Arrays.asList(log1, log2);

        // When
        when(auditLogMapper.selectAuditLogsByModule(module)).thenReturn(expected);

        List<SysAuditLog> result = auditLogService.listAuditLogsByModule(module);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(module, result.get(0).getModule());
        assertEquals(module, result.get(1).getModule());
    }

    // 测试根据时间范围查询审计日志
    @Test
    void testListAuditLogsByTimeRange() {
        // Given
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 1, 31, 23, 59, 59);

        SysAuditLog log1 = new SysAuditLog();
        log1.setId(1L);
        log1.setOperationTime(LocalDateTime.of(2023, 1, 10, 10, 0, 0));
        log1.setOperation("登录系统");

        SysAuditLog log2 = new SysAuditLog();
        log2.setId(2L);
        log2.setOperationTime(LocalDateTime.of(2023, 1, 20, 15, 0, 0));
        log2.setOperation("退出系统");

        List<SysAuditLog> expected = Arrays.asList(log1, log2);

        // When
        when(auditLogMapper.selectAuditLogsByTimeRange(startTime, endTime)).thenReturn(expected);

        List<SysAuditLog> result = auditLogService.listAuditLogsByTimeRange(startTime, endTime);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getOperationTime().isAfter(startTime) && result.get(0).getOperationTime().isBefore(endTime));
        assertTrue(result.get(1).getOperationTime().isAfter(startTime) && result.get(1).getOperationTime().isBefore(endTime));
    }
}