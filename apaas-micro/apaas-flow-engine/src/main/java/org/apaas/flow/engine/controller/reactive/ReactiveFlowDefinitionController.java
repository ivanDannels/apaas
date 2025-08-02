package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.service.reactive.ReactiveFlowDefinitionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/flow/definition")
@Tag(name = "响应式流程定义管理", description = "响应式流程定义CRUD及部署操作")
@RequiredArgsConstructor
public class ReactiveFlowDefinitionController {

    private final ReactiveFlowDefinitionService flowDefinitionService;

    /**
     * 获取流程定义列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程定义列表", description = "分页查询流程定义信息")
    public Mono<Object> list(FlowDefinitionDTO query) {
        return flowDefinitionService.selectFlowDefinitionPage(query);
    }

    /**
     * 获取流程定义详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程定义详情", description = "根据ID查询流程定义信息")
    public Mono<FlowDefinition> getById(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return flowDefinitionService.getById(id);
    }

    /**
     * 创建流程定义
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建流程定义", description = "新增流程定义信息")
    public Mono<FlowDefinition> save(@RequestBody FlowDefinition flowDefinition) {
        return flowDefinitionService.saveFlowDefinition(flowDefinition);
    }

    /**
     * 更新流程定义
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新流程定义", description = "修改流程定义信息")
    public Mono<FlowDefinition> update(@RequestBody FlowDefinition flowDefinition) {
        return flowDefinitionService.updateFlowDefinition(flowDefinition);
    }

    /**
     * 删除流程定义
     */
    @DeleteMapping(value = "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除流程定义", description = "批量删除流程定义")
    public Mono<Void> remove(@Parameter(description = "流程ID集合", required = true) @PathVariable Long[] ids) {
        return flowDefinitionService.deleteFlowDefinitions(Flux.fromArray(ids));
    }

    /**
     * 部署流程定义
     */
    @PostMapping(value = "/deploy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "部署流程定义", description = "发布流程定义为可用状态")
    public Mono<FlowDefinition> deploy(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return flowDefinitionService.deployFlowDefinition(id);
    }

    /**
     * 停用流程定义
     */
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用流程定义", description = "将流程定义设置为停用状态")
    public Mono<FlowDefinition> disable(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return flowDefinitionService.disableFlowDefinition(id);
    }

    /**
     * 获取流程版本列表
     */
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程版本列表", description = "根据流程编码查询所有版本")
    public Flux<FlowDefinition> getVersionsByCode(@Parameter(description = "流程编码", required = true) @PathVariable String code) {
        return flowDefinitionService.getVersionsByCode(code);
    }
}