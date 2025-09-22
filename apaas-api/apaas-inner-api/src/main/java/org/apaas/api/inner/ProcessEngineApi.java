package org.apaas.api.inner;

import org.apaas.core.query.PageResult;
import org.apaas.api.inner.dto.ProcessDefinitionDTO;
import org.apaas.api.inner.dto.ProcessInstanceDTO;
import org.apaas.api.inner.dto.ProcessTaskDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 流程引擎服务API
 */
@RequestMapping("/inner/process-engine")
public interface ProcessEngineApi {
    /**
     * 获取流程定义列表
     */
    @GetMapping("/definitions")
    Mono<PageResult<ProcessDefinitionDTO>> getProcessDefinitions();

    /**
     * 获取流程定义详情
     */
    @GetMapping("/definitions/{id}")
    Mono<ProcessDefinitionDTO> getProcessDefinition(@PathVariable("id") String id);

    /**
     * 创建流程定义
     */
    @PostMapping("/definitions")
    Mono<ProcessDefinitionDTO> createProcessDefinition(@RequestBody ProcessDefinitionDTO definitionDTO);

    /**
     * 更新流程定义
     */
    @PutMapping("/definitions/{id}")
    Mono<ProcessDefinitionDTO> updateProcessDefinition(@PathVariable("id") String id,
                                                             @RequestBody ProcessDefinitionDTO definitionDTO);

    /**
     * 激活/禁用流程定义
     */
    @PutMapping("/definitions/{id}/status")
    Mono<Void> changeProcessDefinitionStatus(@PathVariable("id") String id, @RequestParam boolean active);

    /**
     * 启动流程实例
     */
    @PostMapping("/instances")
    Mono<ProcessInstanceDTO> startProcessInstance(@RequestBody ProcessInstanceDTO instanceDTO);

    /**
     * 获取流程实例列表
     */
    @GetMapping("/instances")
    Mono<PageResult<ProcessInstanceDTO>> getProcessInstances();

    /**
     * 获取流程实例详情
     */
    @GetMapping("/instances/{id}")
    Mono<ProcessInstanceDTO> getProcessInstance(@PathVariable("id") String id);

    /**
     * 暂停/恢复流程实例
     */
    @PutMapping("/instances/{id}/status")
    Mono<Void> changeProcessInstanceStatus(@PathVariable("id") String id, @RequestParam boolean active);

    /**
     * 终止流程实例
     */
    @DeleteMapping("/instances/{id}")
    Mono<Void> terminateProcessInstance(@PathVariable("id") String id);

    /**
     * 获取用户待办任务
     */
    @GetMapping("/tasks/todo")
    Mono<PageResult<ProcessTaskDTO>> getTodoTasks(@RequestParam String userId);

    /**
     * 办理任务
     */
    @PostMapping("/tasks/{id}/complete")
    Mono<Void> completeTask(@PathVariable("id") String id, @RequestBody ProcessTaskDTO taskDTO);

    /**
     * 转办任务
     */
    @PostMapping("/tasks/{id}/transfer")
    Mono<Void> transferTask(@PathVariable("id") String id, @RequestParam String targetUserId);

    /**
     * 获取流程历史
     */
    @GetMapping("/instances/{id}/history")
    Mono<List<ProcessTaskDTO>> getProcessHistory(@PathVariable("id") String id);
}