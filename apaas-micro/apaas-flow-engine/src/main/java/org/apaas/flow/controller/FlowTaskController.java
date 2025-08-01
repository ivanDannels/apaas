package org.apaas.flow.controller;

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
import org.apaas.flow.domain.entity.FlowTask;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程任务控制器
 */
@Tag(name = "流程任务管理")
@RestController
@RequestMapping("/flow/task")
@RequiredArgsConstructor
public class FlowTaskController {

    @Operation(summary = "获取待办任务列表")
    @GetMapping("/todo")
    public PageResult<FlowTask> todoList(BasePageQuery query) {
        // TODO: 实现待办任务查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取已办任务列表")
    @GetMapping("/done")
    public PageResult<FlowTask> doneList(BasePageQuery query) {
        // TODO: 实现已办任务查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取所有任务列表")
    @PreAuthorize("@ss.hasPermi('flow:task:list')")
    @GetMapping("/list")
    public PageResult<FlowTask> list(BasePageQuery query) {
        // TODO: 实现任务列表查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取任务详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "任务ID") @PathVariable Long id) {
        // TODO: 实现查询任务详情
        return AjaxResult.success();
    }

    @Operation(summary = "完成任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/complete")
    public AjaxResult complete(@Validated @RequestBody FlowTask task) {
        // TODO: 实现完成任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "拒绝任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/reject")
    public AjaxResult reject(@Validated @RequestBody FlowTask task) {
        // TODO: 实现拒绝任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "转办任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/transfer")
    public AjaxResult transfer(@Parameter(description = "任务ID") @RequestParam Long taskId,
                              @Parameter(description = "转办人ID") @RequestParam Long assigneeId,
                              @Parameter(description = "转办意见") @RequestParam String comment) {
        // TODO: 实现转办任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "委派任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/delegate")
    public AjaxResult delegate(@Parameter(description = "任务ID") @RequestParam Long taskId,
                              @Parameter(description = "委派人ID") @RequestParam Long assigneeId,
                              @Parameter(description = "委派意见") @RequestParam String comment) {
        // TODO: 实现委派任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "认领任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/claim/{id}")
    public AjaxResult claim(@Parameter(description = "任务ID") @PathVariable Long id) {
        // TODO: 实现认领任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "取消认领任务")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/unclaim/{id}")
    public AjaxResult unclaim(@Parameter(description = "任务ID") @PathVariable Long id) {
        // TODO: 实现取消认领任务功能
        return AjaxResult.success();
    }

    @Operation(summary = "获取任务历史")
    @GetMapping("/history/{instanceId}")
    public AjaxResult getTaskHistory(@Parameter(description = "流程实例ID") @PathVariable Long instanceId) {
        // TODO: 实现获取任务历史功能
        return AjaxResult.success();
    }

    @Operation(summary = "批量处理任务")
    @PreAuthorize("@ss.hasPermi('flow:task:batch')")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @PostMapping("/batch")
    public AjaxResult batchProcess(@RequestBody FlowTask[] tasks) {
        // TODO: 实现批量处理任务功能
        return AjaxResult.success();
    }
}