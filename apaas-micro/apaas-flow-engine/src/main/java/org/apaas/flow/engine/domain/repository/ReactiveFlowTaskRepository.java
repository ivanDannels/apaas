package org.apaas.flow.engine.domain.repository;

import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.flow.engine.domain.model.FlowTask;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveFlowTaskRepository extends ReactiveBaseRepository<FlowTask, Long> {
    
    /**
     * 根据流程实例ID获取任务列表
     *
     * @param instanceId 流程实例ID
     * @return 任务列表
     */
    Flux<FlowTask> findByInstanceId(Long instanceId);
    
    /**
     * 根据任务定义键获取任务
     *
     * @param taskKey 任务定义键
     * @return 任务
     */
    Mono<FlowTask> findByTaskKey(String taskKey);
    
    /**
     * 根据处理人ID获取任务列表
     *
     * @param assigneeId 处理人ID
     * @return 任务列表
     */
    Flux<FlowTask> findByAssigneeId(Long assigneeId);
    
    /**
     * 根据任务状态获取任务列表
     *
     * @param status 任务状态
     * @return 任务列表
     */
    Flux<FlowTask> findByStatus(Integer status);
    
    /**
     * 根据流程实例ID获取任务列表
     *
     * @param flowInstanceId 流程实例ID
     * @return 任务列表
     */
    Flux<FlowTask> findByFlowInstanceId(Long flowInstanceId);
    
    /**
     * 根据任务定义键获取任务列表
     *
     * @param taskDefinitionKey 任务定义键
     * @return 任务列表
     */
    Flux<FlowTask> findByTaskDefinitionKey(String taskDefinitionKey);
}