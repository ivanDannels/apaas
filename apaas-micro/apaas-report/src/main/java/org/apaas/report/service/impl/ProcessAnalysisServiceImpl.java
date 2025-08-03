package org.apaas.report.service.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.ProcessAnalysis;
import org.apaas.report.repository.ProcessAnalysisRepository;
import org.apaas.report.service.ProcessAnalysisService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProcessAnalysisServiceImpl extends BaseServiceImpl<ProcessAnalysis, Long, ProcessAnalysisRepository> implements ProcessAnalysisService {

    public ProcessAnalysisServiceImpl(ProcessAnalysisRepository repository, RedisDomainEventPublisher<EntityChangedEvent<ProcessAnalysis>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<ProcessAnalysis> findByProcessName(String processName) {
        return repository.findByProcessName(processName);
    }

    @Override
    public Flux<ProcessAnalysis> findByProcessId(Long processId) {
        return repository.findByProcessId(processId);
    }
}