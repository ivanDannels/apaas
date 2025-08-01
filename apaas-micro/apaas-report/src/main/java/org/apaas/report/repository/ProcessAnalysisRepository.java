package org.apaas.report.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.report.entity.ProcessAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessAnalysisRepository extends BaseEntityRepository<ProcessAnalysis, Long> {
}