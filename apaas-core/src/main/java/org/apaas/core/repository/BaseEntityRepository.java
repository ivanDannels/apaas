package org.apaas.core.repository;

import org.apaas.core.domain.BaseEntity;
import org.apaas.core.domain.HasDeleted;
import org.apaas.core.domain.HasTenantId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity & HasDeleted & HasTenantId, ID extends Serializable> extends BaseRepository<T, ID> {
    Mono<T> findByIdAndDeletedFalse(ID id);
    Mono<Void> deleteByIdAndDeletedFalse(ID id);
    
    default Mono<T> findByIdAndTenantIdAndDeletedFalse(ID id, Long tenantId) {
        return findByIdAndTenantId(id, tenantId)
            .filter(e -> e.getDeleted() != null && e.getDeleted() == 0);
    }
    
    default Flux<T> findAllByTenantIdAndDeletedFalse(Long tenantId, Pageable pageable) {
        return findAllByTenantId(tenantId, pageable)
            .filter(entity -> entity.getDeleted() != null && entity.getDeleted() == 0);
    }
}