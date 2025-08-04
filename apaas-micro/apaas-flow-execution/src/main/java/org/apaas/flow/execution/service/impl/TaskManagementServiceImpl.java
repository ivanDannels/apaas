package org.apaas.flow.execution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.apaas.flow.execution.repository.WorkflowTaskRepository;
import org.apaas.flow.execution.service.TaskManagementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TaskManagementServiceImpl extends BaseServiceImpl<WorkflowTask, Long, WorkflowTaskRepository> implements TaskManagementService {

    public TaskManagementServiceImpl(WorkflowTaskRepository repository) {
        super(repository);
    }

    /**
     * 查询用户任务列表
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    @Override
    public Mono<Result<Flux<WorkflowTask>>> getUserTasks(Long userId) {
        return null;
    }

    /**
     * 领取任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    @Override
    public Mono<Result<WorkflowTask>> claimTask(Long taskId, Long userId) {
        return null;
    }

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务
     */
    @Override
    public Mono<Result<WorkflowTask>> completeTask(Long taskId, Long userId) {
        return null;
    }

    /**
     * 转办任务
     *
     * @param taskId     任务ID
     * @param fromUserId 原处理人
     * @param toUserId   新处理人
     * @return 任务
     */
    @Override
    public Mono<Result<WorkflowTask>> transferTask(Long taskId, Long fromUserId, Long toUserId) {
        return null;
    }
}