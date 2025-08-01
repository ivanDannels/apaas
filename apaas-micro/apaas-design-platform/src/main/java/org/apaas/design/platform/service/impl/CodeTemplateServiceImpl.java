package org.apaas.design.platform.service.impl;

import org.apaas.design.platform.entity.CodeTemplate;
import org.apaas.design.platform.repository.CodeTemplateRepository;
import org.apaas.design.platform.service.CodeTemplateService;
import org.apaas.design.platform.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CodeTemplateServiceImpl extends BaseServiceImpl<CodeTemplate, Long, CodeTemplateRepository> implements CodeTemplateService {

    @Override
    public Mono<CodeTemplate> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }

    @Override
    public Flux<CodeTemplate> findByLanguage(String language) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByLanguageAndTenantId(language, tenantId));
    }
}