package com.apaas.security.core.service;

import com.apaas.security.core.domain.SysResource;
import com.apaas.security.core.domain.SysRoleResource;
import com.apaas.security.core.mapper.SysResourceMapper;
import com.apaas.security.core.mapper.SysRoleResourceMapper;
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

public class ResourceServiceTest {

    @Mock
    private SysResourceMapper resourceMapper;

    @Mock
    private SysRoleResourceMapper roleResourceMapper;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试创建资源
    @Test
    void testCreateResource() {
        // Given
        SysResource resource = new SysResource();
        resource.setResourceName("测试菜单");
        resource.setResourceCode("TEST_MENU");
        resource.setParentId(0L);
        resource.setResourceType(1); // 菜单
        resource.setSort(1);
        resource.setStatus(1);
        resource.setCreateTime(LocalDateTime.now());

        // When
        when(resourceMapper.selectResourceByCode("TEST_MENU")).thenReturn(null);
        when(resourceMapper.insertResource(resource)).thenReturn(1);

        boolean result = resourceService.createResource(resource);

        // Then
        assertTrue(result);
        verify(resourceMapper, times(1)).insertResource(resource);
    }

    // 测试创建资源编码已存在
    @Test
    void testCreateResourceWithExistingCode() {
        // Given
        SysResource resource = new SysResource();
        resource.setResourceName("测试菜单");
        resource.setResourceCode("EXISTING_MENU");

        // When
        when(resourceMapper.selectResourceByCode("EXISTING_MENU")).thenReturn(new SysResource());

        boolean result = resourceService.createResource(resource);

        // Then
        assertFalse(result);
        verify(resourceMapper, never()).insertResource(any(SysResource.class));
    }

    // 测试根据ID查询资源
    @Test
    void testGetResourceById() {
        // Given
        Long resourceId = 1L;
        SysResource expected = new SysResource();
        expected.setResourceId(resourceId);
        expected.setResourceName("首页");
        expected.setResourceCode("HOME_PAGE");

        // When
        when(resourceMapper.selectResourceById(resourceId)).thenReturn(expected);

        SysResource result = resourceService.getResourceById(resourceId);

        // Then
        assertNotNull(result);
        assertEquals(resourceId, result.getResourceId());
        assertEquals("首页", result.getResourceName());
    }

    // 测试根据编码查询资源
    @Test
    void testGetResourceByCode() {
        // Given
        String resourceCode = "HOME_PAGE";
        SysResource expected = new SysResource();
        expected.setResourceId(1L);
        expected.setResourceName("首页");
        expected.setResourceCode(resourceCode);

        // When
        when(resourceMapper.selectResourceByCode(resourceCode)).thenReturn(expected);

        SysResource result = resourceService.getResourceByCode(resourceCode);

        // Then
        assertNotNull(result);
        assertEquals(resourceCode, result.getResourceCode());
        assertEquals("首页", result.getResourceName());
    }

    // 测试查询资源列表
    @Test
    void testListResources() {
        // Given
        SysResource resource1 = new SysResource();
        resource1.setResourceId(1L);
        resource1.setResourceName("首页");

        SysResource resource2 = new SysResource();
        resource2.setResourceId(2L);
        resource2.setResourceName("用户管理");

        List<SysResource> expected = Arrays.asList(resource1, resource2);

        // When
        when(resourceMapper.selectResourceList(any())).thenReturn(expected);

        List<SysResource> result = resourceService.listResources(new SysResource());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("首页", result.get(0).getResourceName());
        assertEquals("用户管理", result.get(1).getResourceName());
    }

