package org.apaas.auth.service.reactive;

import org.apaas.auth.domain.LoginUser;
import reactor.core.publisher.Mono;

/**
 * 响应式认证服务接口
 */
public interface ReactiveAuthService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param ipAddr IP地址
     * @return 登录用户信息
     */
    Mono<LoginUser> login(String username, String password, String ipAddr);

    /**
     * 获取当前用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<LoginUser> getUserInfo(String username);

    /**
     * 登出
     *
     * @return 结果
     */
    Mono<Void> logout();
}