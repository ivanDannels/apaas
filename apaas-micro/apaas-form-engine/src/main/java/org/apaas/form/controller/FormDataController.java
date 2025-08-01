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
import org.apaas.form.domain.entity.FormData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 表单数据控制器
 */
@Tag(name = "表单数据管理")
@RestController
@RequestMapping("/form/data")
@RequiredArgsConstructor
public class FormDataController {

    @Operation(summary = "获取表单数据列表")
    @PreAuthorize("@ss.hasPermi('form:data:list')")
    @GetMapping("/list")
    public PageResult<FormData> list(BasePageQuery query) {
        // TODO: 实现分页查询
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "根据表单编码获取数据列表")
    @GetMapping("/list/{formCode}")
    public PageResult<FormData> listByFormCode(@Parameter(description = "表单编码") @PathVariable String formCode,
                                              BasePageQuery query) {
        // TODO: 实现根据表单编码查询数据
        return PageResult.build(new Page<>());
    }

    @Operation(summary = "获取表单数据详细信息")
    @PreAuthorize("@ss.hasPermi('form:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "表单数据ID") @PathVariable Long id) {
        // TODO: 实现查询详情
        return AjaxResult.success();
    }

    @Operation(summary = "根据业务键获取表单数据")
    @GetMapping(value = "/business/{businessKey}")
    public AjaxResult getByBusinessKey(@Parameter(description = "业务键") @PathVariable String businessKey) {
        // TODO: 实现根据业务键查询
        return AjaxResult.success();
    }

    @Operation(summary = "保存表单数据")
    @Log(title = "表单数据", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult save(@Validated @RequestBody FormData formData) {
        // TODO: 实现保存功能
        return AjaxResult.success();
    }

    @Operation(summary = "提交表单数据")
    @Log(title = "表单数据", businessType = BusinessType.UPDATE)
    @PostMapping("/submit")
    public AjaxResult submit(@Validated @RequestBody FormData formData) {
        // TODO: 实现提交功能
        return AjaxResult.success();
    }

    @Operation(summary = "修改表单数据")
    @PreAuthorize("@ss.hasPermi('form:data:edit')")
    @Log(title = "表单数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FormData formData) {
        // TODO: 实现修改功能
        return AjaxResult.success();
    }

    @Operation(summary = "删除表单数据")
    @PreAuthorize("@ss.hasPermi('form:data:remove')")
    @Log(title = "表单数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "表单数据ID数组") @PathVariable Long[] ids) {
        // TODO: 实现删除功能
        return AjaxResult.success();
    }

    @Operation(summary = "批量导入表单数据")
    @PreAuthorize("@ss.hasPermi('form:data:import')")
    @Log(title = "表单数据", businessType = BusinessType.IMPORT)
    @PostMapping("/import/{formCode}")
    public AjaxResult importData(@Parameter(description = "表单编码") @PathVariable String formCode,
                                @RequestParam("file") String dataJson) {
        // TODO: 实现批量导入功能
        return AjaxResult.success();
    }

    @Operation(summary = "导出表单数据")
    @PreAuthorize("@ss.hasPermi('form:data:export')")
    @Log(title = "表单数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export/{formCode}")
    public AjaxResult export(@Parameter(description = "表单编码") @PathVariable String formCode,
                            BasePageQuery query) {
        // TODO: 实现导出功能
        return AjaxResult.success();
    }

    @Operation(summary = "获取表单数据统计")
    @GetMapping("/statistics/{formCode}")
    public AjaxResult getStatistics(@Parameter(description = "表单编码") @PathVariable String formCode) {
        // TODO: 实现数据统计功能
        return AjaxResult.success();
    }

    @Operation(summary = "验证表单数据")
    @PostMapping("/validate")
    public AjaxResult validate(@Validated @RequestBody FormData formData) {
        // TODO: 实现数据验证功能
        return AjaxResult.success();
    }
}