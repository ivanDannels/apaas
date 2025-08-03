package com.apaas.security.core.service;

import com.apaas.security.core.domain.SysRole;
import com.apaas.security.core.domain.SysRoleResource;
import com.apaas.security.core.domain.SysRoleDataScope;
import com.apaas.security.core.mapper.SysRoleMapper;
import com.apaas.security.core.mapper.SysRoleResourceMapper;
import com.apaas.security.core.mapper.SysRoleDataScopeMapper;
import com.apaas.security.core.mapper.SysUserRoleMapper;
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

public class RoleServiceTest {

    @Mock
    private SysRoleMapper roleMapper;

    @Mock
    private SysRoleResourceMapper roleResourceMapper;

    @Mock
    private SysRoleDataScopeMapper roleDataScopeMapper;

    @Mock
    private SysUserRoleMapper userRoleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试创建角色
    @Test
    void testCreateRole() {
        // Given
        SysRole role = new SysRole();
        role.setRoleName("测试角色");
        role.setRoleCode("TEST_ROLE");
        role.setDescription("这是一个测试角色");
        role.setStatus(1);
        role.setCreateTime(LocalDateTime.now());

        // When
        when(roleMapper.selectRoleByCode("TEST_ROLE")).thenReturn(null);
        when(roleMapper.insertRole(role)).thenReturn(1);

        boolean result = roleService.createRole(role);

        // Then
        assertTrue(result);
        verify(roleMapper, times(1)).insertRole(role);
    }

    // 测试创建角色编码已存在
    @Test
    void testCreateRoleWithExistingCode() {
        // Given
        SysRole role = new SysRole();
        role.setRoleName("测试角色");
        role.setRoleCode("EXISTING_ROLE");

        // When
        when(roleMapper.selectRoleByCode("EXISTING_ROLE")).thenReturn(new SysRole());

        boolean result = roleService.createRole(role);

        // Then
        assertFalse(result);
        verify(roleMapper, never()).insertRole(any(SysRole.class));
    }

    // 测试根据ID查询角色
    @Test
    void testGetRoleById() {
        // Given
        Long roleId = 1L;
        SysRole expected = new SysRole();
        expected.setRoleId(roleId);
        expected.setRoleName("管理员");
        expected.setRoleCode("ADMIN");

        // When
        when(roleMapper.selectRoleById(roleId)).thenReturn(expected);

        SysRole result = roleService.getRoleById(roleId);

        // Then
        assertNotNull(result);
        assertEquals(roleId, result.getRoleId());
        assertEquals("管理员", result.getRoleName());
    }

    // 测试根据编码查询角色
    @Test
    void testGetRoleByCode() {
        // Given
        String roleCode = "ADMIN";
        SysRole expected = new SysRole();
        expected.setRoleId(1L);
        expected.setRoleName("管理员");
        expected.setRoleCode(roleCode);

        // When
        when(roleMapper.selectRoleByCode(roleCode)).thenReturn(expected);

        SysRole result = roleService.getRoleByCode(roleCode);

        // Then
        assertNotNull(result);
        assertEquals(roleCode, result.getRoleCode());
        assertEquals("管理员", result.getRoleName());
    }

    // 测试查询角色列表
    @Test
    void testListRoles() {
        // Given
        SysRole role1 = new SysRole();
        role1.setRoleId(1L);
        role1.setRoleName("管理员");

        SysRole role2 = new SysRole();
        role2.setRoleId(2L);
        role2.setRoleName("普通用户");

        List<SysRole> expected = Arrays.asList(role1, role2);

        // When
        when(roleMapper.selectRoleList(any())).thenReturn(expected);

        List<SysRole> result = roleService.listRoles(new SysRole());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("管理员", result.get(0).getRoleName());
        assertEquals("普通用户", result.get(1).getRoleName());
    }

    // 测试更新角色
    @Test
    void testUpdateRole() {
        // Given
        SysRole role = new SysRole();
        role.setRoleId(1L);
        role.setRoleName("更新后的角色名");
        role.setDescription("更新后的描述");

        // When
        when(roleMapper.updateRole(role)).thenReturn(1);

        boolean result = roleService.updateRole(role);

        // Then
        assertTrue(result);
        verify(roleMapper, times(1)).updateRole(role);
    }

