package org.apaas.integration.service;

import org.apaas.domain.service.BaseService;
import org.apaas.integration.entity.IntegrationEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntegrationService extends BaseService<IntegrationEntity, Long> {
    Flux<IntegrationEntity> findAll();
    Mono<IntegrationEntity> findById(Long id);
    Mono<IntegrationEntity> save(IntegrationEntity entity);
    Mono<Void> deleteById(Long id);
}