package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.system.entity.SysOperLog;
import org.apaas.system.service.reactive.ReactiveSysOperLogService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 操作日志记录
 * @author ivan
 */
@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/api/v1/reactive/oper-logs")
public class SysOperLogController extends ReactiveBaseController<SysOperLog, Long, ReactiveSysOperLogService> {

    public SysOperLogController(ReactiveSysOperLogService service) {
        super(service);
    }

    @Operation(summary = "批量删除操作日志")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> remove(@Parameter(description = "操作日志ID数组") @RequestBody Long[] operIds) {
        return super.deleteBatch(operIds).then(Mono.empty());
    }

    @Operation(summary = "查询操作日志详细")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:query')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SysOperLog> getInfo(@Parameter(description = "操作日志ID") @PathVariable Long id) {
        return super.get(id);
    }

}