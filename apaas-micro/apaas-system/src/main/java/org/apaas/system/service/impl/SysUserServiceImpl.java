package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apaas.core.exception.BusinessException;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.domain.entity.SysUser;
import org.apaas.system.domain.entity.SysUserRole;
import org.apaas.system.mapper.SysUserMapper;
import org.apaas.system.mapper.SysUserRoleMapper;
import org.apaas.system.service.ISysMenuService;
import org.apaas.system.service.ISysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final ISysMenuService menuService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException("用户名不能为空");
        }
        return userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    @Override
    public Page<SysUser> getUserPage(Page<SysUser> page, UserQueryDTO query) {
        return userMapper.selectUserPage(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(SysUser user) {
        // 检查用户名是否存在
        SysUser existUser = getUserByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        // 保存用户信息
        boolean result = save(user);

        // 保存用户角色关系
        if (result && user.getRoleIds() != null && user.getRoleIds().length > 0) {
            insertUserRoles(user.getId(), user.getRoleIds());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUser user) {
        // 检查用户是否存在
        SysUser existUser = getById(user.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改了用户名，检查是否重复
        if (!existUser.getUsername().equals(user.getUsername())) {
            SysUser sameNameUser = getUserByUsername(user.getUsername());
            if (sameNameUser != null && !sameNameUser.getId().equals(user.getId())) {
                throw new BusinessException("用户名已存在");
            }
        }

        // 不更新密码
        user.setPassword(null);

        // 更新用户信息
        boolean result = updateById(user);

        // 更新用户角色关系
        if (result && user.getRoleIds() != null) {
            // 删除原有角色关系
            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, user.getId()));
            // 添加新的角色关系
            if (user.getRoleIds().length > 0) {
                insertUserRoles(user.getId(), user.getRoleIds());
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long userId) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 检查是否为管理员
        if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
            throw new BusinessException("不能删除管理员用户");
        }

        // 删除用户角色关系
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));

        // 删除用户
        return removeById(userId);
    }

    @Override
    public boolean resetPassword(Long userId, String password) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 更新密码
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setPassword(passwordEncoder.encode(password));
        updateUser.setUpdateTime(LocalDateTime.now());

        return updateById(updateUser);
    }

    @Override
    public boolean changeStatus(Long userId, Integer status) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 检查是否为管理员
        if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
            throw new BusinessException("不能修改管理员用户状态");
        }

        // 更新状态
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setStatus(status);
        updateUser.setUpdateTime(LocalDateTime.now());

        return updateById(updateUser);
    }

    @Override
    public String[] getUserPermissions(Long userId) {
        // 获取用户菜单权限
        List<String> permissions = menuService.getMenuPermissionsByUserId(userId);
        return permissions.toArray(new String[0]);
    }

    @Override
    public boolean recordLoginInfo(String username, String ip) {
        SysUser user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        // 更新登录信息
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setLoginIp(ip);
        updateUser.setLoginDate(LocalDateTime.now());
        updateUser.setUpdateTime(LocalDateTime.now());

        return updateById(updateUser);
    }

    /**
     * 批量插入用户角色关系
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     */
    private void insertUserRoles(Long userId, Long[] roleIds) {
        if (userId == null || roleIds == null || roleIds.length == 0) {
            return;
        }

        Arrays.stream(roleIds).forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
    }
}