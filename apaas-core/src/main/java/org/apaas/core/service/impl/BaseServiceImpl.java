package org.apaas.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.core.context.TenantContext;
import org.apaas.core.domain.BaseEntity;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.core.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable, R extends BaseEntityRepository<T, ID>> implements BaseService<T, ID> {
    
    protected final R repository;
    
    protected final RedisDomainEventPublisher<EntityChangedEvent<T>> eventPublisher;
    
    @Override
    public Mono<T> save(T entity) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            entity.setTenantId(tenantId);
        }
        return repository.save(entity)
                .flatMap(savedEntity -> eventPublisher.publish(new EntityChangedEvent<T>(EntityChangedEvent.OperationType.CREATE, savedEntity))
                        .thenReturn(savedEntity));
    }
    
    @Override
    public Mono<T> findById(ID id) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.findByIdAndTenantId(id, tenantId);
    }
    
    @Override
    public Flux<T> findAll() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.findAllByTenantIdAndDeletedFalse(tenantId, null);
    }
    
    @Override
    public Mono<Void> deleteById(ID id) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        final Long finalTenantId = tenantId;
        return repository.findByIdAndTenantId(id, tenantId)
                .flatMap(entity -> repository.deleteByIdAndTenantId(id, finalTenantId)
                        .then(eventPublisher.publish(new EntityChangedEvent<T>(EntityChangedEvent.OperationType.DELETE, entity))))
                .then();
    }
    
    @Override
    public R getRepository() {
        return repository;
    }
}