    // 测试删除角色
    @Test
    void testDeleteRole() {
        // Given
        Long roleId = 1L;

        // When
        // 检查角色是否被用户使用
        when(userRoleMapper.countUserByRoleId(roleId)).thenReturn(0);
        // 删除角色
        when(roleMapper.deleteRole(roleId)).thenReturn(1);
        // 级联删除角色资源关联
        when(roleResourceMapper.deleteRoleResourceByRoleId(roleId)).thenReturn(1);
        // 级联删除角色数据权限关联
        when(roleDataScopeMapper.deleteRoleDataScopeByRoleId(roleId)).thenReturn(1);

        boolean result = roleService.deleteRole(roleId);

        // Then
        assertTrue(result);
        verify(userRoleMapper, times(1)).countUserByRoleId(roleId);
        verify(roleMapper, times(1)).deleteRole(roleId);
        verify(roleResourceMapper, times(1)).deleteRoleResourceByRoleId(roleId);
        verify(roleDataScopeMapper, times(1)).deleteRoleDataScopeByRoleId(roleId);
    }

    // 测试删除被用户使用的角色
    @Test
    void testDeleteRoleInUse() {
        // Given
        Long roleId = 1L;

        // When
        // 检查角色是否被用户使用
        when(userRoleMapper.countUserByRoleId(roleId)).thenReturn(1);

        boolean result = roleService.deleteRole(roleId);

        // Then
        assertFalse(result);
        verify(userRoleMapper, times(1)).countUserByRoleId(roleId);
        verify(roleMapper, never()).deleteRole(roleId);
        verify(roleResourceMapper, never()).deleteRoleResourceByRoleId(roleId);
        verify(roleDataScopeMapper, never()).deleteRoleDataScopeByRoleId(roleId);
    }

    // 测试禁用角色
    @Test
    void testDisableRole() {
        // Given
        Long roleId = 1L;
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role.setStatus(1);

        // When
        when(roleMapper.selectRoleById(roleId)).thenReturn(role);
        when(roleMapper.updateRoleStatus(roleId, 0)).thenReturn(1);

        boolean result = roleService.disableRole(roleId);

        // Then
        assertTrue(result);
        assertEquals(0, role.getStatus());
        verify(roleMapper, times(1)).updateRoleStatus(roleId, 0);
    }

    // 测试启用角色
    @Test
    void testEnableRole() {
        // Given
        Long roleId = 1L;
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role.setStatus(0);

        // When
        when(roleMapper.selectRoleById(roleId)).thenReturn(role);
        when(roleMapper.updateRoleStatus(roleId, 1)).thenReturn(1);

        boolean result = roleService.enableRole(roleId);

        // Then
        assertTrue(result);
        assertEquals(1, role.getStatus());
        verify(roleMapper, times(1)).updateRoleStatus(roleId, 1);
    }

    // 测试批量删除角色
    @Test
    void testBatchDeleteRoles() {
        // Given
        List<Long> roleIds = Arrays.asList(1L, 2L, 3L);

        // When
        // 检查角色是否被用户使用
        when(userRoleMapper.countUserByRoleIds(roleIds)).thenReturn(0);
        // 批量删除角色
        when(roleMapper.batchDeleteRoles(roleIds)).thenReturn(3);
        // 级联删除角色资源关联
        when(roleResourceMapper.batchDeleteRoleResourcesByRoleIds(roleIds)).thenReturn(3);
        // 级联删除角色数据权限关联
        when(roleDataScopeMapper.batchDeleteRoleDataScopesByRoleIds(roleIds)).thenReturn(3);

        boolean result = roleService.batchDeleteRoles(roleIds);

        // Then
        assertTrue(result);
        verify(userRoleMapper, times(1)).countUserByRoleIds(roleIds);
        verify(roleMapper, times(1)).batchDeleteRoles(roleIds);
        verify(roleResourceMapper, times(1)).batchDeleteRoleResourcesByRoleIds(roleIds);
        verify(roleDataScopeMapper, times(1)).batchDeleteRoleDataScopesByRoleIds(roleIds);
    }

