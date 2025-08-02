package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
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
@RequiredArgsConstructor
public class ReactiveFlowInstanceController {

    private final ReactiveFlowInstanceService flowInstanceService;

    /**
     * 获取流程实例列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程实例列表", description = "分页查询流程实例信息")
    public Mono<Object> list(FlowInstanceDTO query) {
        return flowInstanceService.selectFlowInstancePage(query);
    }

    /**
     * 获取流程实例详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程实例详情", description = "根据ID查询流程实例完整信息")
    public Mono<Object> getById(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return flowInstanceService.getFlowInstanceDetail(id);
    }

    /**
     * 启动流程实例
     */
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启动流程实例", description = "根据流程定义ID启动新的流程实例")
    public Mono<FlowInstance> startInstance(@RequestBody StartInstanceDTO startInstanceDTO) {
        return flowInstanceService.startInstance(startInstanceDTO);
    }

    /**
     * 终止流程实例
     */
    @PostMapping(value = "/{id}/terminate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "终止流程实例", description = "将运行中的流程实例终止")
    public Mono<FlowInstance> terminateInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return flowInstanceService.terminateInstance(id);
    }

    /**
     * 暂停流程实例
     */
    @PostMapping(value = "/{id}/suspend", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "暂停流程实例", description = "暂停运行中的流程实例")
    public Mono<FlowInstance> suspendInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return flowInstanceService.suspendInstance(id);
    }

    /**
     * 恢复流程实例
     */
    @PostMapping(value = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "恢复流程实例", description = "恢复暂停的流程实例")
    public Mono<FlowInstance> resumeInstance(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return flowInstanceService.resumeInstance(id);
    }

    /**
     * 获取流程实例的审批记录
     */
    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取审批记录", description = "查询流程实例的所有任务记录")
    public Flux<Object> getInstanceTasks(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return flowInstanceService.getInstanceTasks(id);
    }
}