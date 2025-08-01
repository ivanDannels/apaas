package org.apaas.integration.controller;

import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.service.IntegrationService;
import org.apaas.integration.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/integrations")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @GetMapping
    public Mono<AjaxResult<Flux<IntegrationEntity>>> getAllIntegrations() {
        return Mono.just(AjaxResult.success(integrationService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<AjaxResult<Mono<IntegrationEntity>>> getIntegrationById(@PathVariable Long id) {
        return Mono.just(AjaxResult.success(integrationService.findById(id)));
    }

    @PostMapping
    public Mono<AjaxResult<Mono<IntegrationEntity>>> createIntegration(@RequestBody IntegrationEntity integration) {
        return Mono.just(AjaxResult.success(integrationService.save(integration)));
    }

    @DeleteMapping("/{id}")
    public Mono<AjaxResult<Void>> deleteIntegration(@PathVariable Long id) {
        return integrationService.deleteById(id)
                .then(Mono.just(AjaxResult.success()));
    }
}