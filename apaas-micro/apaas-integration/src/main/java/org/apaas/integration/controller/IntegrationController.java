package org.apaas.integration.controller;

import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.service.IntegrationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/integrations")
public class IntegrationController extends ReactiveBaseController<IntegrationEntity, Long, IntegrationService> {

    public IntegrationController(IntegrationService service) {
        super(service);
    }

    @GetMapping
    public Flux<IntegrationEntity> getAllIntegrations() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<IntegrationEntity> getIntegrationById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<IntegrationEntity> createIntegration(@RequestBody IntegrationEntity integration) {
        return service.save(integration);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteIntegration(@PathVariable Long id) {
        return service.deleteById(id);
    }
}