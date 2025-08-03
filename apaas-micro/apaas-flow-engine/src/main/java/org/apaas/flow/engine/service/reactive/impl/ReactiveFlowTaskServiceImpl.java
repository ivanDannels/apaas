package org.apaas.flow.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.flow.engine.domain.entity.FlowTask;
import org.apaas.flow.engine.repository.FlowTaskRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowTaskService;
import org.apaas.common.exception.BusinessException;
import org.apaas.common.web.domain.PageResult;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.web.domain.BasePageQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式流程任务服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveFlowTaskServiceImpl extends BaseServiceImpl<FlowTask, Long, FlowTaskRepository> implements ReactiveFlowTaskService {

    private final FlowTaskRepository flowTaskRepository;

    /**
     * 分页查询流程任务
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<FlowTask>> selectFlowTaskPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return flowTaskRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据流程实例ID查询流程任务列表
     *
     * @param flowInstanceId 流程实例ID
     * @return 流程任务列表
     */
    @Override
    public Flux<FlowTask> getFlowTasksByFlowInstanceId(Long flowInstanceId) {
        return flowTaskRepository.findByFlowInstanceId(flowInstanceId);
    }

    /**
     * 根据任务定义键查询流程任务列表
     *
     * @param taskDefinitionKey 任务定义键
     * @return 流程任务列表
     */
    @Override
    public Flux<FlowTask> getFlowTasksByTaskDefinitionKey(String taskDefinitionKey) {
        return flowTaskRepository.findByTaskDefinitionKey(taskDefinitionKey);
    }

    /**
     * 根据任务ID签收任务
     *
     * @param taskId 任务ID
     * @param assignee 签收人
     * @return 签收结果
     */
    @Override
    public Mono<Boolean> claimTask(Long taskId, Long assignee) {
        return super.findById(taskId)
                .switchIfEmpty(Mono.error(new BusinessException("任务不存在")))
                .flatMap(flowTask -> {
                    if (flowTask.getAssignee() != null) {
                        return Mono.error(new BusinessException("任务已被签收"));
                    }
                    flowTask.setAssignee(assignee);
                    return super.save(flowTask).map(saved -> true);
                });
    }

    /**
     * 根据任务ID完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     * @return 完成结果
     */
    @Override
    public Mono<Boolean> completeTask(Long taskId, java.util.Map<String, Object> variables) {
        return super.findById(taskId)
                .switchIfEmpty(Mono.error(new BusinessException("任务不存在")))
                .flatMap(flowTask -> {
                    // 这里需要实现完成任务的逻辑
                    // 暂时返回true，实际开发中需要实现具体的完成逻辑
                    return Mono.just(true);
                });
    }

    /**
     * 根据任务ID委派任务
     *
     * @param taskId 任务ID
     * @param delegateTo 委派人
     * @return 委派结果
     */
    @Override
    public Mono<Boolean> delegateTask(Long taskId, Long delegateTo) {
        return super.findById(taskId)
                .switchIfEmpty(Mono.error(new BusinessException("任务不存在")))
                .flatMap(flowTask -> {
                    flowTask.setDelegatedTo(delegateTo);
                    return super.save(flowTask).map(saved -> true);
                });
    }

    /**
     * 根据任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteTask(Long taskId) {
        return super.findById(taskId)
                .switchIfEmpty(Mono.error(new BusinessException("任务不存在")))
                .flatMap(flowTask -> {
                    return super.deleteById(taskId).map(deleted -> true);
                });
    }
}