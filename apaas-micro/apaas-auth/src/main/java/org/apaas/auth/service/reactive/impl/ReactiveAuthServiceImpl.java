package org.apaas.auth.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.LoginUser;
import org.apaas.auth.feign.reactive.ReactiveSystemFeignClient;
import org.apaas.auth.service.reactive.ReactiveAuthService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式认证服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveAuthServiceImpl implements ReactiveAuthService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ReactiveSystemFeignClient systemFeignClient;

    @Override
    public Mono<LoginUser> login(String username, String password, String ipAddr) {
        // 用户认证
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password))
                .flatMap(authentication -> {
                    // 设置认证信息到上下文
                    return ReactiveSecurityContextHolder.withAuthentication(authentication)
                            .then(Mono.defer(() -> {
                                // 获取登录用户信息
                                LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                                
                                // 记录登录信息
                                return systemFeignClient.recordLoginInfo(username, ipAddr)
                                        .thenReturn(loginUser);
                            }));
                });
    }

    @Override
    public Mono<LoginUser> getUserInfo(String username) {
        // 根据用户名获取用户信息
        return systemFeignClient.getUserByUsername(username)
                .flatMap(user -> {
                    if (user == null) {
                        return Mono.error(new RuntimeException("用户不存在"));
                    }
                    
                    LoginUser loginUser = new LoginUser();
                    loginUser.setId(user.getId());
                    loginUser.setUsername(user.getUsername());
                    loginUser.setRealName(user.getRealName());
                    loginUser.setEmail(user.getEmail());
                    loginUser.setPhone(user.getPhone());
                    loginUser.setStatus(user.getStatus());
                    
                    // 获取用户权限
                    return systemFeignClient.getUserPermissions(user.getId())
                            .map(permissions -> {
                                loginUser.setPermissions(permissions);
                                return loginUser;
                            });
                });
    }

    @Override
    public Mono<Void> logout() {
        // 清除认证信息
        return ReactiveSecurityContextHolder.clearContext();
    }
}