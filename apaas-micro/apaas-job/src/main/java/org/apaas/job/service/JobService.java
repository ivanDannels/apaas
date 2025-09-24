package org.apaas.job.service;

import org.apaas.domain.service.BaseService;
import org.apaas.job.entity.JobEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobService extends BaseService<JobEntity, Long> {
    Flux<JobEntity> findAll();
    Mono<JobEntity> findById(Long id);
    Mono<JobEntity> save(JobEntity entity);
    Mono<Void> deleteById(Long id);
}