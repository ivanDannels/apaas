package org.apaas.design.platform.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.APIEntity;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式API实体服务接口
 */
public interface ReactiveAPIEntityService extends BaseService<APIEntity, Long> {

}