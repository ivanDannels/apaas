package org.apaas.flow.engine.service.reactive;

import org.apaas.flow.engine.domain.entity.FlowInstance;
import org.apaas.common.web.domain.PageResult;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务接口
 */
public interface ReactiveFlowInstanceService {

    /**
     * 根据流程实例ID获取流程实例
     *
     * @param instanceId 流程实例ID
     * @return 流程实例信息
     */
    Mono<FlowInstance> getFlowInstanceById(String instanceId);

    /**
     * 分页获取流程实例列表
     *
     * @param pageable 分页参数
     * @return 流程实例分页结果
     */
    Mono<PageResult<FlowInstance>> getFlowInstancePage(Pageable pageable);

    /**
     * 启动流程实例
     *
     * @param flowDefinitionId 流程定义ID
     * @param starterId 启动人ID
     * @param variables 流程变量
     * @return 流程实例信息
     */
    Mono<FlowInstance> startFlowInstance(Long flowDefinitionId, Long starterId, java.util.Map<String, Object> variables);

    /**
     * 挂起流程实例
     *
     * @param instanceId 流程实例ID
     * @return 挂起结果
     */
    Mono<Void> suspendFlowInstance(String instanceId);

    /**
     * 激活流程实例
     *
     * @param instanceId 流程实例ID
     * @return 激活结果
     */
    Mono<Void> activateFlowInstance(String instanceId);

    /**
     * 终止流程实例
     *
     * @param instanceId 流程实例ID
     * @return 终止结果
     */
    Mono<Void> terminateFlowInstance(String instanceId);
}