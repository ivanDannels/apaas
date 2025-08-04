package org.apaas.design.platform.service.reactive.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.design.platform.entity.DataSource;
import org.apaas.design.platform.repository.DataSourceRepository;
import org.apaas.design.platform.service.reactive.ReactiveDataSourceService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式数据源服务实现类
 */
@Service
public class ReactiveDataSourceServiceImpl extends BaseServiceImpl<DataSource, Long, DataSourceRepository> implements ReactiveDataSourceService {

    public ReactiveDataSourceServiceImpl(DataSourceRepository repository, RedisDomainEventPublisher<EntityChangedEvent<DataSource>> eventPublisher) {
        super(repository, eventPublisher);
    }
}