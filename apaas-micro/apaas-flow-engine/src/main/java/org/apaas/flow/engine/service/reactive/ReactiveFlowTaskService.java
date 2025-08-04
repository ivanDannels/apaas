package org.apaas.flow.engine.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.flow.engine.entity.FlowTask;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式流程任务服务接口
 */
public interface ReactiveFlowTaskService extends BaseService<FlowTask, Long> {

    /**
     * 根据流程实例ID查询流程任务列表
     *
     * @param flowInstanceId 流程实例ID
     * @return 流程任务列表
     */
    Flux<FlowTask> getFlowTasksByFlowInstanceId(Long flowInstanceId);

    /**
     * 根据任务定义键查询流程任务列表
     *
     * @param taskDefinitionKey 任务定义键
     * @return 流程任务列表
     */
    Flux<FlowTask> getFlowTasksByTaskDefinitionKey(String taskDefinitionKey);

    /**
     * 根据任务ID签收任务
     *
     * @param taskId 任务ID
     * @param assignee 签收人
     * @return 签收结果
     */
    Mono<Boolean> claimTask(Long taskId, Long assignee);

    /**
     * 根据任务ID完成任务
     *
     * @param taskId 任务ID
     * @param variables 任务变量
     * @return 完成结果
     */
    Mono<Boolean> completeTask(Long taskId, java.util.Map<String, Object> variables);

    /**
     * 根据任务ID委派任务
     *
     * @param taskId 任务ID
     * @param delegateTo 委派人
     * @return 委派结果
     */
    Mono<Boolean> delegateTask(Long taskId, Long delegateTo);

    /**
     * 根据任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除结果
     */
    Mono<Boolean> deleteTask(Long taskId);
}