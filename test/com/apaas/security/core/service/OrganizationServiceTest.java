package com.apaas.security.core.service;

import com.apaas.security.core.domain.SysOrganization;
import com.apaas.security.core.domain.SysUserOrganization;
import com.apaas.security.core.mapper.SysOrganizationMapper;
import com.apaas.security.core.mapper.SysUserOrganizationMapper;
import com.apaas.security.core.mapper.SysRoleDataScopeMapper;
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

public class OrganizationServiceTest {

    @Mock
    private SysOrganizationMapper organizationMapper;

    @Mock
    private SysUserOrganizationMapper userOrganizationMapper;

    @Mock
    private SysRoleDataScopeMapper roleDataScopeMapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试创建组织机构
    @Test
    void testCreateOrganization() {
        // Given
        SysOrganization organization = new SysOrganization();
        organization.setOrgName("测试部门");
        organization.setParentId(0L);
        organization.setOrgCode("TEST_DEPT");
        organization.setSort(1);
        organization.setStatus(1);
        organization.setCreateTime(LocalDateTime.now());

        // When
        when(organizationMapper.selectOrganizationByCode("TEST_DEPT")).thenReturn(null);
        when(organizationMapper.insertOrganization(organization)).thenReturn(1);

        boolean result = organizationService.createOrganization(organization);

        // Then
        assertTrue(result);
        verify(organizationMapper, times(1)).insertOrganization(organization);
    }

    // 测试创建组织机构编码已存在
    @Test
    void testCreateOrganizationWithExistingCode() {
        // Given
        SysOrganization organization = new SysOrganization();
        organization.setOrgName("测试部门");
        organization.setOrgCode("EXISTING_DEPT");

        // When
        when(organizationMapper.selectOrganizationByCode("EXISTING_DEPT")).thenReturn(new SysOrganization());

        boolean result = organizationService.createOrganization(organization);

        // Then
        assertFalse(result);
        verify(organizationMapper, never()).insertOrganization(any(SysOrganization.class));
    }

    // 测试根据ID查询组织机构
    @Test
    void testGetOrganizationById() {
        // Given
        Long orgId = 1L;
        SysOrganization expected = new SysOrganization();
        expected.setOrgId(orgId);
        expected.setOrgName("研发部");
        expected.setOrgCode("R&D_DEPT");

        // When
        when(organizationMapper.selectOrganizationById(orgId)).thenReturn(expected);

        SysOrganization result = organizationService.getOrganizationById(orgId);

        // Then
        assertNotNull(result);
        assertEquals(orgId, result.getOrgId());
        assertEquals("研发部", result.getOrgName());
    }

    // 测试根据编码查询组织机构
    @Test
    void testGetOrganizationByCode() {
        // Given
        String orgCode = "R&D_DEPT";
        SysOrganization expected = new SysOrganization();
        expected.setOrgId(1L);
        expected.setOrgName("研发部");
        expected.setOrgCode(orgCode);

        // When
        when(organizationMapper.selectOrganizationByCode(orgCode)).thenReturn(expected);

        SysOrganization result = organizationService.getOrganizationByCode(orgCode);

        // Then
        assertNotNull(result);
        assertEquals(orgCode, result.getOrgCode());
        assertEquals("研发部", result.getOrgName());
    }

    // 测试查询组织机构列表
    @Test
    void testListOrganizations() {
        // Given
        SysOrganization org1 = new SysOrganization();
        org1.setOrgId(1L);
        org1.setOrgName("研发部");

        SysOrganization org2 = new SysOrganization();
        org2.setOrgId(2L);
        org2.setOrgName("测试部");

        List<SysOrganization> expected = Arrays.asList(org1, org2);

        // When
        when(organizationMapper.selectOrganizationList(any())).thenReturn(expected);

        List<SysOrganization> result = organizationService.listOrganizations(new SysOrganization());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("研发部", result.get(0).getOrgName());
        assertEquals("测试部", result.get(1).getOrgName());
    }

