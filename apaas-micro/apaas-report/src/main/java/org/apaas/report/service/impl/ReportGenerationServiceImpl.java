package org.apaas.report.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apaas.report.entity.Report;
import org.apaas.report.exception.ReportException;
import org.apaas.report.service.ReportGenerationService;
import org.apaas.report.util.ReportUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerationServiceImpl implements ReportGenerationService {
    
    @Override
    public byte[] generatePdfReport(String reportTemplate, List<?> data) {
        try {
            // Load the report template
            InputStream reportStream = getClass().getResourceAsStream(reportTemplate);
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // Create data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
            
            // Set parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("generatedAt", ReportUtil.formatDate(java.time.LocalDateTime.now()));
            
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Error generating PDF report", e);
        }
    }
    
    @Override
    public byte[] generateExcelReport(List<Report> reports) {
        // Implementation for Excel report generation
        // This would use Apache POI to create an Excel file
        return new byte[0];
    }
}