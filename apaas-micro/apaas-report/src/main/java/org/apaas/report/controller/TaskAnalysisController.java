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

import org.apaas.report.entity.TaskAnalysis;
import org.apaas.report.service.TaskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task-analyses")
public class TaskAnalysisController {
    
    private final TaskAnalysisService taskAnalysisService;
    
    @Autowired
    public TaskAnalysisController(TaskAnalysisService taskAnalysisService) {
        this.taskAnalysisService = taskAnalysisService;
    }
    
    @PostMapping
    public Mono<TaskAnalysis> create(@RequestBody TaskAnalysis taskAnalysis) {
        return taskAnalysisService.save(taskAnalysis);
    }
    
    @GetMapping("/{id}")
    public Mono<TaskAnalysis> findById(@PathVariable Long id) {
        return taskAnalysisService.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<TaskAnalysis> update(@PathVariable Long id, @RequestBody TaskAnalysis taskAnalysis) {
        taskAnalysis.setId(id);
        return taskAnalysisService.save(taskAnalysis);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return taskAnalysisService.deleteById(id);
    }
    
    @GetMapping
    public Flux<TaskAnalysis> findAll() {
        return taskAnalysisService.findAll();
    }
    
    @GetMapping("/task-name/{taskName}")
    public Mono<TaskAnalysis> findByTaskName(@PathVariable String taskName) {
        return taskAnalysisService.findByTaskName(taskName);
    }
    
    @GetMapping("/task-id/{taskId}")
    public Flux<TaskAnalysis> findByTaskId(@PathVariable Long taskId) {
        return taskAnalysisService.findByTaskId(taskId);
    }
}