    // 测试获取组织机构树
    @Test
    void testListOrganizationTree() {
        // Given
        Map<String, Object> node1 = new HashMap<>();
        node1.put("orgId", 1L);
        node1.put("orgName", "研发中心");
        node1.put("parentId", 0L);

        Map<String, Object> node2 = new HashMap<>();
        node2.put("orgId", 2L);
        node2.put("orgName", "前端组");
        node2.put("parentId", 1L);

        Map<String, Object> node3 = new HashMap<>();
        node3.put("orgId", 3L);
        node3.put("orgName", "后端组");
        node3.put("parentId", 1L);

        List<Map<String, Object>> expected = Arrays.asList(node1, node2, node3);

        // When
        when(organizationMapper.selectOrganizationTree()).thenReturn(expected);

        List<Map<String, Object>> result = organizationService.listOrganizationTree();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("研发中心", result.get(0).get("orgName"));
        assertEquals("前端组", result.get(1).get("orgName"));
        assertEquals("后端组", result.get(2).get("orgName"));
    }

    // 测试更新组织机构
    @Test
    void testUpdateOrganization() {
        // Given
        SysOrganization organization = new SysOrganization();
        organization.setOrgId(1L);
        organization.setOrgName("更新后的部门名");
        organization.setDescription("更新后的描述");

        // When
        when(organizationMapper.updateOrganization(organization)).thenReturn(1);

        boolean result = organizationService.updateOrganization(organization);

        // Then
        assertTrue(result);
        verify(organizationMapper, times(1)).updateOrganization(organization);
    }

    // 测试删除组织机构
    @Test
    void testDeleteOrganization() {
        // Given
        Long orgId = 1L;

        // When
        // 检查是否有子机构
        when(organizationMapper.countChildrenByParentId(orgId)).thenReturn(0);
        // 检查是否有用户关联
        when(userOrganizationMapper.countUserByOrganizationId(orgId)).thenReturn(0);
        // 检查是否有角色数据权限关联
        when(roleDataScopeMapper.countRoleDataScopeByOrganizationId(orgId)).thenReturn(0);
        // 删除组织机构
        when(organizationMapper.deleteOrganization(orgId)).thenReturn(1);

        boolean result = organizationService.deleteOrganization(orgId);

        // Then
        assertTrue(result);
        verify(organizationMapper, times(1)).countChildrenByParentId(orgId);
        verify(userOrganizationMapper, times(1)).countUserByOrganizationId(orgId);
        verify(roleDataScopeMapper, times(1)).countRoleDataScopeByOrganizationId(orgId);
        verify(organizationMapper, times(1)).deleteOrganization(orgId);
    }

    // 测试删除有子机构的组织机构
    @Test
    void testDeleteOrganizationWithChildren() {
        // Given
        Long orgId = 1L;

        // When
        // 检查是否有子机构
        when(organizationMapper.countChildrenByParentId(orgId)).thenReturn(1);

        boolean result = organizationService.deleteOrganization(orgId);

        // Then
        assertFalse(result);
        verify(organizationMapper, times(1)).countChildrenByParentId(orgId);
        verify(organizationMapper, never()).deleteOrganization(orgId);
    }

    // 测试删除有关联用户的组织机构
    @Test
    void testDeleteOrganizationWithUsers() {
        // Given
        Long orgId = 1L;

        // When
        // 检查是否有子机构
        when(organizationMapper.countChildrenByParentId(orgId)).thenReturn(0);
        // 检查是否有用户关联
        when(userOrganizationMapper.countUserByOrganizationId(orgId)).thenReturn(1);

        boolean result = organizationService.deleteOrganization(orgId);

        // Then
        assertFalse(result);
        verify(organizationMapper, times(1)).countChildrenByParentId(orgId);
        verify(userOrganizationMapper, times(1)).countUserByOrganizationId(orgId);
        verify(organizationMapper, never()).deleteOrganization(orgId);
    }

