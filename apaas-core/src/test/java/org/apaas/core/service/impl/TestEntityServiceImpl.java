package org.apaas.core.service.impl;

import org.apaas.core.domain.TestEntity;
import org.apaas.core.repository.TestEntityRepository;
import org.apaas.core.service.TestEntityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestEntityServiceImpl extends BaseServiceImpl<TestEntity, Long, TestEntityRepository> implements TestEntityService {
    
    public TestEntityServiceImpl(TestEntityRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<TestEntity> findByName(String name) {
        return repository.findByName(name);
    }
}