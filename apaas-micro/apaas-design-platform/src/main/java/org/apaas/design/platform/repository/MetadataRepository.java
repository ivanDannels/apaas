package org.apaas.design.platform.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.design.platform.entity.Metadata;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MetadataRepository extends ReactiveBaseRepository<Metadata, Long> {
    Mono<Metadata> findByNameAndTenantId(String name, Long tenantId);
    Flux<Metadata> findByTypeAndTenantId(String type, Long tenantId);
}