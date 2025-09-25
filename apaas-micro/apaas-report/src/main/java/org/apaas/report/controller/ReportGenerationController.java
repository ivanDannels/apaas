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
import org.apaas.report.service.ReportGenerationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/report-generation")
public class ReportGenerationController {
    
    private final ReportGenerationService reportGenerationService;
    
    public ReportGenerationController(ReportGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }
    
    @PostMapping("/pdf")
    public Mono<ResponseEntity<Resource>> generatePdfReport(@RequestParam String template, @RequestBody List<?> data) {
        
        return Mono.fromCallable(() -> {
            byte[] reportBytes = reportGenerationService.generatePdfReport(template, data);
            ByteArrayResource resource = new ByteArrayResource(reportBytes);
            
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf").contentType(MediaType.APPLICATION_PDF).body(resource);
        });
    }
    
    @PostMapping("/excel")
    public Mono<ResponseEntity<Resource>> generateExcelReport(@RequestBody List<Report> reports) {
        
        return Mono.fromCallable(() -> {
            byte[] reportBytes = reportGenerationService.generateExcelReport(reports);
            ByteArrayResource resource = new ByteArrayResource(reportBytes);
            
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx").contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        });
    }
}