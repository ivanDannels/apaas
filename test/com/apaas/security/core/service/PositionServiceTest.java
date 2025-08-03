package com.apaas.security.core.service;

import com.apaas.security.core.domain.SysPosition;
import com.apaas.security.core.domain.SysUserPosition;
import com.apaas.security.core.mapper.SysPositionMapper;
import com.apaas.security.core.mapper.SysUserPositionMapper;
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

public class PositionServiceTest {

    @Mock
    private SysPositionMapper positionMapper;

    @Mock
    private SysUserPositionMapper userPositionMapper;

    @InjectMocks
    private PositionServiceImpl positionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试创建岗位
    @Test
    void testCreatePosition() {
        // Given
        SysPosition position = new SysPosition();
        position.setPositionName("测试岗位");
        position.setPositionCode("TEST_POS");
        position.setSort(1);
        position.setStatus(1);
        position.setCreateTime(LocalDateTime.now());

        // When
        when(positionMapper.selectPositionByCode("TEST_POS")).thenReturn(null);
        when(positionMapper.insertPosition(position)).thenReturn(1);

        boolean result = positionService.createPosition(position);

        // Then
        assertTrue(result);
        verify(positionMapper, times(1)).insertPosition(position);
    }

    // 测试创建岗位编码已存在
    @Test
    void testCreatePositionWithExistingCode() {
        // Given
        SysPosition position = new SysPosition();
        position.setPositionName("测试岗位");
        position.setPositionCode("EXISTING_POS");

        // When
        when(positionMapper.selectPositionByCode("EXISTING_POS")).thenReturn(new SysPosition());

        boolean result = positionService.createPosition(position);

        // Then
        assertFalse(result);
        verify(positionMapper, never()).insertPosition(any(SysPosition.class));
    }

    // 测试根据ID查询岗位
    @Test
    void testGetPositionById() {
        // Given
        Long posId = 1L;
        SysPosition expected = new SysPosition();
        expected.setPositionId(posId);
        expected.setPositionName("高级工程师");
        expected.setPositionCode("SENIOR_ENGINEER");

        // When
        when(positionMapper.selectPositionById(posId)).thenReturn(expected);

        SysPosition result = positionService.getPositionById(posId);

        // Then
        assertNotNull(result);
        assertEquals(posId, result.getPositionId());
        assertEquals("高级工程师", result.getPositionName());
    }

    // 测试根据编码查询岗位
    @Test
    void testGetPositionByCode() {
        // Given
        String posCode = "SENIOR_ENGINEER";
        SysPosition expected = new SysPosition();
        expected.setPositionId(1L);
        expected.setPositionName("高级工程师");
        expected.setPositionCode(posCode);

        // When
        when(positionMapper.selectPositionByCode(posCode)).thenReturn(expected);

        SysPosition result = positionService.getPositionByCode(posCode);

        // Then
        assertNotNull(result);
        assertEquals(posCode, result.getPositionCode());
        assertEquals("高级工程师", result.getPositionName());
    }

    // 测试查询岗位列表
    @Test
    void testListPositions() {
        // Given
        SysPosition pos1 = new SysPosition();
        pos1.setPositionId(1L);
        pos1.setPositionName("高级工程师");

        SysPosition pos2 = new SysPosition();
        pos2.setPositionId(2L);
        pos2.setPositionName("架构师");

        List<SysPosition> expected = Arrays.asList(pos1, pos2);

        // When
        when(positionMapper.selectPositionList(any())).thenReturn(expected);

        List<SysPosition> result = positionService.listPositions(new SysPosition());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("高级工程师", result.get(0).getPositionName());
        assertEquals("架构师", result.get(1).getPositionName());
    }

    // 测试更新岗位
    @Test
    void testUpdatePosition() {
        // Given
        SysPosition position = new SysPosition();
        position.setPositionId(1L);
        position.setPositionName("更新后的岗位名");
        position.setDescription("更新后的描述");

        // When
        when(positionMapper.updatePosition(position)).thenReturn(1);

        boolean result = positionService.updatePosition(position);

        // Then
        assertTrue(result);
        verify(positionMapper, times(1)).updatePosition(position);
    }

