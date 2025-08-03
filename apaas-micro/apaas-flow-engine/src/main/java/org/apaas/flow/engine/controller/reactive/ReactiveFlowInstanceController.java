package org.apaas.flow.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.Query;
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

    /**
     * 获取流程实例列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程实例列表", description = "分页查询流程实例信息")
    @Override
    public Mono<org.apaas.core.query.PageResult<FlowInstance>> page(@RequestBody Query query) {
        return service.selectFlowInstancePage(query);
    }

    /**
     * 获取流程实例详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取流程实例详情", description = "根据ID查询流程实例完整信息")
    @Override
    public Mono<FlowInstance> get(@Parameter(description = "实例ID", required = true) @PathVariable Long id) {
        return service.getFlowInstanceDetail(id);
    }
    
    /**
     * 批量删除流程实例
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除流程实例", description = "批量删除流程实例")
    public Mono<Void> deleteBatch(@RequestBody Long[] ids) {
        return service.deleteByIds(Flux.fromArray(ids)).then(Mono.empty());
    }
    
    /**
     * 批量新增流程实例
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增流程实例", description = "批量新增流程实例")
    public Flux<FlowInstance> addBatch(@RequestBody Flux<FlowInstance> instances) {
        return service.saveBatch(instances);
    }
    
    /**
     * 批量更新流程实例
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新流程实例", description = "批量更新流程实例")
    public Flux<FlowInstance> updateBatch(@RequestBody Flux<FlowInstance> instances) {
        return service.updateBatch(instances);
    }
    
    /**
     * 导出流程实例
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出流程实例", description = "导出流程实例")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入流程实例
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入流程实例", description = "导入流程实例")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
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