    // 测试分页查询角色
    @Test
    void testListRolesByPage() {
        // Given
        SysRole query = new SysRole();
        query.setRoleName("测试");
        int pageNum = 1;
        int pageSize = 10;

        SysRole role1 = new SysRole();
        role1.setRoleId(1L);
        role1.setRoleName("测试角色1");

        SysRole role2 = new SysRole();
        role2.setRoleId(2L);
        role2.setRoleName("测试角色2");

        List<SysRole> roleList = Arrays.asList(role1, role2);
        int total = 2;

        // When
        when(roleMapper.selectRoleCount(query)).thenReturn(total);
        when(roleMapper.selectRoleListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(roleList);

        Map<String, Object> result = roleService.listRolesByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(roleList, result.get("rows"));
        verify(roleMapper, times(1)).selectRoleCount(query);
        verify(roleMapper, times(1)).selectRoleListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试分配角色资源
    @Test
    void testAssignRoleResources() {
        // Given
        Long roleId = 1L;
        List<Long> resourceIds = Arrays.asList(1001L, 1002L, 1003L);

        // When
        // 删除原有的角色资源关联
        when(roleResourceMapper.deleteRoleResourceByRoleId(roleId)).thenReturn(3);
        // 批量插入新的角色资源关联
        when(roleResourceMapper.batchInsertRoleResources(anyList())).thenReturn(3);

        boolean result = roleService.assignRoleResources(roleId, resourceIds);

        // Then
        assertTrue(result);
        verify(roleResourceMapper, times(1)).deleteRoleResourceByRoleId(roleId);
        verify(roleResourceMapper, times(1)).batchInsertRoleResources(anyList());
    }

    // 测试查询角色资源
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

        List<Map<String, Object>> result = roleService.listRoleResources(roleId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1001L, result.get(0).get("resourceId"));
        assertEquals("菜单1", result.get(0).get("resourceName"));
    }

    // 测试分配角色数据权限
    @Test
    void testAssignRoleDataScope() {
        // Given
        Long roleId = 1L;
        Integer dataScopeType = 2; // 部门数据权限
        List<Long> organizationIds = Arrays.asList(1L, 2L);

        // When
        // 删除原有的角色数据权限关联
        when(roleDataScopeMapper.deleteRoleDataScopeByRoleId(roleId)).thenReturn(2);
        // 更新角色的数据权限类型
        when(roleMapper.updateRoleDataScopeType(roleId, dataScopeType)).thenReturn(1);
        // 批量插入新的角色数据权限关联
        when(roleDataScopeMapper.batchInsertRoleDataScopes(anyList())).thenReturn(2);

        boolean result = roleService.assignRoleDataScope(roleId, dataScopeType, organizationIds);

        // Then
        assertTrue(result);
        verify(roleDataScopeMapper, times(1)).deleteRoleDataScopeByRoleId(roleId);
        verify(roleMapper, times(1)).updateRoleDataScopeType(roleId, dataScopeType);
        verify(roleDataScopeMapper, times(1)).batchInsertRoleDataScopes(anyList());
    }

    // 测试查询角色数据权限
    @Test
    void testListRoleDataScopes() {
        // Given
        Long roleId = 1L;
        Map<String, Object> dataScope1 = new HashMap<>();
        dataScope1.put("id", 1L);
        dataScope1.put("organizationId", 1L);
        dataScope1.put("organizationName", "研发部");

        Map<String, Object> dataScope2 = new HashMap<>();
        dataScope2.put("id", 2L);
        dataScope2.put("organizationId", 2L);
        dataScope2.put("organizationName", "测试部");

        List<Map<String, Object>> expected = Arrays.asList(dataScope1, dataScope2);

        // When
        when(roleDataScopeMapper.selectRoleDataScopes(roleId)).thenReturn(expected);

        List<Map<String, Object>> result = roleService.listRoleDataScopes(roleId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).get("organizationId"));
        assertEquals("研发部", result.get(0).get("organizationName"));
    }
}