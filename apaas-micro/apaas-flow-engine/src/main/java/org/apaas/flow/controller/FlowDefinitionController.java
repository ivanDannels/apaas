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
import org.apaas.flow.domain.entity.FlowDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义控制器
 */
@Tag(name = "流程定义管理")
@RestController
@RequestMapping("/flow/definition")
@RequiredArgsConstructor
public class FlowDefinitionController {

    @Operation(summary = "获取流程定义列表")
    @PreAuthorize("@ss.hasPermi('flow:definition:list')")
    @GetMapping("/list")
    public PageResult<FlowDefinition> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取流程定义详细信息")
    @PreAuthorize("@ss.hasPermi('flow:definition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "流程定义ID") @PathVariable Long id) {
        // TODO: 实现查询详情
        return AjaxResult.success();
    }

    @Operation(summary = "新增流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:add')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FlowDefinition flowDefinition) {
        // TODO: 实现新增功能
        return AjaxResult.success();
    }

    @Operation(summary = "修改流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FlowDefinition flowDefinition) {
        // TODO: 实现修改功能
        return AjaxResult.success();
    }

    @Operation(summary = "删除流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:remove')")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "流程定义ID数组") @PathVariable Long[] ids) {
        // TODO: 实现删除功能
        return AjaxResult.success();
    }

    @Operation(summary = "发布流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:publish')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PostMapping("/publish/{id}")
    public AjaxResult publish(@Parameter(description = "流程定义ID") @PathVariable Long id) {
        // TODO: 实现发布功能
        return AjaxResult.success();
    }

    @Operation(summary = "停用流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:disable')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PostMapping("/disable/{id}")
    public AjaxResult disable(@Parameter(description = "流程定义ID") @PathVariable Long id) {
        // TODO: 实现停用功能
        return AjaxResult.success();
    }

    @Operation(summary = "复制流程定义")
    @PreAuthorize("@ss.hasPermi('flow:definition:copy')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@Parameter(description = "流程定义ID") @PathVariable Long id) {
        // TODO: 实现复制功能
        return AjaxResult.success();
    }
}