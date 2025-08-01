package org.apaas.report.service.impl;

import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.Report;
import org.apaas.report.repository.ReportRepository;
import org.apaas.report.service.ReportService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl extends BaseServiceImpl<Report, Long, ReportRepository> implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    @Override
    public Mono<Report> findByName(String name) {
        return reportRepository.findByName(name);
    }

    @Override
    public Flux<Report> findByType(String type) {
        return reportRepository.findByType(type);
    }
}