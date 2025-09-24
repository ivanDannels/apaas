package org.apaas.design.platform.service.reactive.impl;

import org.apaas.design.platform.entity.Metadata;
import org.apaas.design.platform.repository.MetadataRepository;
import org.apaas.design.platform.service.reactive.ReactiveMetadataService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式元数据服务实现类
 */
@Service
public class ReactiveMetadataServiceImpl extends BaseServiceImpl<Metadata, Long, MetadataRepository> implements ReactiveMetadataService {

    public ReactiveMetadataServiceImpl(MetadataRepository repository) {
        super(repository);
    }
}