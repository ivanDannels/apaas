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

import org.apaas.report.entity.UserAnalysis;
import org.apaas.report.service.UserAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user-analyses")
public class UserAnalysisController {
    
    private final UserAnalysisService userAnalysisService;
    
    @Autowired
    public UserAnalysisController(UserAnalysisService userAnalysisService) {
        this.userAnalysisService = userAnalysisService;
    }
    
    @PostMapping
    public Mono<UserAnalysis> create(@RequestBody UserAnalysis userAnalysis) {
        return userAnalysisService.save(userAnalysis);
    }
    
    @GetMapping("/{id}")
    public Mono<UserAnalysis> findById(@PathVariable Long id) {
        return userAnalysisService.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<UserAnalysis> update(@PathVariable Long id, @RequestBody UserAnalysis userAnalysis) {
        userAnalysis.setId(id);
        return userAnalysisService.save(userAnalysis);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return userAnalysisService.deleteById(id);
    }
    
    @GetMapping
    public Flux<UserAnalysis> findAll() {
        return userAnalysisService.findAll();
    }
    
    @GetMapping("/user-id/{userId}")
    public Mono<UserAnalysis> findByUserId(@PathVariable Long userId) {
        return userAnalysisService.findByUserId(userId);
    }
    
    @GetMapping("/user-name/{userName}")
    public Flux<UserAnalysis> findByUserName(@PathVariable String userName) {
        return userAnalysisService.findByUserName(userName);
    }
}