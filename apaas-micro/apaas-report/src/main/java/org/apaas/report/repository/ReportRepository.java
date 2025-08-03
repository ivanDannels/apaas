package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends ReactiveBaseRepository<Report, Long> {
}