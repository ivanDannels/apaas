package org.apaas.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.core.context.TenantContext;
import org.apaas.core.domain.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.core.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * @author ivan
 */
@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable, R extends ReactiveBaseRepository<T, ID>> implements BaseService<T, ID> {
    
    protected final R repository;

    @Override
    public Mono<T> save(T entity) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            entity.setTenantId(tenantId);
        }
        return repository.save(entity);
    }
    
    @Override
    public Flux<T> saveAll(Iterable<T> entities) {
        // 为每个实体设置租户ID
        entities.forEach(entity -> {
            Long tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                entity.setTenantId(tenantId);
            }
        });
        
        return repository.saveAll(entities);
    }
    
    @Override
    public Flux<T> saveBatch(Flux<T> entities) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        final Long finalTenantId = tenantId;
        
        return entities.doOnNext(entity -> entity.setTenantId(finalTenantId)).flatMap(repository::save);
    }
    
    @Override
    public Flux<T> updateBatch(Flux<T> entities) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        final Long finalTenantId = tenantId;
        
        return entities.doOnNext(entity -> entity.setTenantId(finalTenantId)).flatMap(repository::save);
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
        return repository.findByIdAndTenantId(id, tenantId).flatMap(repository::delete);
    }
    
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return repository.deleteAllById(ids);
    }
    
    @Override
    public Mono<PageResult<T>> selectPage(Query query) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.selectPage(tenantId, query);
    }
    
    @Override
    public Mono<byte[]> export(Query query) {
        // 默认实现，子类可以覆盖
        return Mono.empty();
    }
    
    @Override
    public Mono<Void> importData(byte[] data) {
        // 默认实现，子类可以覆盖
        return Mono.empty();
    }
    
    @Override
    public R getRepository() {
        return repository;
    }
}