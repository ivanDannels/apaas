package org.apaas.api.open;

import org.apaas.api.common.ApiResponse;
import org.apaas.api.common.BasePageRequest;
import org.apaas.api.open.dto.ScheduleTaskDTO;
import org.apaas.api.open.dto.TaskExecutionLogDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务调度服务API
 */
@RequestMapping("/open/task-scheduler")
public interface TaskSchedulerApi {
    /**
     * 获取任务列表
     */
    @GetMapping("/tasks")
    ApiResponse<PageResult<ScheduleTaskDTO>> getTasks(BasePageRequest request);

    /**
     * 获取任务详情
     */
    @GetMapping("/tasks/{id}")
    ApiResponse<ScheduleTaskDTO> getTask(@PathVariable("id") String id);

    /**
     * 创建任务
     */
    @PostMapping("/tasks")
    ApiResponse<ScheduleTaskDTO> createTask(@RequestBody ScheduleTaskDTO taskDTO);

    /**
     * 更新任务
     */
    @PutMapping("/tasks/{id}")
    ApiResponse<ScheduleTaskDTO> updateTask(@PathVariable("id") String id,
                                           @RequestBody ScheduleTaskDTO taskDTO);

    /**
     * 启用/禁用任务
     */
    @PutMapping("/tasks/{id}/status")
    ApiResponse<Void> changeTaskStatus(@PathVariable("id") String id, @RequestParam boolean enabled);

    /**
     * 删除任务
     */
    @DeleteMapping("/tasks/{id}")
    ApiResponse<Void> deleteTask(@PathVariable("id") String id);

    /**
     * 手动触发任务
     */
    @PostMapping("/tasks/{id}/trigger")
    ApiResponse<Void> triggerTask(@PathVariable("id") String id);

    /**
     * 获取任务执行日志
     */
    @GetMapping("/tasks/{id}/logs")
    ApiResponse<PageResult<TaskExecutionLogDTO>> getTaskLogs(@PathVariable("id") String id, BasePageRequest request);

    /**
     * 获取任务执行状态
     */
    @GetMapping("/tasks/{id}/status")
    ApiResponse<String> getTaskExecutionStatus(@PathVariable("id") String id);

    /**
     * 暂停任务执行
     */
    @PostMapping("/tasks/{id}/pause")
    ApiResponse<Void> pauseTaskExecution(@PathVariable("id") String id);

    /**
     * 恢复任务执行
     */
    @PostMapping("/tasks/{id}/resume")
    ApiResponse<Void> resumeTaskExecution(@PathVariable("id") String id);
}