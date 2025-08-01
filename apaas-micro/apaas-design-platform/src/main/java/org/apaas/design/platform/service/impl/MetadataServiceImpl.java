package org.apaas.design.platform.service.impl;

import org.apaas.core.context.TenantContext;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.design.platform.entity.Metadata;
import org.apaas.design.platform.repository.MetadataRepository;
import org.apaas.design.platform.service.MetadataService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MetadataServiceImpl extends BaseServiceImpl<Metadata, Long, MetadataRepository> implements MetadataService {
    
    @Override
    public Mono<Metadata> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }
    
    @Override
    public Flux<Metadata> findByType(String type) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByTypeAndTenantId(type, tenantId));
    }
}