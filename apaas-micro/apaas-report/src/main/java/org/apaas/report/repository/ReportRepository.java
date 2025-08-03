package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.Report;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReportRepository extends ReactiveBaseRepository<Report, Long> {
    Mono<Report> findByName(String name);

    Flux<Report> findByType(String type);
}