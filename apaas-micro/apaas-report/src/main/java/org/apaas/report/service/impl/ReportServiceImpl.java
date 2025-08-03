package org.apaas.report.service.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.Report;
import org.apaas.report.repository.ReportRepository;
import org.apaas.report.service.ReportService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl extends BaseServiceImpl<Report, Long, ReportRepository> implements ReportService {

    public ReportServiceImpl(ReportRepository repository, RedisDomainEventPublisher<EntityChangedEvent<Report>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<Report> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Flux<Report> findByType(String type) {
        return repository.findByType(type);
    }
}