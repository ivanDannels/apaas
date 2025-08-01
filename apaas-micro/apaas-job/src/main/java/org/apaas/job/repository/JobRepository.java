package org.apaas.job.repository;

import org.apaas.job.entity.JobEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends R2dbcRepository<JobEntity, Long> {
}