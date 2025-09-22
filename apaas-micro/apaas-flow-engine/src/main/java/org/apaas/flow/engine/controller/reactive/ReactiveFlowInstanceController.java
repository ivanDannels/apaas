package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/flow/instance")
@Tag(name = "响应式流程实例管理", description = "响应式流程实例生命周期管理")
public class ReactiveFlowInstanceController extends ReactiveBaseController<FlowInstance, Long, ReactiveFlowInstanceService> {

    public ReactiveFlowInstanceController(ReactiveFlowInstanceService service) {
        super(service);
    }

    /**
     * 启动流程实例
     */
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启动流程实例", description = "根据流程定义ID启动新的流程实例")
    public Mono<FlowInstance> startInstance(@RequestBody StartInstanceDTO startInstanceDTO) {
        return service.startInstance(startInstanceDTO);
    }

    /**
     * 终止流程实例
     */
    @PostMapping(value = "/{id}/terminate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "终止流程实例", description = "将运行中的流程实例终止")
    public Mono<FlowInstance> terminateInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.terminateInstance(id);
    }

    /**
     * 暂停流程实例
     */
    @PostMapping(value = "/{id}/suspend", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "暂停流程实例", description = "暂停运行中的流程实例")
    public Mono<FlowInstance> suspendInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.suspendInstance(id);
    }

    /**
     * 恢复流程实例
     */
    @PostMapping(value = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "恢复流程实例", description = "恢复暂停的流程实例")
    public Mono<FlowInstance> resumeInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.resumeInstance(id);
    }

    /**
     * 获取流程实例的审批记录
     */
    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取审批记录", description = "查询流程实例的所有任务记录")
    public Flux<Object> getInstanceTasks(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.getInstanceTasks(id);
    }
}