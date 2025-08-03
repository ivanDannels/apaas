package org.apaas.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.auth.domain.dto.UserDTO;
import org.apaas.auth.entity.Role;
import org.apaas.auth.entity.User;
import org.apaas.auth.entity.UserRole;
import org.apaas.auth.mapper.ResourceMapper;
import org.apaas.auth.mapper.RoleMapper;
import org.apaas.auth.mapper.UserMapper;
import org.apaas.auth.mapper.UserRoleMapper;
import org.apaas.auth.service.UserService;
import org.apaas.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public String login(String username, String password) {
        // 查询用户
        User user = getByUsername(username);
        if (user == null || user.getStatus() == 1 || user.getDeleted() == 1) {
            return null;
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("tenantId", user.getTenantId());

        // 获取用户角色
        List<String> roles = getRolesByUserId(user.getId());
        claims.put("roles", roles);

        return jwtUtils.generateToken(claims);
    }

    @Override
    @Transactional
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = getByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(0);
        user.setDeleted(0);
        user.setTenantId(SecurityUtils.getTenantId());
        user.setCreator(SecurityUtils.getUsername());
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdater(SecurityUtils.getUsername());
        user.setUpdatedTime(LocalDateTime.now());

        return save(user);
    }

    @Override
    @Transactional
    public boolean resetPassword(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdater(SecurityUtils.getUsername());
        user.setUpdatedTime(LocalDateTime.now());
        return updateById(user);
    }

    @Override
    public IPageResult<User> selectPage(UserDTO query) {
        PageResult<User> page = new PageResult<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        if (query.getUsername() != null) {
            wrapper.like("username", query.getUsername());
        }
        if (query.getNickname() != null) {
            wrapper.like("nickname", query.getNickname());
        }
        if (query.getPhone() != null) {
            wrapper.like("phone", query.getPhone());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        if (query.getDeptId() != null) {
            wrapper.eq("dept_id", query.getDeptId());
        }
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public boolean changeStatus(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdater(SecurityUtils.getUsername());
        user.setUpdatedTime(LocalDateTime.now());
        return updateById(user);
    }

    @Override
    @Transactional
    public boolean associateUserRole(Long userId, List<Long> roleIds) {
        // 删除旧的关联
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        userRoleMapper.delete(wrapper);

        // 添加新的关联
        if (roleIds != null && !roleIds.isEmpty()) {
            List<UserRole> userRoles = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setTenantId(SecurityUtils.getTenantId());
                userRoles.add(userRole);
            }
            return userRoleMapper.insertBatch(userRoles);
        }
        return true;
    }

    @Override
    public List<String> getRolesByUserId(Long userId) {
        List<Role> roles = roleMapper.selectByUserId(userId);
        List<String> roleCodes = new ArrayList<>();
        for (Role role : roles) {
            roleCodes.add(role.getCode());
        }
        return roleCodes;
    }

    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        List<String> permissions = resourceMapper.selectByUserId(userId);
        return permissions;
    }
}