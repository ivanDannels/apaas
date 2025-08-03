package org.apaas.flow.engine.repository.reactive;

import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例仓库接口
 */
@Repository
public interface ReactiveFlowInstanceRepository extends ReactiveBaseRepository<FlowInstance, Long> {

    /**
     * 根据业务键查询流程实例
     *
     * @param businessKey 业务键
     * @return 流程实例列表
     */
    Mono<FlowInstance> findByBusinessKey(String businessKey);

    /**
     * 根据流程定义ID查询流程实例
     *
     * @param flowDefinitionId 流程定义ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByFlowDefinitionId(Long flowDefinitionId);

    /**
     * 根据启动人ID查询流程实例
     *
     * @param starterId 启动人ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByStarterId(Long starterId);
}