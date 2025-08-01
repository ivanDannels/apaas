package org.apaas.report.service;

import org.apaas.core.service.BaseService;
import org.apaas.report.entity.ProcessAnalysis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProcessAnalysisService extends BaseService<ProcessAnalysis, Long> {
    Mono<ProcessAnalysis> findByProcessName(String processName);
    Flux<ProcessAnalysis> findByProcessId(Long processId);
}