    // 测试获取资源树
    @Test
    void testListResourceTree() {
        // Given
        Map<String, Object> node1 = new HashMap<>();
        node1.put("resourceId", 1L);
        node1.put("resourceName", "系统管理");
        node1.put("parentId", 0L);

        Map<String, Object> node2 = new HashMap<>();
        node2.put("resourceId", 2L);
        node2.put("resourceName", "用户管理");
        node2.put("parentId", 1L);

        Map<String, Object> node3 = new HashMap<>();
        node3.put("resourceId", 3L);
        node3.put("resourceName", "角色管理");
        node3.put("parentId", 1L);

        List<Map<String, Object>> expected = Arrays.asList(node1, node2, node3);

        // When
        when(resourceMapper.selectResourceTree()).thenReturn(expected);

        List<Map<String, Object>> result = resourceService.listResourceTree();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("系统管理", result.get(0).get("resourceName"));
        assertEquals("用户管理", result.get(1).get("resourceName"));
        assertEquals("角色管理", result.get(2).get("resourceName"));
    }

    // 测试更新资源
    @Test
    void testUpdateResource() {
        // Given
        SysResource resource = new SysResource();
        resource.setResourceId(1L);
        resource.setResourceName("更新后的资源名");
        resource.setDescription("更新后的描述");

        // When
        when(resourceMapper.updateResource(resource)).thenReturn(1);

        boolean result = resourceService.updateResource(resource);

        // Then
        assertTrue(result);
        verify(resourceMapper, times(1)).updateResource(resource);
    }

    // 测试删除资源
    @Test
    void testDeleteResource() {
        // Given
        Long resourceId = 1L;

        // When
        // 检查是否有子资源
        when(resourceMapper.countChildrenByParentId(resourceId)).thenReturn(0);
        // 检查是否有角色关联
        when(roleResourceMapper.countRoleByResourceId(resourceId)).thenReturn(0);
        // 删除资源
        when(resourceMapper.deleteResource(resourceId)).thenReturn(1);

        boolean result = resourceService.deleteResource(resourceId);

        // Then
        assertTrue(result);
        verify(resourceMapper, times(1)).countChildrenByParentId(resourceId);
        verify(roleResourceMapper, times(1)).countRoleByResourceId(resourceId);
        verify(resourceMapper, times(1)).deleteResource(resourceId);
    }

    // 测试删除有子资源的资源
    @Test
    void testDeleteResourceWithChildren() {
        // Given
        Long resourceId = 1L;

        // When
        // 检查是否有子资源
        when(resourceMapper.countChildrenByParentId(resourceId)).thenReturn(1);

        boolean result = resourceService.deleteResource(resourceId);

        // Then
        assertFalse(result);
        verify(resourceMapper, times(1)).countChildrenByParentId(resourceId);
        verify(resourceMapper, never()).deleteResource(resourceId);
    }

    // 测试删除有关联角色的资源
    @Test
    void testDeleteResourceWithRoles() {
        // Given
        Long resourceId = 1L;

        // When
        // 检查是否有子资源
        when(resourceMapper.countChildrenByParentId(resourceId)).thenReturn(0);
        // 检查是否有角色关联
        when(roleResourceMapper.countRoleByResourceId(resourceId)).thenReturn(1);

        boolean result = resourceService.deleteResource(resourceId);

        // Then
        assertFalse(result);
        verify(resourceMapper, times(1)).countChildrenByParentId(resourceId);
        verify(roleResourceMapper, times(1)).countRoleByResourceId(resourceId);
        verify(resourceMapper, never()).deleteResource(resourceId);
    }

    // 测试禁用资源
    @Test
    void testDisableResource() {
        // Given
        Long resourceId = 1L;
        SysResource resource = new SysResource();
        resource.setResourceId(resourceId);
        resource.setStatus(1);

        // When
        when(resourceMapper.selectResourceById(resourceId)).thenReturn(resource);
        when(resourceMapper.updateResourceStatus(resourceId, 0)).thenReturn(1);

        boolean result = resourceService.disableResource(resourceId);

        // Then
        assertTrue(result);
        assertEquals(0, resource.getStatus());
        verify(resourceMapper, times(1)).updateResourceStatus(resourceId, 0);
    }

