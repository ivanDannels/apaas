package com.apaas.security.core.service;

import com.apaas.security.core.domain.SysUser;
import com.apaas.security.core.domain.SysUserRole;
import com.apaas.security.core.domain.SysUserOrganization;
import com.apaas.security.core.domain.SysUserPosition;
import com.apaas.security.core.mapper.SysUserMapper;
import com.apaas.security.core.mapper.SysUserRoleMapper;
import com.apaas.security.core.mapper.SysUserOrganizationMapper;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private SysUserMapper userMapper;

    @Mock
    private SysUserRoleMapper userRoleMapper;

    @Mock
    private SysUserOrganizationMapper userOrganizationMapper;

    @Mock
    private SysUserPositionMapper userPositionMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Given
        SysUser user = new SysUser();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setPhone("13800138000");
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());

        // When
        when(userMapper.insertUser(user)).thenReturn(1);
        boolean result = userService.createUser(user);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).insertUser(user);
    }

    @Test
    void testCreateUserWithExistingUsername() {
        // Given
        SysUser user = new SysUser();
        user.setUsername("existinguser");
        user.setPassword("password123");

        // When
        when(userMapper.selectUserByUsername("existinguser")).thenReturn(new SysUser());
        boolean result = userService.createUser(user);

        // Then
        assertFalse(result);
        verify(userMapper, never()).insertUser(any(SysUser.class));
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        SysUser expected = new SysUser();
        expected.setUserId(userId);
        expected.setUsername("testuser");
        expected.setEmail("testuser@example.com");

        // When
        when(userMapper.selectUserById(userId)).thenReturn(expected);
        SysUser result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertEquals("testuser@example.com", result.getEmail());
    }

    @Test
    void testGetUserByUsername() {
        // Given
        String username = "testuser";
        SysUser expected = new SysUser();
        expected.setUserId(1L);
        expected.setUsername(username);
        expected.setEmail("testuser@example.com");

        // When
        when(userMapper.selectUserByUsername(username)).thenReturn(expected);
        SysUser result = userService.getUserByUsername(username);

        // Then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("testuser@example.com", result.getEmail());
    }

    @Test
    void testUpdateUser() {
        // Given
        SysUser user = new SysUser();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("updated@example.com");
        user.setPhone("13900139000");

        // When
        when(userMapper.updateUser(user)).thenReturn(1);
        boolean result = userService.updateUser(user);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        when(userMapper.deleteUser(userId)).thenReturn(1);
        // 级联删除用户角色关联
        when(userRoleMapper.deleteUserRoleByUserId(userId)).thenReturn(1);
        // 级联删除用户组织关联
        when(userOrganizationMapper.deleteUserOrganizationByUserId(userId)).thenReturn(1);
        // 级联删除用户岗位关联
        when(userPositionMapper.deleteUserPositionByUserId(userId)).thenReturn(1);

        boolean result = userService.deleteUser(userId);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).deleteUser(userId);
        verify(userRoleMapper, times(1)).deleteUserRoleByUserId(userId);
        verify(userOrganizationMapper, times(1)).deleteUserOrganizationByUserId(userId);
        verify(userPositionMapper, times(1)).deleteUserPositionByUserId(userId);
    }

    @Test
    void testListUsers() {
        // Given
        SysUser user1 = new SysUser();
        user1.setUserId(1L);
        user1.setUsername("user1");

        SysUser user2 = new SysUser();
        user2.setUserId(2L);
        user2.setUsername("user2");

        List<SysUser> expected = Arrays.asList(user1, user2);

        // When
        when(userMapper.selectUserList(any())).thenReturn(expected);
        List<SysUser> result = userService.listUsers(new SysUser());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testDisableUser() {
        // Given
        Long userId = 1L;
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setStatus(1);

        // When
        when(userMapper.selectUserById(userId)).thenReturn(user);
        when(userMapper.updateUserStatus(userId, 0)).thenReturn(1);
        boolean result = userService.disableUser(userId);

        // Then
        assertTrue(result);
        assertEquals(0, user.getStatus());
        verify(userMapper, times(1)).updateUserStatus(userId, 0);
    }

    @Test
    void testEnableUser() {
        // Given
        Long userId = 1L;
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setStatus(0);

        // When
        when(userMapper.selectUserById(userId)).thenReturn(user);
        when(userMapper.updateUserStatus(userId, 1)).thenReturn(1);
        boolean result = userService.enableUser(userId);

        // Then
        assertTrue(result);
        assertEquals(1, user.getStatus());
        verify(userMapper, times(1)).updateUserStatus(userId, 1);
    }

    @Test
    void testResetPassword() {
        // Given
        Long userId = 1L;
        String newPassword = "newpassword123";

        // When
        when(userMapper.updatePassword(userId, newPassword)).thenReturn(1);
        boolean result = userService.resetPassword(userId, newPassword);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).updatePassword(userId, newPassword);
    }

    @Test
    void testUpdateUserProfile() {
        // Given
        Long userId = 1L;
        Map<String, Object> profileInfo = new HashMap<>();
        profileInfo.put("email", "newemail@example.com");
        profileInfo.put("phone", "13700137000");
        profileInfo.put("realName", "张三");

        // When
        when(userMapper.updateUserProfile(userId, profileInfo)).thenReturn(1);
        boolean result = userService.updateUserProfile(userId, profileInfo);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).updateUserProfile(userId, profileInfo);
    }

    @Test
    void testAddUserRole() {
        // Given
        Long userId = 1L;
        Long roleId = 2L;
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        // When
        when(userRoleMapper.insertUserRole(userRole)).thenReturn(1);
        boolean result = userService.addUserRole(userId, roleId);

        // Then
        assertTrue(result);
        verify(userRoleMapper, times(1)).insertUserRole(userRole);
    }

    @Test
    void testRemoveUserRole() {
        // Given
        Long userId = 1L;
        Long roleId = 2L;

        // When
        when(userRoleMapper.deleteUserRole(userId, roleId)).thenReturn(1);
        boolean result = userService.removeUserRole(userId, roleId);

        // Then
        assertTrue(result);
        verify(userRoleMapper, times(1)).deleteUserRole(userId, roleId);
    }

    @Test
    void testListUserRoles() {
        // Given
        Long userId = 1L;
        Map<String, Object> role1 = new HashMap<>();
        role1.put("roleId", 1L);
        role1.put("roleName", "管理员");

        Map<String, Object> role2 = new HashMap<>();
        role2.put("roleId", 2L);
        role2.put("roleName", "普通用户");

        List<Map<String, Object>> expected = Arrays.asList(role1, role2);

        // When
        when(userRoleMapper.selectUserRoles(userId)).thenReturn(expected);
        List<Map<String, Object>> result = userService.listUserRoles(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("管理员", result.get(0).get("roleName"));
        assertEquals("普通用户", result.get(1).get("roleName"));
    }

    @Test
    void testAddUserToOrganization() {
        // Given
        Long userId = 1L;
        Long organizationId = 3L;
        SysUserOrganization userOrg = new SysUserOrganization();
        userOrg.setUserId(userId);
        userOrg.setOrganizationId(organizationId);
        userOrg.setMainOrg(1); // 主组织

        // When
        when(userOrganizationMapper.insertUserOrganization(userOrg)).thenReturn(1);
        boolean result = userService.addUserToOrganization(userId, organizationId, true);

        // Then
        assertTrue(result);
        verify(userOrganizationMapper, times(1)).insertUserOrganization(userOrg);
    }

    @Test
    void testRemoveUserFromOrganization() {
        // Given
        Long userId = 1L;
        Long organizationId = 3L;

        // When
        when(userOrganizationMapper.deleteUserOrganization(userId, organizationId)).thenReturn(1);
        boolean result = userService.removeUserFromOrganization(userId, organizationId);

        // Then
        assertTrue(result);
        verify(userOrganizationMapper, times(1)).deleteUserOrganization(userId, organizationId);
    }

    @Test
    void testAddUserPosition() {
        // Given
        Long userId = 1L;
        Long positionId = 4L;
        SysUserPosition userPos = new SysUserPosition();
        userPos.setUserId(userId);
        userPos.setPositionId(positionId);

        // When
        when(userPositionMapper.insertUserPosition(userPos)).thenReturn(1);
        boolean result = userService.addUserPosition(userId, positionId);

        // Then
        assertTrue(result);
        verify(userPositionMapper, times(1)).insertUserPosition(userPos);
    }

    @Test
    void testRemoveUserPosition() {
        // Given
        Long userId = 1L;
        Long positionId = 4L;

        // When
        when(userPositionMapper.deleteUserPosition(userId, positionId)).thenReturn(1);
        boolean result = userService.removeUserPosition(userId, positionId);

        // Then
        assertTrue(result);
        verify(userPositionMapper, times(1)).deleteUserPosition(userId, positionId);
    }

    // 测试查询用户组织列表
    @Test
    void testListUserOrganizations() {
        // Given
        Long userId = 1L;
        Map<String, Object> org1 = new HashMap<>();
        org1.put("organizationId", 1L);
        org1.put("organizationName", "研发部");
        org1.put("mainOrg", 1);

        Map<String, Object> org2 = new HashMap<>();
        org2.put("organizationId", 2L);
        org2.put("organizationName", "测试部");
        org2.put("mainOrg", 0);

        List<Map<String, Object>> expected = Arrays.asList(org1, org2);

        // When
        when(userOrganizationMapper.selectUserOrganizations(userId)).thenReturn(expected);
        List<Map<String, Object>> result = userService.listUserOrganizations(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("研发部", result.get(0).get("organizationName"));
        assertEquals(1, result.get(0).get("mainOrg"));
        assertEquals("测试部", result.get(1).get("organizationName"));
        assertEquals(0, result.get(1).get("mainOrg"));
    }

    // 测试查询用户岗位列表
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
        List<Map<String, Object>> result = userService.listUserPositions(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("高级工程师", result.get(0).get("positionName"));
        assertEquals("架构师", result.get(1).get("positionName"));
    }

    // 测试批量删除用户
    @Test
    void testBatchDeleteUsers() {
        // Given
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);

        // When
        when(userMapper.batchDeleteUsers(userIds)).thenReturn(3);
        // 级联删除用户角色关联
        when(userRoleMapper.batchDeleteUserRolesByUserIds(userIds)).thenReturn(3);
        // 级联删除用户组织关联
        when(userOrganizationMapper.batchDeleteUserOrganizationsByUserIds(userIds)).thenReturn(3);
        // 级联删除用户岗位关联
        when(userPositionMapper.batchDeleteUserPositionsByUserIds(userIds)).thenReturn(3);

        boolean result = userService.batchDeleteUsers(userIds);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).batchDeleteUsers(userIds);
        verify(userRoleMapper, times(1)).batchDeleteUserRolesByUserIds(userIds);
        verify(userOrganizationMapper, times(1)).batchDeleteUserOrganizationsByUserIds(userIds);
        verify(userPositionMapper, times(1)).batchDeleteUserPositionsByUserIds(userIds);
    }

    // 测试分页查询用户
    @Test
    void testListUsersByPage() {
        // Given
        SysUser query = new SysUser();
        query.setUsername("test");
        int pageNum = 1;
        int pageSize = 10;

        SysUser user1 = new SysUser();
        user1.setUserId(1L);
        user1.setUsername("testuser1");

        SysUser user2 = new SysUser();
        user2.setUserId(2L);
        user2.setUsername("testuser2");

        List<SysUser> userList = Arrays.asList(user1, user2);
        int total = 2;

        // When
        when(userMapper.selectUserCount(query)).thenReturn(total);
        when(userMapper.selectUserListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(userList);

        Map<String, Object> result = userService.listUsersByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(userList, result.get("rows"));
        verify(userMapper, times(1)).selectUserCount(query);
        verify(userMapper, times(1)).selectUserListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }
}