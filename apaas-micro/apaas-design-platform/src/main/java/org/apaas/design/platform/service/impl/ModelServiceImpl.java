package org.apaas.design.platform.service.impl;

import org.apaas.core.context.TenantContext;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.design.platform.entity.Model;
import org.apaas.design.platform.repository.ModelRepository;
import org.apaas.design.platform.service.ModelService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ModelServiceImpl extends BaseServiceImpl<Model, Long, ModelRepository> implements ModelService {

    public ModelServiceImpl(ModelRepository repository, RedisDomainEventPublisher<EntityChangedEvent<Model>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<Model> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }
    
    @Override
    public Flux<Model> findByType(String type) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByTypeAndTenantId(type, tenantId));
    }
}