package org.apaas.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.auth.domain.DataScope;
import org.apaas.auth.entity.Resource;
import org.apaas.auth.entity.Role;
import org.apaas.auth.entity.RoleResource;
import org.apaas.auth.mapper.ResourceMapper;
import org.apaas.auth.mapper.RoleMapper;
import org.apaas.auth.mapper.RoleResourceMapper;
import org.apaas.auth.service.RoleService;
import org.apaas.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public IPage<Role> selectPage(RoleDTO query) {
        Page<Role> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        if (query.getName() != null) {
            wrapper.like("name", query.getName());
        }
        if (query.getCode() != null) {
            wrapper.like("code", query.getCode());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        return roleMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public boolean create(Role role) {
        role.setStatus(0);
        role.setDeleted(0);
        role.setTenantId(SecurityUtils.getTenantId());
        role.setCreateBy(SecurityUtils.getUsername());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(LocalDateTime.now());
        return save(role);
    }

    @Override
    @Transactional
    public boolean update(Role role) {
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(LocalDateTime.now());
        return updateById(role);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Role role = new Role();
        role.setId(id);
        role.setDeleted(1);
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(LocalDateTime.now());
        return updateById(role);
    }

    @Override
    @Transactional
    public boolean changeStatus(Long id, Integer status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(LocalDateTime.now());
        return updateById(role);
    }

    @Override
    @Transactional
    public boolean associateRoleResource(Long roleId, List<Long> resourceIds) {
        // 删除旧的关联
        QueryWrapper<RoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        roleResourceMapper.delete(wrapper);

        // 添加新的关联
        if (resourceIds != null && !resourceIds.isEmpty()) {
            List<RoleResource> roleResources = new ArrayList<>();
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResource.setTenantId(SecurityUtils.getTenantId());
                roleResources.add(roleResource);
            }
            return roleResourceMapper.insertBatch(roleResources);
        }
        return true;
    }

    @Override
    public List<String> getPermissionsByRoleId(Long roleId) {
        List<Resource> resources = resourceMapper.selectByRoleId(roleId);
        List<String> permissions = new ArrayList<>();
        for (Resource resource : resources) {
            if (resource.getPermission() != null && !resource.getPermission().isEmpty()) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public String getDataScopeByRoleId(Long roleId) {
        Role role = getById(roleId);
        return role != null ? role.getDataScopeType() : null;
    }

    @Override
    @Transactional
    public boolean updateDataScope(Long roleId, String dataScopeType, List<Long> customDeptIds) {
        Role role = new Role();
        role.setId(roleId);
        role.setDataScopeType(dataScopeType);
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(LocalDateTime.now());

        // 更新角色信息
        boolean result = updateById(role);

        // 如果是自定义部门数据范围，这里可以添加处理逻辑
        // 例如，保存角色和部门的关联关系

        return result;
    }
}