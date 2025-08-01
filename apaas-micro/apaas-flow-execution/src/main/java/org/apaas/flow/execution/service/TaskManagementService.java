package org.apaas.flow.execution.service;

import org.apaas.core.service.BaseService;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskManagementService extends BaseService<WorkflowTask, Long> {
    
    /**
     * 查询用户任务列表
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    Mono<Result<Flux<WorkflowTask>>> getUserTasks(Long userId);
    
    /**
     * 领取任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    Mono<Result<WorkflowTask>> claimTask(Long taskId, Long userId);
    
    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    Mono<Result<WorkflowTask>> completeTask(Long taskId, Long userId);
    
    /**
     * 转办任务
     *
     * @param taskId 任务ID
     * @param fromUserId 原处理人
     * @param toUserId 新处理人
     * @return 任务
     */
    Mono<Result<WorkflowTask>> transferTask(Long taskId, Long fromUserId, Long toUserId);
    
}