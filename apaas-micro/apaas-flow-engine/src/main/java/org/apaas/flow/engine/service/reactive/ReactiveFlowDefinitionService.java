package org.apaas.flow.engine.service.reactive;

import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义服务接口
 */
public interface ReactiveFlowDefinitionService {

    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<Object> selectFlowDefinitionPage(FlowDefinitionDTO query);

    /**
     * 根据ID获取流程定义
     *
     * @param id 流程定义ID
     * @return 流程定义
     */
    Mono<FlowDefinition> getById(Long id);

    /**
     * 保存流程定义
     *
     * @param flowDefinition 流程定义
     * @return 保存后的流程定义
     */
    Mono<FlowDefinition> saveFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 更新流程定义
     *
     * @param flowDefinition 流程定义
     * @return 更新后的流程定义
     */
    Mono<FlowDefinition> updateFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 批量删除流程定义
     *
     * @param ids 流程定义ID集合
     * @return 完成信号
     */
    Mono<Void> deleteFlowDefinitions(Flux<Long> ids);

    /**
     * 部署流程定义
     *
     * @param id 流程定义ID
     * @return 部署后的流程定义
     */
    Mono<FlowDefinition> deployFlowDefinition(Long id);

    /**
     * 停用流程定义
     *
     * @param id 流程定义ID
     * @return 停用后的流程定义
     */
    Mono<FlowDefinition> disableFlowDefinition(Long id);

    /**
     * 根据流程编码获取所有版本
     *
     * @param code 流程编码
     * @return 流程定义版本列表
     */
    Flux<FlowDefinition> getVersionsByCode(String code);
}