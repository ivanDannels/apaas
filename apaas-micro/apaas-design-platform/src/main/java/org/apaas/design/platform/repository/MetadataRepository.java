package org.apaas.design.platform.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.design.platform.entity.Metadata;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MetadataRepository extends BaseEntityRepository<Metadata, Long> {
    Mono<Metadata> findByNameAndTenantId(String name, Long tenantId);
    Flux<Metadata> findByTypeAndTenantId(String type, Long tenantId);
}