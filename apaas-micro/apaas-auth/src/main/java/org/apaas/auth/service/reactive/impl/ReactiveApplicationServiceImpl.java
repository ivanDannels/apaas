package org.apaas.auth.service.reactive.impl;

import org.apaas.auth.entity.Application;
import org.apaas.auth.repository.reactive.ReactiveApplicationRepository;
import org.apaas.auth.service.reactive.ReactiveApplicationService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveApplicationServiceImpl extends BaseServiceImpl<Application, Long, ReactiveApplicationRepository> implements ReactiveApplicationService {

    public ReactiveApplicationServiceImpl(ReactiveApplicationRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Application> getApplicationByName(String name) {
        // 实现根据应用名称获取应用的逻辑
        return repository.findByName(name);
    }

    @Override
    public Mono<Application> getApplicationByCode(String code) {
        // 实现根据应用编码获取应用的逻辑
        return repository.findByCode(code);
    }

    @Override
    public Flux<Application> getAllApplications() {
        // 实现获取所有应用列表的逻辑
        return repository.findAll();
    }

    @Override
    public Mono<Boolean> updateApplicationStatus(Long id, Integer status) {
        // 实现更新应用状态的逻辑
        return repository.findById(id)
                .flatMap(application -> {
                    application.setStatus(status);
                    return repository.save(application);
                })
                .thenReturn(true)
                .onErrorReturn(false);
    }
}