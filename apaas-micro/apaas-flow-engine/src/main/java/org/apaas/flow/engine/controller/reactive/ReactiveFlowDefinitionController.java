package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.Query;
import org.apaas.core.web.controller.ReactiveBaseController;
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
public class ReactiveFlowDefinitionController extends ReactiveBaseController<FlowDefinition, Long, ReactiveFlowDefinitionService> {

    /**
     * 获取流程定义列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程定义列表", description = "分页查询流程定义信息")
    @Override
    public Mono<org.apaas.core.query.PageResult<FlowDefinition>> page(@RequestBody Query query) {
        return service.selectFlowDefinitionPage(query);
    }

    /**
     * 获取流程定义详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程定义详情", description = "根据ID查询流程定义信息")
    @Override
    public Mono<FlowDefinition> get(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return super.get(id);
    }

    /**
     * 创建流程定义
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建流程定义", description = "新增流程定义信息")
    @Override
    public Mono<FlowDefinition> add(@RequestBody FlowDefinition flowDefinition) {
        return service.saveFlowDefinition(flowDefinition);
    }

    /**
     * 更新流程定义
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新流程定义", description = "修改流程定义信息")
    @Override
    public Mono<FlowDefinition> update(@RequestBody FlowDefinition flowDefinition) {
        return service.updateFlowDefinition(flowDefinition);
    }

    /**
     * 删除流程定义
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除流程定义", description = "删除流程定义")
    @Override
    public Mono<Void> delete(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return service.deleteFlowDefinitions(Flux.just(id));
    }
    
    /**
     * 批量删除流程定义
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除流程定义", description = "批量删除流程定义")
    public Mono<Void> deleteBatch(@RequestBody Long[] ids) {
        return service.deleteByIds(Flux.fromArray(ids)).then(Mono.empty());
    }
    
    /**
     * 批量新增流程定义
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增流程定义", description = "批量新增流程定义")
    public Flux<FlowDefinition> addBatch(@RequestBody Flux<FlowDefinition> flows) {
        return service.saveBatch(flows);
    }
    
    /**
     * 批量更新流程定义
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新流程定义", description = "批量更新流程定义")
    public Flux<FlowDefinition> updateBatch(@RequestBody Flux<FlowDefinition> flows) {
        return service.updateBatch(flows);
    }
    
    /**
     * 导出流程定义
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出流程定义", description = "导出流程定义")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入流程定义
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入流程定义", description = "导入流程定义")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
    }

    /**
     * 部署流程定义
     */
    @PostMapping(value = "/deploy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "部署流程定义", description = "发布流程定义为可用状态")
    public Mono<FlowDefinition> deploy(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return service.deployFlowDefinition(id);
    }

    /**
     * 停用流程定义
     */
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用流程定义", description = "将流程定义设置为停用状态")
    public Mono<FlowDefinition> disable(@Parameter(description = "流程ID", required = true) @PathVariable Long id) {
        return service.disableFlowDefinition(id);
    }

    /**
     * 获取流程版本列表
     */
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程版本列表", description = "根据流程编码查询所有版本")
    public Flux<FlowDefinition> getVersionsByCode(@Parameter(description = "流程编码", required = true) @PathVariable String code) {
        return service.getVersionsByCode(code);
    }
}