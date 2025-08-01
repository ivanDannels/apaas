package org.apaas.report.service.impl;

import org.apaas.report.feign.ProcessServiceClient;
import org.apaas.report.service.ProcessReportService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProcessReportServiceImpl implements ProcessReportService {
    
    private final ProcessServiceClient processServiceClient;
    
    public ProcessReportServiceImpl(ProcessServiceClient processServiceClient) {
        this.processServiceClient = processServiceClient;
    }
    
    @Override
    public Mono<String> getProcessInfo(Long processId) {
        return processServiceClient.getProcessById(processId);
    }
}