    // 测试启用资源
    @Test
    void testEnableResource() {
        // Given
        Long resourceId = 1L;
        SysResource resource = new SysResource();
        resource.setResourceId(resourceId);
        resource.setStatus(0);

        // When
        when(resourceMapper.selectResourceById(resourceId)).thenReturn(resource);
        when(resourceMapper.updateResourceStatus(resourceId, 1)).thenReturn(1);

        boolean result = resourceService.enableResource(resourceId);

        // Then
        assertTrue(result);
        assertEquals(1, resource.getStatus());
        verify(resourceMapper, times(1)).updateResourceStatus(resourceId, 1);
    }

    // 测试批量删除资源
    @Test
    void testBatchDeleteResources() {
        // Given
        List<Long> resourceIds = Arrays.asList(1L, 2L, 3L);

        // When
        // 检查是否有子资源
        when(resourceMapper.countChildrenByParentIds(resourceIds)).thenReturn(0);
        // 检查是否有角色关联
        when(roleResourceMapper.countRoleByResourceIds(resourceIds)).thenReturn(0);
        // 批量删除资源
        when(resourceMapper.batchDeleteResources(resourceIds)).thenReturn(3);

        boolean result = resourceService.batchDeleteResources(resourceIds);

        // Then
        assertTrue(result);
        verify(resourceMapper, times(1)).countChildrenByParentIds(resourceIds);
        verify(roleResourceMapper, times(1)).countRoleByResourceIds(resourceIds);
        verify(resourceMapper, times(1)).batchDeleteResources(resourceIds);
    }

    // 测试分页查询资源
    @Test
    void testListResourcesByPage() {
        // Given
        SysResource query = new SysResource();
        query.setResourceName("测试");
        int pageNum = 1;
        int pageSize = 10;

        SysResource resource1 = new SysResource();
        resource1.setResourceId(1L);
        resource1.setResourceName("测试资源1");

        SysResource resource2 = new SysResource();
        resource2.setResourceId(2L);
        resource2.setResourceName("测试资源2");

        List<SysResource> resourceList = Arrays.asList(resource1, resource2);
        int total = 2;

        // When
        when(resourceMapper.selectResourceCount(query)).thenReturn(total);
        when(resourceMapper.selectResourceListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(resourceList);

        Map<String, Object> result = resourceService.listResourcesByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(resourceList, result.get("rows"));
        verify(resourceMapper, times(1)).selectResourceCount(query);
        verify(resourceMapper, times(1)).selectResourceListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试查询角色拥有的资源
    @Test
    void testListRoleResources() {
        // Given
        Long roleId = 1L;
        Map<String, Object> resource1 = new HashMap<>();
        resource1.put("resourceId", 1001L);
        resource1.put("resourceName", "菜单1");

        Map<String, Object> resource2 = new HashMap<>();
        resource2.put("resourceId", 1002L);
        resource2.put("resourceName", "菜单2");

        List<Map<String, Object>> expected = Arrays.asList(resource1, resource2);

        // When
        when(roleResourceMapper.selectRoleResources(roleId)).thenReturn(expected);

        List<Map<String, Object>> result = resourceService.listRoleResources(roleId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1001L, result.get(0).get("resourceId"));
        assertEquals("菜单1", result.get(0).get("resourceName"));
    }

    // 测试查询用户拥有的资源
    @Test
    void testListUserResources() {
        // Given
        Long userId = 1L;
        Map<String, Object> resource1 = new HashMap<>();
        resource1.put("resourceId", 1001L);
        resource1.put("resourceName", "菜单1");

        Map<String, Object> resource2 = new HashMap<>();
        resource2.put("resourceId", 1002L);
        resource2.put("resourceName", "菜单2");

        List<Map<String, Object>> expected = Arrays.asList(resource1, resource2);

        // When
        when(resourceMapper.selectUserResources(userId)).thenReturn(expected);

        List<Map<String, Object>> result = resourceService.listUserResources(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1001L, result.get(0).get("resourceId"));
        assertEquals("菜单1", result.get(0).get("resourceName"));
    }
}