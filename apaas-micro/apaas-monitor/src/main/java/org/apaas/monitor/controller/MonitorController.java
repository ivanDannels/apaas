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