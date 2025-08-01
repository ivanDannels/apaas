package org.apaas.monitor.service.impl;

import org.apaas.monitor.entity.MonitorEntity;
import org.apaas.monitor.repository.MonitorRepository;
import org.apaas.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Override
    public Flux<MonitorEntity> findAll() {
        return monitorRepository.findAll();
    }

    @Override
    public Mono<MonitorEntity> findById(Long id) {
        return monitorRepository.findById(id);
    }

    @Override
    public Mono<MonitorEntity> save(MonitorEntity entity) {
        return monitorRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return monitorRepository.deleteById(id);
    }
}