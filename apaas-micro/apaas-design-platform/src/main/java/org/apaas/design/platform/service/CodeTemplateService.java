package org.apaas.design.platform.service;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.CodeTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CodeTemplateService extends BaseService<CodeTemplate, Long> {
    Mono<CodeTemplate> findByName(String name);
    Flux<CodeTemplate> findByLanguage(String language);
}