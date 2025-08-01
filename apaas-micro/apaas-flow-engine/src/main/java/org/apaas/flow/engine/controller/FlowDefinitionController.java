package org.apaas.flow.engine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.service.FlowDefinitionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义控制器
 */
@RestController
@RequestMapping("/flow/definition")
@Tag(name = "流程定义管理", description = "流程定义CRUD及部署操作")
public class FlowDefinitionController {

    @Resource
    private FlowDefinitionService flowDefinitionService;

    /**
     * 获取流程定义列表
     */
    @GetMapping
    @Operation(summary = "获取流程定义列表", description = "分页查询流程定义信息")
    public AjaxResult list(FlowDefinitionDTO query) {
        return AjaxResult.success(flowDefinitionService.selectFlowDefinitionPage(query));
    }

    /**
     * 获取流程定义详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取流程定义详情", description = "根据ID查询流程定义信息")
    public AjaxResult getById(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowDefinitionService.getById(id));
    }

    /**
     * 创建流程定义
     */
    @PostMapping
    @Operation(summary = "创建流程定义", description = "新增流程定义信息")
    public AjaxResult save(@RequestBody FlowDefinition flowDefinition) {
        return AjaxResult.success(flowDefinitionService.saveFlowDefinition(flowDefinition));
    }

    /**
     * 更新流程定义
     */
    @PutMapping
    @Operation(summary = "更新流程定义", description = "修改流程定义信息")
    public AjaxResult update(@RequestBody FlowDefinition flowDefinition) {
        return AjaxResult.success(flowDefinitionService.updateFlowDefinition(flowDefinition));
    }

    /**
     * 删除流程定义
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除流程定义", description = "批量删除流程定义")
    public AjaxResult remove(@Parameter(description = "流程ID集合", required = true) @PathVariable Long[] ids) {
        return AjaxResult.success(flowDefinitionService.deleteFlowDefinitions(ids));
    }

    /**
     * 部署流程定义
     */
    @PostMapping("/deploy/{id}")
    @Operation(summary = "部署流程定义", description = "发布流程定义为可用状态")
    public AjaxResult deploy(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowDefinitionService.deployFlowDefinition(id));
    }

    /**
     * 停用流程定义
     */
    @PostMapping("/disable/{id}")
    @Operation(summary = "停用流程定义", description = "将流程定义设置为停用状态")
    public AjaxResult disable(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowDefinitionService.disableFlowDefinition(id));
    }

    /**
     * 获取流程版本列表
     */
    @GetMapping("/versions/{code}")
    @Operation(summary = "获取流程版本列表", description = "根据流程编码查询所有版本")
    public AjaxResult getVersionsByCode(@Parameter(description = "流程编码", required = true) @PathVariable String code) {
        List<FlowDefinition> versions = flowDefinitionService.getVersionsByCode(code);
        return AjaxResult.success(versions);
    }
}