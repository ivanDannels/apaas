package org.apaas.design.platform.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.Metadata;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式元数据服务接口
 */
public interface ReactiveMetadataService extends BaseService<Metadata, Long> {

}