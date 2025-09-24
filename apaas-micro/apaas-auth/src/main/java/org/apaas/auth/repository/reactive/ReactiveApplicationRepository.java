package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.Application;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveApplicationRepository extends ReactiveBaseRepository<Application, Long> {
    Mono<Application> findByName(String name);

    Mono<Application> findByCode(String code);
}
