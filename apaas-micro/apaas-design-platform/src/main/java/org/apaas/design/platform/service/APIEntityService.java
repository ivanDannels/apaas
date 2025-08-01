package org.apaas.design.platform.service;

import org.apaas.design.platform.entity.APIEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface APIEntityService extends BaseService<APIEntity, Long> {
    Mono<APIEntity> findByName(String name);
    Flux<APIEntity> findByPath(String path);
}