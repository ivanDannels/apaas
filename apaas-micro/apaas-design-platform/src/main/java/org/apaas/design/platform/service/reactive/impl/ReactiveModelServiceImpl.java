package org.apaas.design.platform.service.reactive.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.design.platform.entity.Model;
import org.apaas.design.platform.repository.ModelRepository;
import org.apaas.design.platform.service.reactive.ReactiveModelService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式模型服务实现类
 */
@Service
public class ReactiveModelServiceImpl extends BaseServiceImpl<Model, Long, ModelRepository> implements ReactiveModelService {

    public ReactiveModelServiceImpl(ModelRepository repository, RedisDomainEventPublisher<EntityChangedEvent<Model>> eventPublisher) {
        super(repository, eventPublisher);
    }

}