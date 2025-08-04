package org.apaas.flow.engine.service.reactive.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowInstanceRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式流程实例服务实现类
 */
@Service
public class ReactiveFlowInstanceServiceImpl extends BaseServiceImpl<FlowInstance, Long, ReactiveFlowInstanceRepository> implements ReactiveFlowInstanceService {

    public ReactiveFlowInstanceServiceImpl(ReactiveFlowInstanceRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FlowInstance>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<FlowInstance> getFlowInstanceById(Long instanceId) {
        return repository.findById(instanceId);
    }

    @Override
    public Mono<FlowInstance> startInstance(StartInstanceDTO startInstanceDTO) {
        return null;
    }

    @Override
    public Mono<FlowInstance> terminateInstance(Long id) {
        return null;
    }

    @Override
    public Mono<FlowInstance> suspendInstance(Long id) {
        return null;
    }

    @Override
    public Mono<FlowInstance> resumeInstance(Long id) {
        return null;
    }

    @Override
    public Flux<Object> getInstanceTasks(Long id) {
        return null;
    }
}