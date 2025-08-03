package org.apaas.core.service.impl;

import org.apaas.core.domain.TestEntity;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.repository.TestEntityRepository;
import org.apaas.core.service.TestEntityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestEntityServiceImpl extends BaseServiceImpl<TestEntity, Long, TestEntityRepository> implements TestEntityService {
    
    public TestEntityServiceImpl(TestEntityRepository repository, RedisDomainEventPublisher<EntityChangedEvent<TestEntity>> eventPublisher) {
        super(repository, eventPublisher);
    }
    
    @Override
    public Mono<TestEntity> findByName(String name) {
        return repository.findByName(name);
    }
}