package org.apaas.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.web.domain.PageResult;
import org.apaas.system.domain.entity.SysLoginInfo;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 系统访问记录
 */
@Tag(name = "登录日志管理")
@RestController
@RequestMapping("/monitor/logininfor")
@RequiredArgsConstructor
public class SysLoginInfoController {

    @Operation(summary = "获取系统访问记录列表")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysLoginInfo>> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return Mono.just(PageResult.build(new Page<>()));
    }

    @Operation(summary = "导出系统访问记录列表")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> export(ServerWebExchange exchange, BasePageQuery query) {
        // TODO: 实现导出功能
        return Mono.empty();
    }

    @Operation(summary = "批量删除系统登录日志")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{infoIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "登录日志ID数组") @PathVariable Long[] infoIds) {
        // TODO: 实现删除功能
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "清空系统登录日志")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> clean() {
        // TODO: 实现清空功能
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "账户解锁")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping(value = "/unlock/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> unlock(@Parameter(description = "用户名") @PathVariable String userName) {
        // TODO: 实现账户解锁功能
        return Mono.just(AjaxResult.success());
    }
}