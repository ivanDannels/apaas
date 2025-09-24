package org.apaas.integration.service.impl;

import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.repository.IntegrationRepository;
import org.apaas.integration.service.IntegrationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IntegrationServiceImpl extends BaseServiceImpl<IntegrationEntity, Long, IntegrationRepository> implements IntegrationService {

    public IntegrationServiceImpl(IntegrationRepository repository) {
        super(repository);
    }

    @Override
    public Flux<IntegrationEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<IntegrationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mono<IntegrationEntity> save(IntegrationEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}