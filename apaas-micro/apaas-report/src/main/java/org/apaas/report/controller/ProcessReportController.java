package org.apaas.report.controller;

import org.apaas.report.service.ProcessReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/process-reports")
public class ProcessReportController {
    
    private final ProcessReportService processReportService;
    
    public ProcessReportController(ProcessReportService processReportService) {
        this.processReportService = processReportService;
    }
    
    @GetMapping("/{processId}")
    public Mono<String> getProcessReport(@PathVariable Long processId) {
        return processReportService.getProcessInfo(processId);
    }
}