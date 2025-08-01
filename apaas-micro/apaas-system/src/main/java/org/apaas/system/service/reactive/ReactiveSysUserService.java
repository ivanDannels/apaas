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
public interface ReactiveSysUserService {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<SysUser> getUserByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 用户列表
     */
    Flux<SysUser> getUserPage(Pageable pageable, UserQueryDTO query);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 结果
     */
    Mono<Boolean> addUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user 用户信息
     * @return 结果
     */
    Mono<Boolean> updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    Mono<Boolean> deleteUser(Long userId);

    /**
     * 重置密码
     *
     * @param userId   用户ID
     * @param password 新密码
     * @return 结果
     */
    Mono<Boolean> resetPassword(Long userId, String password);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 结果
     */
    Mono<Boolean> changeStatus(Long userId, Integer status);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Mono<String[]> getUserPermissions(Long userId);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ip       IP地址
     * @return 结果
     */
    Mono<Boolean> recordLoginInfo(String username, String ip);
}