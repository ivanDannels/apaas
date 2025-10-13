package org.apaas.flow.engine.domain.repository;

import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.flow.engine.domain.model.FlowInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveFlowInstanceRepository extends ReactiveBaseRepository<FlowInstance, Long> {
    
    /**
     * 根据流程定义ID获取流程实例列表
     *
     * @param definitionId 流程定义ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByDefinitionId(Long definitionId);
    
    /**
     * 根据业务主键获取流程实例
     *
     * @param businessKey 业务主键
     * @return 流程实例
     */
    Mono<FlowInstance> findByBusinessKey(String businessKey);
    
    /**
     * 根据启动用户ID获取流程实例列表
     *
     * @param startUserId 启动用户ID
     * @return 流程实例列表
     */
    Flux<FlowInstance> findByStartUserId(Long startUserId);
}