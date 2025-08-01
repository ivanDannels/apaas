package org.apaas.report.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.report.entity.TaskAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAnalysisRepository extends BaseEntityRepository<TaskAnalysis, Long> {
}