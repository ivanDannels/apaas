package org.apaas.report.controller;

import org.apaas.report.entity.TaskAnalysis;
import org.apaas.report.service.TaskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task-analyses")
public class TaskAnalysisController {
    private final TaskAnalysisService taskAnalysisService;

    @Autowired
    public TaskAnalysisController(TaskAnalysisService taskAnalysisService) {
        this.taskAnalysisService = taskAnalysisService;
    }

    @PostMapping
    public Mono<TaskAnalysis> create(@RequestBody TaskAnalysis taskAnalysis) {
        return taskAnalysisService.save(taskAnalysis);
    }

    @GetMapping("/{id}")
    public Mono<TaskAnalysis> findById(@PathVariable Long id) {
        return taskAnalysisService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<TaskAnalysis> update(@PathVariable Long id, @RequestBody TaskAnalysis taskAnalysis) {
        taskAnalysis.setId(id);
        return taskAnalysisService.save(taskAnalysis);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return taskAnalysisService.deleteById(id);
    }

    @GetMapping
    public Flux<TaskAnalysis> findAll() {
        return taskAnalysisService.findAll();
    }

    @GetMapping("/task-name/{taskName}")
    public Mono<TaskAnalysis> findByTaskName(@PathVariable String taskName) {
        return taskAnalysisService.findByTaskName(taskName);
    }

    @GetMapping("/task-id/{taskId}")
    public Flux<TaskAnalysis> findByTaskId(@PathVariable Long taskId) {
        return taskAnalysisService.findByTaskId(taskId);
    }
}