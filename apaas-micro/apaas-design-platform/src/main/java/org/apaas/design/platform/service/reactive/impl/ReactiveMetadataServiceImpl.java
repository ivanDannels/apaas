package org.apaas.design.platform.service.reactive.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.design.platform.entity.Metadata;
import org.apaas.design.platform.repository.MetadataRepository;
import org.apaas.design.platform.service.reactive.ReactiveMetadataService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式元数据服务实现类
 */
@Service
public class ReactiveMetadataServiceImpl extends BaseServiceImpl<Metadata, Long, MetadataRepository> implements ReactiveMetadataService {

    public ReactiveMetadataServiceImpl(MetadataRepository repository, RedisDomainEventPublisher<EntityChangedEvent<Metadata>> eventPublisher) {
        super(repository, eventPublisher);
    }
}