package org.apaas.report.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.report.entity.TaskAnalysis;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TaskAnalysisRepository extends ReactiveBaseRepository<TaskAnalysis, Long> {
    Mono<TaskAnalysis> findByTaskName(String taskName);

    Flux<TaskAnalysis> findByTaskId(Long taskId);
}