package org.apaas.report.repository;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.report.entity.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends BaseEntityRepository<Report, Long> {
}