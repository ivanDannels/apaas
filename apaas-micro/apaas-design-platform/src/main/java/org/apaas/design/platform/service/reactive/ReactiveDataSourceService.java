package org.apaas.design.platform.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.DataSource;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式数据源服务接口
 */
public interface ReactiveDataSourceService extends BaseService<DataSource, Long> {

}