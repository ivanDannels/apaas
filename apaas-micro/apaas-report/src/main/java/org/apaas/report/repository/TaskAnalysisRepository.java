package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.TaskAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAnalysisRepository extends ReactiveBaseRepository<TaskAnalysis, Long> {
}