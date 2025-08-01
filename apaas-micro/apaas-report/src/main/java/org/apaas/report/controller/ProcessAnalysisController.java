package org.apaas.report.controller;

import org.apaas.report.entity.ProcessAnalysis;
import org.apaas.report.service.ProcessAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/process-analyses")
public class ProcessAnalysisController {
    private final ProcessAnalysisService processAnalysisService;

    @Autowired
    public ProcessAnalysisController(ProcessAnalysisService processAnalysisService) {
        this.processAnalysisService = processAnalysisService;
    }

    @PostMapping
    public Mono<ProcessAnalysis> create(@RequestBody ProcessAnalysis processAnalysis) {
        return processAnalysisService.save(processAnalysis);
    }

    @GetMapping("/{id}")
    public Mono<ProcessAnalysis> findById(@PathVariable Long id) {
        return processAnalysisService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<ProcessAnalysis> update(@PathVariable Long id, @RequestBody ProcessAnalysis processAnalysis) {
        processAnalysis.setId(id);
        return processAnalysisService.save(processAnalysis);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return processAnalysisService.deleteById(id);
    }

    @GetMapping
    public Flux<ProcessAnalysis> findAll() {
        return processAnalysisService.findAll();
    }

    @GetMapping("/process-name/{processName}")
    public Mono<ProcessAnalysis> findByProcessName(@PathVariable String processName) {
        return processAnalysisService.findByProcessName(processName);
    }

    @GetMapping("/process-id/{processId}")
    public Flux<ProcessAnalysis> findByProcessId(@PathVariable Long processId) {
        return processAnalysisService.findByProcessId(processId);
    }
}