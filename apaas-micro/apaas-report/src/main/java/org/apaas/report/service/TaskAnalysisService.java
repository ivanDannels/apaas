package org.apaas.report.service;

import org.apaas.domain.service.BaseService;
import org.apaas.report.entity.TaskAnalysis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskAnalysisService extends BaseService<TaskAnalysis, Long> {
    Mono<TaskAnalysis> findByTaskName(String taskName);
    Flux<TaskAnalysis> findByTaskId(Long taskId);
}