package org.apaas.design.platform.service;

import org.apaas.design.platform.entity.DataSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DataSourceService extends BaseService<DataSource, Long> {
    Mono<DataSource> findByName(String name);
    Flux<DataSource> findByType(String type);
}