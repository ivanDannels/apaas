package org.apaas.report.service.impl;

import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.ProcessAnalysis;
import org.apaas.report.repository.ProcessAnalysisRepository;
import org.apaas.report.service.ProcessAnalysisService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProcessAnalysisServiceImpl extends BaseServiceImpl<ProcessAnalysis, Long, ProcessAnalysisRepository> implements ProcessAnalysisService {
    private final ProcessAnalysisRepository processAnalysisRepository;

    public ProcessAnalysisServiceImpl(ProcessAnalysisRepository processAnalysisRepository) {
        super(processAnalysisRepository);
        this.processAnalysisRepository = processAnalysisRepository;
    }

    @Override
    public Mono<ProcessAnalysis> findByProcessName(String processName) {
        return processAnalysisRepository.findByProcessName(processName);
    }

    @Override
    public Flux<ProcessAnalysis> findByProcessId(Long processId) {
        return processAnalysisRepository.findByProcessId(processId);
    }
}