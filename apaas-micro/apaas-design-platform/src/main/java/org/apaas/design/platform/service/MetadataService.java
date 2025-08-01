package org.apaas.design.platform.service;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.Metadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MetadataService extends BaseService<Metadata, Long> {
    Mono<Metadata> findByName(String name);
    Flux<Metadata> findByType(String type);
}