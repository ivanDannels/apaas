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
    public Mono<ResponseEntity<Resource>> generatePdfReport(
            @RequestParam String template,
            @RequestBody List<?> data) {
        
        return Mono.fromCallable(() -> {
            byte[] reportBytes = reportGenerationService.generatePdfReport(template, data);
            ByteArrayResource resource = new ByteArrayResource(reportBytes);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        });
    }
    
    @PostMapping("/excel")
    public Mono<ResponseEntity<Resource>> generateExcelReport(@RequestBody List<Report> reports) {
        
        return Mono.fromCallable(() -> {
            byte[] reportBytes = reportGenerationService.generateExcelReport(reports);
            ByteArrayResource resource = new ByteArrayResource(reportBytes);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        });
    }
}