package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.User;
import org.apaas.core.query.PageResult;
import org.apaas.core.service.BaseService;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务接口
 */
public interface ReactiveUserService extends BaseService<User, Long> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<User> getUserByUsername(String username);

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    Mono<User> addUser(User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 更新结果
     */
    Mono<User> updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    Mono<Void> deleteUser(Long id);

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    Mono<Void> resetPassword(Long id, String newPassword);

    /**
     * 修改用户状态
     *
     * @param id 用户ID
     * @param status 状态
     * @return 修改结果
     */
    Mono<Void> changeStatus(Long id, Integer status);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Flux<String> getUserPermissions(Long userId);

    /**
     * 记录用户登录信息
     *
     * @param userId 用户ID
     * @param loginIp 登录IP
     * @return 记录结果
     */
    Mono<Void> recordLoginInfo(Long userId, String loginIp);

    Mono<User> login(String username, String password);

    Mono<Boolean> updatePassword(String oldPassword, String newPassword);

    Mono<User> getCurrentUser();
}