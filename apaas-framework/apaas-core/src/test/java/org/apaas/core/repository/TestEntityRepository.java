package org.apaas.core.repository;

import org.apaas.core.domain.TestEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TestEntityRepository extends ReactiveBaseRepository<TestEntity, Long> {
    Mono<TestEntity> findByName(String name);
}