    // 测试禁用组织机构
    @Test
    void testDisableOrganization() {
        // Given
        Long orgId = 1L;
        SysOrganization organization = new SysOrganization();
        organization.setOrgId(orgId);
        organization.setStatus(1);

        // When
        when(organizationMapper.selectOrganizationById(orgId)).thenReturn(organization);
        when(organizationMapper.updateOrganizationStatus(orgId, 0)).thenReturn(1);

        boolean result = organizationService.disableOrganization(orgId);

        // Then
        assertTrue(result);
        assertEquals(0, organization.getStatus());
        verify(organizationMapper, times(1)).updateOrganizationStatus(orgId, 0);
    }

    // 测试启用组织机构
    @Test
    void testEnableOrganization() {
        // Given
        Long orgId = 1L;
        SysOrganization organization = new SysOrganization();
        organization.setOrgId(orgId);
        organization.setStatus(0);

        // When
        when(organizationMapper.selectOrganizationById(orgId)).thenReturn(organization);
        when(organizationMapper.updateOrganizationStatus(orgId, 1)).thenReturn(1);

        boolean result = organizationService.enableOrganization(orgId);

        // Then
        assertTrue(result);
        assertEquals(1, organization.getStatus());
        verify(organizationMapper, times(1)).updateOrganizationStatus(orgId, 1);
    }

    // 测试分页查询组织机构
    @Test
    void testListOrganizationsByPage() {
        // Given
        SysOrganization query = new SysOrganization();
        query.setOrgName("测试");
        int pageNum = 1;
        int pageSize = 10;

        SysOrganization org1 = new SysOrganization();
        org1.setOrgId(1L);
        org1.setOrgName("测试部门1");

        SysOrganization org2 = new SysOrganization();
        org2.setOrgId(2L);
        org2.setOrgName("测试部门2");

        List<SysOrganization> orgList = Arrays.asList(org1, org2);
        int total = 2;

        // When
        when(organizationMapper.selectOrganizationCount(query)).thenReturn(total);
        when(organizationMapper.selectOrganizationListByPage(query, (pageNum - 1) * pageSize, pageSize)).thenReturn(orgList);

        Map<String, Object> result = organizationService.listOrganizationsByPage(query, pageNum, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(total, result.get("total"));
        assertEquals(orgList, result.get("rows"));
        verify(organizationMapper, times(1)).selectOrganizationCount(query);
        verify(organizationMapper, times(1)).selectOrganizationListByPage(query, (pageNum - 1) * pageSize, pageSize);
    }

    // 测试查询用户所属组织机构
    @Test
    void testListUserOrganizations() {
        // Given
        Long userId = 1L;
        Map<String, Object> org1 = new HashMap<>();
        org1.put("orgId", 1L);
        org1.put("orgName", "研发部");
        org1.put("mainOrg", 1);

        Map<String, Object> org2 = new HashMap<>();
        org2.put("orgId", 2L);
        org2.put("orgName", "测试部");
        org2.put("mainOrg", 0);

        List<Map<String, Object>> expected = Arrays.asList(org1, org2);

        // When
        when(userOrganizationMapper.selectUserOrganizations(userId)).thenReturn(expected);

        List<Map<String, Object>> result = organizationService.listUserOrganizations(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("研发部", result.get(0).get("orgName"));
        assertEquals(1, result.get(0).get("mainOrg"));
    }

    // 测试设置用户主组织机构
    @Test
    void testSetMainOrganization() {
        // Given
        Long userId = 1L;
        Long orgId = 1L;

        // When
        // 先取消原主组织
        when(userOrganizationMapper.updateUserMainOrg(userId, 0)).thenReturn(1);
        // 设置新主组织
        when(userOrganizationMapper.updateUserMainOrgByUserIdAndOrgId(userId, orgId, 1)).thenReturn(1);

        boolean result = organizationService.setMainOrganization(userId, orgId);

        // Then
        assertTrue(result);
        verify(userOrganizationMapper, times(1)).updateUserMainOrg(userId, 0);
        verify(userOrganizationMapper, times(1)).updateUserMainOrgByUserIdAndOrgId(userId, orgId, 1);
    }
}