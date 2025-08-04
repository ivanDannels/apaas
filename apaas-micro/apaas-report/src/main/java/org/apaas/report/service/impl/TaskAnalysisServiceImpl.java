package org.apaas.report.service.impl;

import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.TaskAnalysis;
import org.apaas.report.repository.TaskAnalysisRepository;
import org.apaas.report.service.TaskAnalysisService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskAnalysisServiceImpl extends BaseServiceImpl<TaskAnalysis, Long, TaskAnalysisRepository> implements TaskAnalysisService {

    public TaskAnalysisServiceImpl(TaskAnalysisRepository repository) {
        super(repository);
    }

    @Override
    public Mono<TaskAnalysis> findByTaskName(String taskName) {
        return repository.findByTaskName(taskName);
    }

    @Override
    public Flux<TaskAnalysis> findByTaskId(Long taskId) {
        return repository.findByTaskId(taskId);
    }
}