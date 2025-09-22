package org.apaas.design.platform.service.reactive.impl;

import org.apaas.design.platform.entity.APIEntity;
import org.apaas.design.platform.repository.APIEntityRepository;
import org.apaas.design.platform.service.reactive.ReactiveAPIEntityService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式API实体服务实现类
 */
@Service
public class ReactiveAPIEntityServiceImpl extends BaseServiceImpl<APIEntity, Long, APIEntityRepository> implements ReactiveAPIEntityService {

    public ReactiveAPIEntityServiceImpl(APIEntityRepository repository) {
        super(repository);
    }
}