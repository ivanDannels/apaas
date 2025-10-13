package org.apaas.flow.engine.domain.repository;

import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.flow.engine.domain.model.FlowDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveFlowDefinitionRepository extends ReactiveBaseRepository<FlowDefinition, Long> {
    
    /**
     * 根据流程定义键获取流程定义
     *
     * @param flowKey 流程定义键
     * @return 流程定义信息
     */
    Mono<FlowDefinition> findByFlowKey(String flowKey);
    
    /**
     * 根据流程编码获取所有版本
     *
     * @param code 流程编码
     * @return 流程定义列表
     */
    Flux<FlowDefinition> findByCode(String code);
    
    /**
     * 更新默认版本标识
     *
     * @param code 流程编码
     * @param isDefault 是否默认版本
     * @return 更新结果
     */
    Mono<Void> updateIsDefaultByCode(String code, boolean isDefault);
}