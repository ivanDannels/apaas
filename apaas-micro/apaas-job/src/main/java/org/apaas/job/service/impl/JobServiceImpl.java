package org.apaas.job.service.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.job.entity.JobEntity;
import org.apaas.job.repository.JobRepository;
import org.apaas.job.service.JobService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JobServiceImpl extends BaseServiceImpl<JobEntity, Long, JobRepository> implements JobService {

    public JobServiceImpl(JobRepository repository, RedisDomainEventPublisher<EntityChangedEvent<JobEntity>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Flux<JobEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<JobEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mono<JobEntity> save(JobEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}