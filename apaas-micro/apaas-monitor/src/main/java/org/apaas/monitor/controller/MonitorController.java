package org.apaas.monitor.controller;

import org.apaas.monitor.entity.MonitorEntity;
import org.apaas.monitor.service.MonitorService;
import org.apaas.monitor.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping
    public Mono<AjaxResult<Flux<MonitorEntity>>> getAllMonitors() {
        return Mono.just(AjaxResult.success(monitorService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<AjaxResult<Mono<MonitorEntity>>> getMonitorById(@PathVariable Long id) {
        return Mono.just(AjaxResult.success(monitorService.findById(id)));
    }

    @PostMapping
    public Mono<AjaxResult<Mono<MonitorEntity>>> createMonitor(@RequestBody MonitorEntity monitor) {
        return Mono.just(AjaxResult.success(monitorService.save(monitor)));
    }

    @DeleteMapping("/{id}")
    public Mono<AjaxResult<Void>> deleteMonitor(@PathVariable Long id) {
        return monitorService.deleteById(id)
                .then(Mono.just(AjaxResult.success()));
    }
}