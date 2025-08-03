package org.apaas.flow.engine.repository.reactive;

import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.core.repository.ReactiveBaseRepository;
import reactor.core.publisher.Flux;

/**
 * 响应式流程定义仓库接口
 */
public interface ReactiveFlowDefinitionRepository extends ReactiveBaseRepository<FlowDefinition, Long> {

    /**
     * 根据流程编码查询所有版本，按版本降序排序
     *
     * @param code 流程编码
     * @return 流程定义列表
     */
    Flux<FlowDefinition> findByCodeOrderByVersionDesc(String code);
}