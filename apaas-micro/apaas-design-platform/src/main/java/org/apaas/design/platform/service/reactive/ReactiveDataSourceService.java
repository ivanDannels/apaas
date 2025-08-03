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

    /**
     * 分页查询数据源
     *
     * @param query 查询参数
     * @return 分页结果
     */
    Mono<PageResult<DataSource>> selectDataSourcePage(BasePageQuery query);

    /**
     * 根据数据源名称查询数据源
     *
     * @param dataSourceName 数据源名称
     * @return 数据源
     */
    Mono<DataSource> getDataSourceByDataSourceName(String dataSourceName);

    /**
     * 根据数据库类型查询数据源列表
     *
     * @param databaseType 数据库类型
     * @return 数据源列表
     */
    Flux<DataSource> getDataSourcesByDatabaseType(String databaseType);

    /**
     * 测试数据源连接
     *
     * @param dataSource 数据源
     * @return 测试结果
     */
    Mono<Boolean> testDataSourceConnection(DataSource dataSource);

    /**
     * 删除数据源
     *
     * @param dataSourceId 数据源ID
     * @return 删除结果
     */
    Mono<Boolean> deleteDataSource(Long dataSourceId);
}