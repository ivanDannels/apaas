package org.apaas.report.service.impl;

import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.report.entity.UserAnalysis;
import org.apaas.report.repository.UserAnalysisRepository;
import org.apaas.report.service.UserAnalysisService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserAnalysisServiceImpl extends BaseServiceImpl<UserAnalysis, Long, UserAnalysisRepository> implements UserAnalysisService {

    public UserAnalysisServiceImpl(UserAnalysisRepository repository) {
        super(repository);
    }

    @Override
    public Mono<UserAnalysis> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Flux<UserAnalysis> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}