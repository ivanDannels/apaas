package org.apaas.monitor.service;

import org.apaas.monitor.entity.MonitorEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MonitorService {
    Flux<MonitorEntity> findAll();
    Mono<MonitorEntity> findById(Long id);
    Mono<MonitorEntity> save(MonitorEntity entity);
    Mono<Void> deleteById(Long id);
}