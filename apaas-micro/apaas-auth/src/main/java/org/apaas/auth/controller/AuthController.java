package org.apaas.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.LoginUser;
import org.apaas.auth.feign.SystemFeignClient;
import org.apaas.core.utils.IpUtils;
import org.apaas.core.web.domain.AjaxResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "认证相关接口")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SystemFeignClient systemFeignClient;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "用户登录接口")
    public AjaxResult<LoginUser> login(
            @Parameter(description = "用户名", required = true)
            @RequestParam String username,
            @Parameter(description = "密码", required = true)
            @RequestParam String password,
            HttpServletRequest request) {
        // 用户认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取登录用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 记录登录信息
        systemFeignClient.recordLoginInfo(username, IpUtils.getIpAddr(request));

        return AjaxResult.success("登录成功", loginUser);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/user/info")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息")
    public AjaxResult<LoginUser> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String username = jwt.getSubject();
            // 根据用户名获取用户信息
            SysUserDTO user = systemFeignClient.getUserByUsername(username);
            if (user == null) {
                return AjaxResult.error("用户不存在");
            }
            LoginUser loginUser = new LoginUser();
            loginUser.setId(user.getId());
            loginUser.setUsername(user.getUsername());
            loginUser.setRealName(user.getRealName());
            loginUser.setEmail(user.getEmail());
            loginUser.setPhone(user.getPhone());
            loginUser.setStatus(user.getStatus());
            // 获取用户权限
            String[] permissions = systemFeignClient.getUserPermissions(user.getId());
            loginUser.setPermissions(permissions);
            return AjaxResult.success(loginUser);
        }
        return AjaxResult.error("获取用户信息失败");
    }

    /**
     * 登出
     *
     * @return 结果
     */
    @PostMapping("/logout")
    @Operation(summary = "登出", description = "用户登出接口")
    public AjaxResult<Void> logout() {
        return AjaxResult.success("登出成功");
    }
}