package org.apaas.monitor.controller;

import org.apaas.monitor.entity.MonitorEntity;
import org.apaas.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive/monitors")
public class MonitorController {

    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MonitorEntity> getAllMonitors() {
        return monitorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MonitorEntity> getMonitorById(@PathVariable Long id) {
        return monitorService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MonitorEntity> createMonitor(@RequestBody MonitorEntity monitor) {
        return monitorService.save(monitor);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMonitor(@PathVariable Long id) {
        return monitorService.deleteById(id);
    }
}