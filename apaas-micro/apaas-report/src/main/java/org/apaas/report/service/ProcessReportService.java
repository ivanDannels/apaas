package org.apaas.report.service;

import reactor.core.publisher.Mono;

public interface ProcessReportService {
    Mono<String> getProcessInfo(Long processId);
}