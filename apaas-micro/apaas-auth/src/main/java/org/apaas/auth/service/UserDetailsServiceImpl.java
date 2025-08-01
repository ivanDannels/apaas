package org.apaas.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.LoginUser;
import org.apaas.auth.feign.SystemFeignClient;
import org.apaas.core.exception.BusinessException;
import org.apaas.system.domain.entity.SysUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SystemFeignClient systemFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 调用系统服务获取用户信息
            SysUser user = systemFeignClient.getUserByUsername(username);
            if (user == null) {
                log.info("登录用户：{} 不存在", username);
                throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
            }

            // 检查用户状态
            if (user.getStatus() != null && user.getStatus() == 0) {
                log.info("登录用户：{} 已被禁用", username);
                throw new BusinessException("对不起，您的账号：" + username + " 已被禁用");
            }

            // 调用系统服务获取用户权限
            String[] permissions = systemFeignClient.getUserPermissions(user.getId());

            // 创建登录用户
            return new LoginUser(user, AuthorityUtils.createAuthorityList(permissions));
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            throw new UsernameNotFoundException("获取用户信息异常");
        }
    }
}