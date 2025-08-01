package org.apaas.report.service;

import org.apaas.report.entity.Report;

import java.util.List;

public interface ReportGenerationService {
    byte[] generatePdfReport(String reportTemplate, List<?> data);
    byte[] generateExcelReport(List<Report> reports);
}