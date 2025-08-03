package org.apaas.job.repository;

import org.apaas.job.entity.JobEntity;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends ReactiveBaseRepository<JobEntity, Long> {
}