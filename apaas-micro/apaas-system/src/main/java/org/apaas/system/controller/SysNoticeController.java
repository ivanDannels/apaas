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
import org.apaas.system.domain.entity.SysNotice;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 公告 信息操作处理
 */
@Tag(name = "通知公告管理")
@RestController
@RequestMapping("/system/notice")
@RequiredArgsConstructor
public class SysNoticeController {

    @Operation(summary = "获取通知公告列表")
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysNotice>> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return Mono.just(PageResult.build(new Page<>()));
    }

    @Operation(summary = "根据通知公告编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> getInfo(@Parameter(description = "通知公告ID") @PathVariable Long noticeId) {
        // TODO: 实现查询详情
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "新增通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> add(@Validated @RequestBody SysNotice notice) {
        // TODO: 实现新增功能
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "修改通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> edit(@Validated @RequestBody SysNotice notice) {
        // TODO: 实现修改功能
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "删除通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{noticeIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "通知公告ID数组") @PathVariable Long[] noticeIds) {
        // TODO: 实现删除功能
        return Mono.just(AjaxResult.success());
    }
}