/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.domain.rest;

import lombok.RequiredArgsConstructor;
import org.apaas.domain.entity.TestEntity;
import org.apaas.domain.service.TestEntityService;
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