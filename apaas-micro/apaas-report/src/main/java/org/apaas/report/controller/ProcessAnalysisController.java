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
package org.apaas.report.controller;

import org.apaas.report.entity.ProcessAnalysis;
import org.apaas.report.service.ProcessAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/process-analyses")
public class ProcessAnalysisController {
    
    private final ProcessAnalysisService processAnalysisService;
    
    @Autowired
    public ProcessAnalysisController(ProcessAnalysisService processAnalysisService) {
        this.processAnalysisService = processAnalysisService;
    }
    
    @PostMapping
    public Mono<ProcessAnalysis> create(@RequestBody ProcessAnalysis processAnalysis) {
        return processAnalysisService.save(processAnalysis);
    }
    
    @GetMapping("/{id}")
    public Mono<ProcessAnalysis> findById(@PathVariable Long id) {
        return processAnalysisService.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<ProcessAnalysis> update(@PathVariable Long id, @RequestBody ProcessAnalysis processAnalysis) {
        processAnalysis.setId(id);
        return processAnalysisService.save(processAnalysis);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return processAnalysisService.deleteById(id);
    }
    
    @GetMapping
    public Flux<ProcessAnalysis> findAll() {
        return processAnalysisService.findAll();
    }
    
    @GetMapping("/process-name/{processName}")
    public Mono<ProcessAnalysis> findByProcessName(@PathVariable String processName) {
        return processAnalysisService.findByProcessName(processName);
    }
    
    @GetMapping("/process-id/{processId}")
    public Flux<ProcessAnalysis> findByProcessId(@PathVariable Long processId) {
        return processAnalysisService.findByProcessId(processId);
    }
}