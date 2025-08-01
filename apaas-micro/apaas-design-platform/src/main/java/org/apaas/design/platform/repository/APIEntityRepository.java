package org.apaas.design.platform.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.design.platform.entity.APIEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface APIEntityRepository extends BaseEntityRepository<APIEntity, Long> {
    Mono<APIEntity> findByNameAndTenantId(String name, Long tenantId);
    Flux<APIEntity> findByPathAndTenantId(String path, Long tenantId);
}