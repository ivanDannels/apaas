package org.apaas.api.inner;

import org.apaas.core.query.PageResult;
import org.apaas.api.inner.dto.ProcessDefinitionDTO;
import org.apaas.api.inner.dto.ProcessInstanceDTO;
import org.apaas.api.inner.dto.ProcessTaskDTO;
import org.springframework.web.bind.annotation.*;

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
    ApiResponse<PageResult<ProcessDefinitionDTO>> getProcessDefinitions(BasePageRequest request);

    /**
     * 获取流程定义详情
     */
    @GetMapping("/definitions/{id}")
    ApiResponse<ProcessDefinitionDTO> getProcessDefinition(@PathVariable("id") String id);

    /**
     * 创建流程定义
     */
    @PostMapping("/definitions")
    ApiResponse<ProcessDefinitionDTO> createProcessDefinition(@RequestBody ProcessDefinitionDTO definitionDTO);

    /**
     * 更新流程定义
     */
    @PutMapping("/definitions/{id}")
    ApiResponse<ProcessDefinitionDTO> updateProcessDefinition(@PathVariable("id") String id,
                                                             @RequestBody ProcessDefinitionDTO definitionDTO);

    /**
     * 激活/禁用流程定义
     */
    @PutMapping("/definitions/{id}/status")
    ApiResponse<Void> changeProcessDefinitionStatus(@PathVariable("id") String id, @RequestParam boolean active);

    /**
     * 启动流程实例
     */
    @PostMapping("/instances")
    ApiResponse<ProcessInstanceDTO> startProcessInstance(@RequestBody ProcessInstanceDTO instanceDTO);

    /**
     * 获取流程实例列表
     */
    @GetMapping("/instances")
    ApiResponse<PageResult<ProcessInstanceDTO>> getProcessInstances(BasePageRequest request);

    /**
     * 获取流程实例详情
     */
    @GetMapping("/instances/{id}")
    ApiResponse<ProcessInstanceDTO> getProcessInstance(@PathVariable("id") String id);

    /**
     * 暂停/恢复流程实例
     */
    @PutMapping("/instances/{id}/status")
    ApiResponse<Void> changeProcessInstanceStatus(@PathVariable("id") String id, @RequestParam boolean active);

    /**
     * 终止流程实例
     */
    @DeleteMapping("/instances/{id}")
    ApiResponse<Void> terminateProcessInstance(@PathVariable("id") String id);

    /**
     * 获取用户待办任务
     */
    @GetMapping("/tasks/todo")
    ApiResponse<PageResult<ProcessTaskDTO>> getTodoTasks(BasePageRequest request, @RequestParam String userId);

    /**
     * 办理任务
     */
    @PostMapping("/tasks/{id}/complete")
    ApiResponse<Void> completeTask(@PathVariable("id") String id, @RequestBody ProcessTaskDTO taskDTO);

    /**
     * 转办任务
     */
    @PostMapping("/tasks/{id}/transfer")
    ApiResponse<Void> transferTask(@PathVariable("id") String id, @RequestParam String targetUserId);

    /**
     * 获取流程历史
     */
    @GetMapping("/instances/{id}/history")
    ApiResponse<List<ProcessTaskDTO>> getProcessHistory(@PathVariable("id") String id);
}