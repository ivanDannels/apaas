package org.apaas.core.repository;

import org.apaas.core.domain.TestEntity;
import org.apaas.core.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TestEntityRepository extends BaseEntityRepository<TestEntity, Long> {
    Mono<TestEntity> findByName(String name);
}