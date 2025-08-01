package org.apaas.job.service.impl;

import org.apaas.job.entity.JobEntity;
import org.apaas.job.repository.JobRepository;
import org.apaas.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Flux<JobEntity> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Mono<JobEntity> findById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public Mono<JobEntity> save(JobEntity entity) {
        return jobRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return jobRepository.deleteById(id);
    }
}