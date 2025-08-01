package org.apaas.report.controller;

import org.apaas.report.entity.Report;
import org.apaas.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Mono<Report> create(@RequestBody Report report) {
        return reportService.save(report);
    }

    @GetMapping("/{id}")
    public Mono<Report> findById(@PathVariable Long id) {
        return reportService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Report> update(@PathVariable Long id, @RequestBody Report report) {
        report.setId(id);
        return reportService.save(report);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return reportService.deleteById(id);
    }

    @GetMapping
    public Flux<Report> findAll() {
        return reportService.findAll();
    }

    @GetMapping("/name/{name}")
    public Mono<Report> findByName(@PathVariable String name) {
        return reportService.findByName(name);
    }

    @GetMapping("/type/{type}")
    public Flux<Report> findByType(@PathVariable String type) {
        return reportService.findByType(type);
    }
}