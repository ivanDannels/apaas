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

import org.apaas.report.entity.Report;
import org.apaas.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportController {
    
    private final ReportService reportService;
    
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    @PostMapping
    public Mono<Report> create(@RequestBody Report report) {
        return reportService.save(report);
    }
    
    @GetMapping("/{id}")
    public Mono<Report> findById(@PathVariable Long id) {
        return reportService.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<Report> update(@PathVariable Long id, @RequestBody Report report) {
        report.setId(id);
        return reportService.save(report);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return reportService.deleteById(id);
    }
    
    @GetMapping
    public Flux<Report> findAll() {
        return reportService.findAll();
    }
    
    @GetMapping("/name/{name}")
    public Mono<Report> findByName(@PathVariable String name) {
        return reportService.findByName(name);
    }
    
    @GetMapping("/type/{type}")
    public Flux<Report> findByType(@PathVariable String type) {
        return reportService.findByType(type);
    }
}