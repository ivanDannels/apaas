package org.apaas.design.platform.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.design.platform.entity.DataSource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DataSourceRepository extends ReactiveBaseRepository<DataSource, Long> {
    Mono<DataSource> findByNameAndTenantId(String name, Long tenantId);
    Flux<DataSource> findByTypeAndTenantId(String type, Long tenantId);
}