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