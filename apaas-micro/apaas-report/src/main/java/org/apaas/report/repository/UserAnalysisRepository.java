package org.apaas.report.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.report.entity.UserAnalysis;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserAnalysisRepository extends ReactiveBaseRepository<UserAnalysis, Long> {
    Mono<UserAnalysis> findByUserId(Long userId);

    Flux<UserAnalysis> findByUserName(String userName);
}