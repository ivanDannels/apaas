package org.apaas.flow.engine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.service.FlowInstanceService;
import org.springframework.web.bind.annotation.*;

/**
 * 流程实例控制器
 */
@RestController
@RequestMapping("/flow/instance")
@Tag(name = "流程实例管理", description = "流程实例生命周期管理")
public class FlowInstanceController {

    @Resource
    private FlowInstanceService flowInstanceService;

    /**
     * 获取流程实例列表
     */
    @GetMapping
    @Operation(summary = "获取流程实例列表", description = "分页查询流程实例信息")
    public AjaxResult list(FlowInstanceDTO query) {
        return AjaxResult.success(flowInstanceService.selectFlowInstancePage(query));
    }

    /**
     * 获取流程实例详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取流程实例详情", description = "根据ID查询流程实例完整信息")
    public AjaxResult getById(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowInstanceService.getFlowInstanceDetail(id));
    }

    /**
     * 启动流程实例
     */
    @PostMapping("/start")
    @Operation(summary = "启动流程实例", description = "根据流程定义ID启动新的流程实例")
    public AjaxResult startInstance(@RequestBody StartInstanceDTO startInstanceDTO) {
        return AjaxResult.success(flowInstanceService.startInstance(startInstanceDTO));
    }

    /**
     * 终止流程实例
     */
    @PostMapping("/{id}/terminate")
    @Operation(summary = "终止流程实例", description = "将运行中的流程实例终止")
    public AjaxResult terminateInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowInstanceService.terminateInstance(id));
    }

    /**
     * 暂停流程实例
     */
    @PostMapping("/{id}/suspend")
    @Operation(summary = "暂停流程实例", description = "暂停运行中的流程实例")
    public AjaxResult suspendInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowInstanceService.suspendInstance(id));
    }

    /**
     * 恢复流程实例
     */
    @PostMapping("/{id}/resume")
    @Operation(summary = "恢复流程实例", description = "恢复暂停的流程实例")
    public AjaxResult resumeInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowInstanceService.resumeInstance(id));
    }

    /**
     * 获取流程实例的审批记录
     */
    @GetMapping("/{id}/tasks")
    @Operation(summary = "获取审批记录", description = "查询流程实例的所有任务记录")
    public AjaxResult getInstanceTasks(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return AjaxResult.success(flowInstanceService.getInstanceTasks(id));
    }
}