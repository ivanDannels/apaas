package org.apaas.report.service;

import org.apaas.domain.service.BaseService;
import org.apaas.report.entity.UserAnalysis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAnalysisService extends BaseService<UserAnalysis, Long> {
    Mono<UserAnalysis> findByUserId(Long userId);
    Flux<UserAnalysis> findByUserName(String userName);
}