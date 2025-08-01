package org.apaas.integration.service.impl;

import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.repository.IntegrationRepository;
import org.apaas.integration.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    @Autowired
    private IntegrationRepository integrationRepository;

    @Override
    public Flux<IntegrationEntity> findAll() {
        return integrationRepository.findAll();
    }

    @Override
    public Mono<IntegrationEntity> findById(Long id) {
        return integrationRepository.findById(id);
    }

    @Override
    public Mono<IntegrationEntity> save(IntegrationEntity entity) {
        return integrationRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return integrationRepository.deleteById(id);
    }
}