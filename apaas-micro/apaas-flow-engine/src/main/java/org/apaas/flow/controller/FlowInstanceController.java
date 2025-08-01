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
import org.apaas.flow.domain.entity.FlowInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程实例控制器
 */
@Tag(name = "流程实例管理")
@RestController
@RequestMapping("/flow/instance")
@RequiredArgsConstructor
public class FlowInstanceController {

    @Operation(summary = "获取流程实例列表")
    @PreAuthorize("@ss.hasPermi('flow:instance:list')")
    @GetMapping("/list")
    public PageResult<FlowInstance> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取我发起的流程实例")
    @GetMapping("/myStarted")
    public PageResult<FlowInstance> myStarted(BasePageQuery query) {
        // TODO: 实现查询我发起的流程
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取流程实例详细信息")
    @PreAuthorize("@ss.hasPermi('flow:instance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "流程实例ID") @PathVariable Long id) {
        // TODO: 实现查询详情
        return AjaxResult.success();
    }

    @Operation(summary = "启动流程实例")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @PostMapping("/start")
    public AjaxResult start(@Validated @RequestBody FlowInstance flowInstance) {
        // TODO: 实现启动流程功能
        return AjaxResult.success();
    }

    @Operation(summary = "终止流程实例")
    @PreAuthorize("@ss.hasPermi('flow:instance:terminate')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PostMapping("/terminate/{id}")
    public AjaxResult terminate(@Parameter(description = "流程实例ID") @PathVariable Long id,
                               @Parameter(description = "终止原因") @RequestParam String reason) {
        // TODO: 实现终止流程功能
        return AjaxResult.success();
    }

    @Operation(summary = "挂起流程实例")
    @PreAuthorize("@ss.hasPermi('flow:instance:suspend')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PostMapping("/suspend/{id}")
    public AjaxResult suspend(@Parameter(description = "流程实例ID") @PathVariable Long id) {
        // TODO: 实现挂起流程功能
        return AjaxResult.success();
    }

    @Operation(summary = "激活流程实例")
    @PreAuthorize("@ss.hasPermi('flow:instance:activate')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PostMapping("/activate/{id}")
    public AjaxResult activate(@Parameter(description = "流程实例ID") @PathVariable Long id) {
        // TODO: 实现激活流程功能
        return AjaxResult.success();
    }

    @Operation(summary = "删除流程实例")
    @PreAuthorize("@ss.hasPermi('flow:instance:remove')")
    @Log(title = "流程实例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "流程实例ID数组") @PathVariable Long[] ids) {
        // TODO: 实现删除功能
        return AjaxResult.success();
    }

    @Operation(summary = "获取流程实例历史")
    @GetMapping("/history/{id}")
    public AjaxResult getHistory(@Parameter(description = "流程实例ID") @PathVariable Long id) {
        // TODO: 实现获取流程历史功能
        return AjaxResult.success();
    }

    @Operation(summary = "获取流程实例图")
    @GetMapping("/diagram/{id}")
    public AjaxResult getDiagram(@Parameter(description = "流程实例ID") @PathVariable Long id) {
        // TODO: 实现获取流程图功能
        return AjaxResult.success();
    }
}