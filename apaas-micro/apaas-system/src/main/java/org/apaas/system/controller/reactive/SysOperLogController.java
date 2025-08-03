package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import org.apaas.system.domain.entity.SysOperLog;
import org.apaas.system.service.ReactiveSysOperLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 操作日志记录
 */
@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/api/v1/reactive/oper-logs")
@RequiredArgsConstructor
public class SysOperLogController {
    private final ReactiveSysOperLogService operLogService;

    @Operation(summary = "获取操作日志列表")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysOperLog>> list(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPageNum() - 1, query.getPageSize());
        return operLogService.selectOperLogPage(pageRequest);
    }

    @Operation(summary = "导出操作日志列表")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> export(ServerWebExchange exchange, BasePageQuery query) {
        return Mono.fromCallable(() -> {
            try {
                operLogService.exportOperLog(exchange, query);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "批量删除操作日志")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{operIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "操作日志ID数组") @PathVariable Long[] operIds) {
        return operLogService.deleteOperLogByIds(operIds)
                .then(Mono.just(AjaxResult.success("删除成功")))
                .onErrorReturn(AjaxResult.error("删除失败"));
    }

    @Operation(summary = "查询操作日志详细")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:query')")
    @GetMapping(value = "/{operId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SysOperLog> getInfo(@Parameter(description = "操作日志ID") @PathVariable Long operId) {
        return operLogService.getById(operId)
            .switchIfEmpty(Mono.error(new RuntimeException("操作日志不存在")));
    }

    @Operation(summary = "清空操作日志")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> clean() {
        return operLogService.cleanOperLog()
                .then(Mono.just(AjaxResult.success("清空成功")))
                .onErrorReturn(AjaxResult.error("清空失败"));
    }
}