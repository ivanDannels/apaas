package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.UserAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnalysisRepository extends ReactiveBaseRepository<UserAnalysis, Long> {
}