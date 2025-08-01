package org.apaas.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.auth.form.LoginBody;
import org.apaas.auth.form.RegisterBody;
import org.apaas.auth.service.SysLoginService;
import org.apaas.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 */
@Tag(name = "认证授权")
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final SysLoginService sysLoginService;

    @Operation(summary = "登录方法")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody form) {
        // 生成令牌
        String token = sysLoginService.login(form.getUsername(), form.getPassword(), form.getCode(), form.getUuid());
        return AjaxResult.success().put("token", token);
    }

    @Operation(summary = "删除token")
    @DeleteMapping("/logout")
    public AjaxResult logout(HttpServletRequest request) {
        String token = sysLoginService.getToken(request);
        if (token != null) {
            sysLoginService.logout(token);
        }
        return AjaxResult.success();
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh")
    public AjaxResult refresh(HttpServletRequest request) {
        String token = sysLoginService.getToken(request);
        if (token != null) {
            sysLoginService.refreshToken(token);
            return AjaxResult.success();
        }
        return AjaxResult.error("令牌已过期");
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        String msg = sysLoginService.register(user);
        return AjaxResult.success(msg);
    }
}