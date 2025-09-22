package org.apaas.core.service;

import org.apaas.core.domain.TestEntity;
import reactor.core.publisher.Mono;

public interface TestEntityService extends BaseService<TestEntity, Long> {
    Mono<TestEntity> findByName(String name);
}