package org.apaas.flow.engine.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务接口
 */
public interface ReactiveFlowInstanceService extends BaseService<FlowInstance, Long> {

    /**
     * 根据流程实例ID获取流程实例
     *
     * @param instanceId 流程实例ID
     * @return 流程实例信息
     */
    Mono<FlowInstance> getFlowInstanceById(Long instanceId);

    Mono<FlowInstance> startInstance(StartInstanceDTO startInstanceDTO);

    Mono<FlowInstance> terminateInstance(Long id);

    Mono<FlowInstance> suspendInstance(Long id);

    Mono<FlowInstance> resumeInstance(Long id);

    Flux<Object> getInstanceTasks(Long id);
}