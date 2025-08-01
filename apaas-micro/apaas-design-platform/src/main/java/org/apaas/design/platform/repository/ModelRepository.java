package org.apaas.design.platform.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.design.platform.entity.Model;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ModelRepository extends BaseEntityRepository<Model, Long> {
    Mono<Model> findByNameAndTenantId(String name, Long tenantId);
    Flux<Model> findByTypeAndTenantId(String type, Long tenantId);
}