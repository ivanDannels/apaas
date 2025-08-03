package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.ProcessAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessAnalysisRepository extends ReactiveBaseRepository<ProcessAnalysis, Long> {
}