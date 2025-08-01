package org.apaas.form.controller;

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
import org.apaas.form.domain.entity.FormDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 表单定义控制器
 */
@Tag(name = "表单定义管理")
@RestController
@RequestMapping("/form/definition")
@RequiredArgsConstructor
public class FormDefinitionController {

    @Operation(summary = "获取表单定义列表")
    @PreAuthorize("@ss.hasPermi('form:definition:list')")
    @GetMapping("/list")
    public PageResult<FormDefinition> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取表单定义详细信息")
    @PreAuthorize("@ss.hasPermi('form:definition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现查询详情
        return AjaxResult.success();
    }

    @Operation(summary = "根据表单编码获取表单定义")
    @GetMapping(value = "/code/{formCode}")
    public AjaxResult getByCode(@Parameter(description = "表单编码") @PathVariable String formCode) {
        // TODO: 实现根据编码查询
        return AjaxResult.success();
    }

    @Operation(summary = "新增表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:add')")
    @Log(title = "表单定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FormDefinition formDefinition) {
        // TODO: 实现新增功能
        return AjaxResult.success();
    }

    @Operation(summary = "修改表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:edit')")
    @Log(title = "表单定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FormDefinition formDefinition) {
        // TODO: 实现修改功能
        return AjaxResult.success();
    }

    @Operation(summary = "删除表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:remove')")
    @Log(title = "表单定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "表单定义ID数组") @PathVariable Long[] ids) {
        // TODO: 实现删除功能
        return AjaxResult.success();
    }

    @Operation(summary = "发布表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:publish')")
    @Log(title = "表单定义", businessType = BusinessType.UPDATE)
    @PostMapping("/publish/{id}")
    public AjaxResult publish(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现发布功能
        return AjaxResult.success();
    }

    @Operation(summary = "停用表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:disable')")
    @Log(title = "表单定义", businessType = BusinessType.UPDATE)
    @PostMapping("/disable/{id}")
    public AjaxResult disable(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现停用功能
        return AjaxResult.success();
    }

    @Operation(summary = "复制表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:copy')")
    @Log(title = "表单定义", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现复制功能
        return AjaxResult.success();
    }

    @Operation(summary = "预览表单")
    @GetMapping("/preview/{id}")
    public AjaxResult preview(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现表单预览功能
        return AjaxResult.success();
    }

    @Operation(summary = "导出表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:export')")
    @Log(title = "表单定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export/{id}")
    public AjaxResult export(@Parameter(description = "表单定义ID") @PathVariable Long id) {
        // TODO: 实现导出功能
        return AjaxResult.success();
    }

    @Operation(summary = "导入表单定义")
    @PreAuthorize("@ss.hasPermi('form:definition:import')")
    @Log(title = "表单定义", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importForm(@RequestParam("file") String formJson) {
        // TODO: 实现导入功能
        return AjaxResult.success();
    }
}