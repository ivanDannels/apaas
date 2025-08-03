package org.apaas.auth.feign.reactive;

import org.apaas.auth.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * 响应式系统服务Feign客户端
 * @author ivan
 */
@HttpExchange(url = "/api/v1/reactive/system")
public interface ReactiveSystemFeignClient {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetExchange("/user/username/{username}")
    Mono<User> getUserByUsername(@PathVariable("username") String username);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetExchange("/user/{userId}/permissions")
    Mono<String[]> getUserPermissions(@PathVariable("userId") Long userId);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ipAddr   IP地址
     * @return 结果
     */
    @PostExchange("/user/login/record")
    Mono<Void> recordLoginInfo(@RequestParam("username") String username, @RequestParam("ipAddr") String ipAddr);
}