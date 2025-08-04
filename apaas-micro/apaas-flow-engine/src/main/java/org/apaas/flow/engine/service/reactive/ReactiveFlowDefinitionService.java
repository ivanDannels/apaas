package org.apaas.flow.engine.service.reactive;

import org.apaas.core.query.PageResult;
import org.apaas.core.service.BaseService;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程定义服务接口
 */
public interface ReactiveFlowDefinitionService extends BaseService<FlowDefinition, Long> {

    /**
     * 根据流程定义键获取流程定义
     *
     * @param flowKey 流程定义键
     * @return 流程定义信息
     */
    Mono<FlowDefinition> getFlowDefinitionByKey(String flowKey);

    /**
     * 分页获取流程定义列表
     *
     * @param pageable 分页参数
     * @return 流程定义分页结果
     */
    Mono<PageResult<FlowDefinition>> getFlowDefinitionPage(Pageable pageable);

    /**
     * 添加流程定义
     *
     * @param flowDefinition 流程定义信息
     * @return 添加结果
     */
    Mono<FlowDefinition> addFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 更新流程定义
     *
     * @param flowDefinition 流程定义信息
     * @return 更新结果
     */
    Mono<FlowDefinition> updateFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 删除流程定义
     *
     * @param id 流程定义ID
     * @return 删除结果
     */
    Mono<Void> deleteFlowDefinition(Long id);

    /**
     * 部署流程定义
     *
     * @param id 流程定义ID
     * @return 部署结果
     */
    Mono<FlowDefinition> deployFlowDefinition(Long id);

    /**
     * 挂起流程定义
     *
     * @param id 流程定义ID
     * @return 挂起结果
     */
    Mono<Void> suspendFlowDefinition(Long id);

    /**
     * 激活流程定义
     *
     * @param id 流程定义ID
     * @return 激活结果
     */
    Mono<Void> activateFlowDefinition(Long id);

    Mono<FlowDefinition> disableFlowDefinition(Long id);

    Flux<FlowDefinition> getVersionsByCode(String code);
}