package org.apaas.design.platform.service.impl;

import org.apaas.core.context.TenantContext;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.design.platform.entity.APIEntity;
import org.apaas.design.platform.repository.APIEntityRepository;
import org.apaas.design.platform.service.APIEntityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class APIEntityServiceImpl extends BaseServiceImpl<APIEntity, Long, APIEntityRepository> implements APIEntityService {

    public APIEntityServiceImpl(APIEntityRepository repository, RedisDomainEventPublisher<EntityChangedEvent<APIEntity>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<APIEntity> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }

    @Override
    public Flux<APIEntity> findByPath(String path) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByPathAndTenantId(path, tenantId));
    }
}