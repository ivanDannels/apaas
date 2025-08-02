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
import org.apaas.core.web.domain.PageResult;
import org.apaas.system.domain.entity.SysNotice;
import org.apaas.system.service.ReactiveSysNoticeService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 公告 信息操作处理
 */
@Tag(name = "通知公告管理")
@RestController
@RequestMapping("/api/v1/reactive/notices")
@RequiredArgsConstructor
public class SysNoticeController {
    private final ReactiveSysNoticeService noticeService;

    @Operation(summary = "获取通知公告列表")
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SysNotice> list(BasePageQuery query) {
        return noticeService.selectNoticePage(query);
    }

    @Operation(summary = "根据通知公告编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SysNotice> getInfo(@Parameter(description = "通知公告ID") @PathVariable Long noticeId) {
        return noticeService.getById(noticeId)
            .switchIfEmpty(Mono.error(new RuntimeException("通知公告不存在")));
    }

    @Operation(summary = "新增通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> add(@Validated @RequestBody SysNotice notice) {
        return noticeService.insertNotice(notice);
    }

    @Operation(summary = "修改通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> edit(
            @Parameter(description = "通知公告ID") @PathVariable Long noticeId,
            @Validated @RequestBody SysNotice notice) {
        notice.setNoticeId(noticeId);
        return noticeService.updateNotice(notice);
    }

    @Operation(summary = "删除通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> remove(@Parameter(description = "通知公告ID") @PathVariable Long noticeId) {
        return noticeService.deleteNoticeById(noticeId);
    }

    @Operation(summary = "批量删除通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> batchRemove(@RequestBody Long[] noticeIds) {
        return noticeService.deleteNoticeByIds(noticeIds);
    }
}