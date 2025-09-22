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

    public ReactiveFlowDefinitionController(ReactiveFlowDefinitionService service) {
        super(service);
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