package org.apaas.report.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.report.entity.ProcessAnalysis;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProcessAnalysisRepository extends ReactiveBaseRepository<ProcessAnalysis, Long> {
    Mono<ProcessAnalysis> findByProcessName(String processName);

    Flux<ProcessAnalysis> findByProcessId(Long processId);
}