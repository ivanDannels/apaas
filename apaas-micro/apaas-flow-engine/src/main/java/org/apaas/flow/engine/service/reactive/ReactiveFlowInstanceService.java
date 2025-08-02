package org.apaas.flow.engine.service.reactive;

import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务接口
 */
public interface ReactiveFlowInstanceService {

    /**
     * 分页查询流程实例
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<Object> selectFlowInstancePage(FlowInstanceDTO query);

    /**
     * 获取流程实例详情
     *
     * @param id 流程实例ID
     * @return 流程实例详情
     */
    Mono<Object> getFlowInstanceDetail(Long id);

    /**
     * 启动流程实例
     *
     * @param startInstanceDTO 启动参数
     * @return 流程实例
     */
    Mono<FlowInstance> startInstance(StartInstanceDTO startInstanceDTO);

    /**
     * 终止流程实例
     *
     * @param id 流程实例ID
     * @return 流程实例
     */
    Mono<FlowInstance> terminateInstance(Long id);

    /**
     * 暂停流程实例
     *
     * @param id 流程实例ID
     * @return 流程实例
     */
    Mono<FlowInstance> suspendInstance(Long id);

    /**
     * 恢复流程实例
     *
     * @param id 流程实例ID
     * @return 流程实例
     */
    Mono<FlowInstance> resumeInstance(Long id);

    /**
     * 获取流程实例的任务记录
     *
     * @param id 流程实例ID
     * @return 任务记录列表
     */
    Flux<Object> getInstanceTasks(Long id);
}