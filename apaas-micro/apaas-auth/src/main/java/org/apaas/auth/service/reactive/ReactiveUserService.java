package org.apaas.auth.service.reactive;

import org.apaas.auth.domain.dto.UserDTO;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.User;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务接口
 */
public interface ReactiveUserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    Mono<String> login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 结果
     */
    Mono<Boolean> register(User user);

    /**
     * 分页查询用户
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<PageResult<User>> selectPage(UserDTO query);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    Mono<User> getById(Long id);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 结果
     */
    Mono<Boolean> updateById(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 结果
     */
    Mono<Boolean> removeById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID数组
     * @return 结果
     */
    Mono<Boolean> removeByIds(Long[] ids);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    Mono<User> getCurrentUser();

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 结果
     */
    Mono<Boolean> updatePassword(String oldPassword, String newPassword);
}