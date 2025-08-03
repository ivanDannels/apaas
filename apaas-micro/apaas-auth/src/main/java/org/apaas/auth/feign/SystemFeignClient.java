package org.apaas.auth.feign;

import org.apaas.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统服务Feign客户端
 * @author ivan
 */
@FeignClient(name = "apaas-system", path = "/system")
public interface SystemFeignClient {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/user/info")
    User getUserByUsername(@RequestParam("username") String username);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetMapping("/user/permissions/{userId}")
    String[] getUserPermissions(@PathVariable("userId") Long userId);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param ip       IP地址
     * @return 结果
     */
    @GetMapping("/user/login")
    void recordLoginInfo(@RequestParam("username") String username, @RequestParam("ip") String ip);
}