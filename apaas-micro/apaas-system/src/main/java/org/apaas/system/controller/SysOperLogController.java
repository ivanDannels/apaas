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
import org.apaas.system.domain.entity.SysOperLog;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 操作日志记录
 */
@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/monitor/operlog")
@RequiredArgsConstructor
public class SysOperLogController {

    @Operation(summary = "获取操作日志列表")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysOperLog>> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return Mono.just(PageResult.build(new Page<>()));
    }

    @Operation(summary = "导出操作日志列表")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> export(ServerWebExchange exchange, BasePageQuery query) {
        // TODO: 实现导出功能
        return Mono.empty();
    }

    @Operation(summary = "批量删除操作日志")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{operIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "操作日志ID数组") @PathVariable Long[] operIds) {
        // TODO: 实现删除功能
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "查询操作日志详细")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:query')")
    @GetMapping(value = "/{operId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> getInfo(@Parameter(description = "操作日志ID") @PathVariable Long operId) {
        // TODO: 实现查询详情
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "清空操作日志")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> clean() {
        // TODO: 实现清空功能
        return Mono.just(AjaxResult.success());
    }
}