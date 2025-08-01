package org.apaas.design.platform.service.impl;

import org.apaas.design.platform.entity.APIEntity;
import org.apaas.design.platform.repository.APIEntityRepository;
import org.apaas.design.platform.service.APIEntityService;
import org.apaas.design.platform.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class APIEntityServiceImpl extends BaseServiceImpl<APIEntity, Long, APIEntityRepository> implements APIEntityService {

    @Override
    public Mono<APIEntity> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }

    @Override
    public Flux<APIEntity> findByPath(String path) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByPathAndTenantId(path, tenantId));
    }
}