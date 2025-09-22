package org.apaas.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.apaas.core.domain.TestEntity;
import org.apaas.core.service.TestEntityService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test-entities")
@RequiredArgsConstructor
public class TestEntityController {
    
    private final TestEntityService testEntityService;
    
    @PostMapping
    public Mono<TestEntity> create(@RequestBody TestEntity entity) {
        return testEntityService.save(entity);
    }
    
    @GetMapping("/{id}")
    public Mono<TestEntity> findById(@PathVariable Long id) {
        return testEntityService.findById(id);
    }
    
    @GetMapping
    public Flux<TestEntity> findAll() {
        return testEntityService.findAll();
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return testEntityService.deleteById(id);
    }
}