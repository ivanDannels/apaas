package org.apaas.form.engine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.form.engine.domain.dto.FormDefinitionDTO;
import org.apaas.form.engine.entity.FormDefinition;
import org.apaas.form.engine.service.FormDefinitionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表单定义控制器
 */
@RestController
@RequestMapping("/form/definition")
@Tag(name = "表单定义管理", description = "表单定义CRUD及发布操作")
public class FormDefinitionController {

    @Resource
    private FormDefinitionService formDefinitionService;

    /**
     * 获取表单定义列表
     */
    @GetMapping
    @Operation(summary = "获取表单定义列表", description = "分页查询表单定义信息")
    public AjaxResult list(FormDefinitionDTO query) {
        return AjaxResult.success(formDefinitionService.selectFormDefinitionPage(query));
    }

    /**
     * 获取表单定义详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取表单定义详情", description = "根据ID查询表单定义信息")
    public AjaxResult getById(@Parameter(description = "表单ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(formDefinitionService.getById(id));
    }

    /**
     * 创建表单定义
     */
    @PostMapping
    @Operation(summary = "创建表单定义", description = "新增表单定义信息")
    public AjaxResult save(@RequestBody FormDefinition formDefinition) {
        return AjaxResult.success(formDefinitionService.saveFormDefinition(formDefinition));
    }

    /**
     * 更新表单定义
     */
    @PutMapping
    @Operation(summary = "更新表单定义", description = "修改表单定义信息")
    public AjaxResult update(@RequestBody FormDefinition formDefinition) {
        return AjaxResult.success(formDefinitionService.updateFormDefinition(formDefinition));
    }

    /**
     * 删除表单定义
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除表单定义", description = "批量删除表单定义")
    public AjaxResult remove(@Parameter(description = "表单ID集合", required = true) @PathVariable Long[] ids) {
        return AjaxResult.success(formDefinitionService.deleteFormDefinitions(ids));
    }

    /**
     * 发布表单定义
     */
    @PostMapping("/publish/{id}")
    @Operation(summary = "发布表单定义", description = "发布表单定义为可用状态")
    public AjaxResult publish(@Parameter(description = "表单ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(formDefinitionService.publishFormDefinition(id));
    }

    /**
     * 停用表单定义
     */
    @PostMapping("/disable/{id}")
    @Operation(summary = "停用表单定义", description = "将表单定义设置为停用状态")
    public AjaxResult disable(@Parameter(description = "表单ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(formDefinitionService.disableFormDefinition(id));
    }

    /**
     * 获取表单版本列表
     */
    @GetMapping("/versions/{code}")
    @Operation(summary = "获取表单版本列表", description = "根据表单编码查询所有版本")
    public AjaxResult getVersionsByCode(@Parameter(description = "表单编码", required = true) @PathVariable String code) {
        List<FormDefinition> versions = formDefinitionService.getVersionsByCode(code);
        return AjaxResult.success(versions);
    }
}