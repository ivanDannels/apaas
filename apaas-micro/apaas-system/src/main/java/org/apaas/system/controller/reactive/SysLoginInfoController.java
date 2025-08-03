package org.apaas.system.controller.reactive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import org.apaas.system.domain.entity.SysLoginInfo;
import org.apaas.system.service.ReactiveSysLoginInfoService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 系统访问记录
 */
@Tag(name = "登录日志管理")
@RestController
@RequestMapping("/api/v1/reactive/login-info")
@RequiredArgsConstructor
public class SysLoginInfoController {
    private final ReactiveSysLoginInfoService loginInfoService;

    @Operation(summary = "获取系统访问记录列表")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SysLoginInfo> list(BasePageQuery query) {
        return loginInfoService.selectLoginInfoPage(query);
    }

    @Operation(summary = "导出系统访问记录列表")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> export(ServerWebExchange exchange, BasePageQuery query) {
        return Mono.fromCallable(() -> {
            try {
                loginInfoService.exportLoginInfo(exchange, query);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "批量删除系统登录日志")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{infoIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> remove(@Parameter(description = "登录日志ID数组") @PathVariable Long[] infoIds) {
        return loginInfoService.deleteLoginInfoByIds(infoIds);
    }

    @Operation(summary = "清空系统登录日志")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> clean() {
        return loginInfoService.cleanLoginInfo();
    }

    @Operation(summary = "账户解锁")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping(value = "/unlock/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> unlock(@Parameter(description = "用户名") @PathVariable String userName) {
        return loginInfoService.unlock(userName);
    }
}