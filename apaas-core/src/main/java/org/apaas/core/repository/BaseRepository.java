package org.apaas.core.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends ReactiveCrudRepository<T, ID> {
    Mono<T> findByIdAndTenantId(ID id, Long tenantId);
    Flux<T> findAllByTenantId(Long tenantId, Pageable pageable);
    Mono<Void> deleteByIdAndTenantId(ID id, Long tenantId);
    Mono<Boolean> existsByIdAndTenantId(ID id, Long tenantId);
    
    default Flux<T> findAllByTenantId(Long tenantId) {
        return findAllByTenantId(tenantId, null);
    }
}