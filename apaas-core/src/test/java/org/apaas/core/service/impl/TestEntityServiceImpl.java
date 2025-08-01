package org.apaas.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.core.domain.TestEntity;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.repository.TestEntityRepository;
import org.apaas.core.service.TestEntityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TestEntityServiceImpl extends BaseServiceImpl<TestEntity, Long, TestEntityRepository> implements TestEntityService {
    
    private final TestEntityRepository testEntityRepository;
    private final RedisDomainEventPublisher redisDomainEventPublisher;
    
    public TestEntityServiceImpl(TestEntityRepository testEntityRepository, 
                                 RedisDomainEventPublisher redisDomainEventPublisher) {
        super(testEntityRepository, redisDomainEventPublisher);
        this.testEntityRepository = testEntityRepository;
        this.redisDomainEventPublisher = redisDomainEventPublisher;
    }
    
    @Override
    public Mono<TestEntity> findByName(String name) {
        return testEntityRepository.findByName(name);
    }
}