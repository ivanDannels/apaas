package org.apaas.design.platform.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.design.platform.entity.CodeTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CodeTemplateRepository extends ReactiveBaseRepository<CodeTemplate, Long> {
    Mono<CodeTemplate> findByNameAndTenantId(String name, Long tenantId);
    Flux<CodeTemplate> findByLanguageAndTenantId(String language, Long tenantId);
}