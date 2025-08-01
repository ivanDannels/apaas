package org.apaas.design.platform.service.impl;

import org.apaas.design.platform.entity.DataSource;
import org.apaas.design.platform.repository.DataSourceRepository;
import org.apaas.design.platform.service.BaseServiceImpl;
import org.apaas.design.platform.service.DataSourceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSourceServiceImpl extends BaseServiceImpl<DataSource, Long, DataSourceRepository> implements DataSourceService {

    @Override
    public Mono<DataSource> findByName(String name) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMap(tenantId -> repository.findByNameAndTenantId(name, tenantId));
    }

    @Override
    public Flux<DataSource> findByType(String type) {
        return TenantContext.getTenantIdAsync()
                .defaultIfEmpty(0L)
                .flatMapMany(tenantId -> repository.findByTypeAndTenantId(type, tenantId));
    }
}