package org.apaas.system.service.reactive;

import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.entity.SysUser;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务接口
 */
@Service
public interface ReactiveISysUserService {
    /**
     * 根据用户名查询用户
     */
    Mono<SysUser> getUserByUsername(String username);

    /**
     * 分页查询用户列表
     */
    Flux<SysUser> getUserPage(Pageable pageable, UserQueryDTO query);

    /**
     * 新增用户
     */
    Mono<Boolean> addUser(SysUser user);

    /**
     * 修改用户
     */
    Mono<Boolean> updateUser(SysUser user);

    /**
     * 删除用户
     */
    Mono<Boolean> deleteUser(Long userId);

    /**
     * 重置密码
     */
    Mono<Boolean> resetPassword(Long userId, String password);

    /**
     * 修改用户状态
     */
    Mono<Boolean> changeStatus(Long userId, Integer status);

    /**
     * 获取用户权限
     */
    Mono<String[]> getUserPermissions(Long userId);

    /**
     * 记录登录信息
     */
    Mono<Boolean> recordLoginInfo(String username, String ip);
}