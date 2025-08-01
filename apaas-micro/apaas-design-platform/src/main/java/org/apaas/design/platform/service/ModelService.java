package org.apaas.design.platform.service;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelService extends BaseService<Model, Long> {
    Mono<Model> findByName(String name);
    Flux<Model> findByType(String type);
}