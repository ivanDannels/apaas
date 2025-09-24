package org.apaas.report.service;

import org.apaas.domain.service.BaseService;
import org.apaas.report.entity.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportService extends BaseService<Report, Long> {
    Mono<Report> findByName(String name);
    Flux<Report> findByType(String type);
}