    // 测试删除岗位
    @Test
    void testDeletePosition() {
        // Given
        Long posId = 1L;

        // When
        // 检查是否有用户关联
        when(userPositionMapper.countUserByPositionId(posId)).thenReturn(0);
        // 删除岗位
        when(positionMapper.deletePosition(posId)).thenReturn(1);

        boolean result = positionService.deletePosition(posId);

        // Then
        assertTrue(result);
        verify(userPositionMapper, times(1)).countUserByPositionId(posId);
        verify(positionMapper, times(1)).deletePosition(posId);
    }

    // 测试删除有关联用户的岗位
    @Test
    void testDeletePositionWithUsers() {
        // Given
        Long posId = 1L;

        // When
        // 检查是否有用户关联
        when(userPositionMapper.countUserByPositionId(posId)).thenReturn(1);

        boolean result = positionService.deletePosition(posId);

        // Then
        assertFalse(result);
        verify(userPositionMapper, times(1)).countUserByPositionId(posId);
        verify(positionMapper, never()).deletePosition(posId);
    }

    // 测试禁用岗位
    @Test
    void testDisablePosition() {
        // Given
        Long posId = 1L;
        SysPosition position = new SysPosition();
        position.setPositionId(posId);
        position.setStatus(1);

        // When
        when(positionMapper.selectPositionById(posId)).thenReturn(position);
        when(positionMapper.updatePositionStatus(posId, 0)).thenReturn(1);

        boolean result = positionService.disablePosition(posId);

        // Then
        assertTrue(result);
        assertEquals(0, position.getStatus());
        verify(positionMapper, times(1)).updatePositionStatus(posId, 0);
    }

    // 测试启用岗位
    @Test
    void testEnablePosition() {
        // Given
        Long posId = 1L;
        SysPosition position = new SysPosition();
        position.setPositionId(posId);
        position.setStatus(0);

        // When
        when(positionMapper.selectPositionById(posId)).thenReturn(position);
        when(positionMapper.updatePositionStatus(posId, 1)).thenReturn(1);

        boolean result = positionService.enablePosition(posId);

        // Then
        assertTrue(result);
        assertEquals(1, position.getStatus());
        verify(positionMapper, times(1)).updatePositionStatus(posId, 1);
    }

    // 测试批量删除岗位
    @Test
    void testBatchDeletePositions() {
        // Given
        List<Long> posIds = Arrays.asList(1L, 2L, 3L);

        // When
        // 检查是否有用户关联
        when(userPositionMapper.countUserByPositionIds(posIds)).thenReturn(0);
        // 批量删除岗位
        when(positionMapper.batchDeletePositions(posIds)).thenReturn(3);

        boolean result = positionService.batchDeletePositions(posIds);

        // Then
        assertTrue(result);
        verify(userPositionMapper, times(1)).countUserByPositionIds(posIds);
        verify(positionMapper, times(1)).batchDeletePositions(posIds);
    }

    // 测试分页查询岗位
    @Test
    void testListPositionsByPage() {
        // Given
        SysPosition query = new SysPosition();
        query.setPositionName("测试");
        int pageNum = 1;
        int pageSize = 10;

        SysPosition pos1 = new SysPosition();
        pos1.setPositionId(1L);
        pos1.setPositionName("测试岗位1");

        SysPosition pos2 = new SysPosition();
        pos2.setPositionId(2L);
        pos2.setPositionName("测试岗位2");

        List<SysPosition> posList = Arrays.asList(pos1, pos2);
        int total = 2;

        // When
        when(positionMapper.selectPositionCount(query)).thenReturn(total);
        when(positionMapper.selectPositionListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(posList);

        Map<String, Object> result = positionService.listPositionsByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(posList, result.get("rows"));
        verify(positionMapper, times(1)).selectPositionCount(query);
        verify(positionMapper, times(1)).selectPositionListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试查询用户所属岗位
    @Test
    void testListUserPositions() {
        // Given
        Long userId = 1L;
        Map<String, Object> pos1 = new HashMap<>();
        pos1.put("positionId", 1L);
        pos1.put("positionName", "高级工程师");

        Map<String, Object> pos2 = new HashMap<>();
        pos2.put("positionId", 2L);
        pos2.put("positionName", "架构师");

        List<Map<String, Object>> expected = Arrays.asList(pos1, pos2);

        // When
        when(userPositionMapper.selectUserPositions(userId)).thenReturn(expected);

        List<Map<String, Object>> result = positionService.listUserPositions(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("高级工程师", result.get(0).get("positionName"));
        assertEquals("架构师", result.get(1).get("positionName"));
    }
}