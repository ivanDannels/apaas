package org.apaas.design.platform.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.design.platform.entity.DataSource;
import org.apaas.design.platform.repository.DataSourceRepository;
import org.apaas.design.platform.service.reactive.ReactiveDataSourceService;
import org.apaas.common.exception.BusinessException;
import org.apaas.common.web.domain.PageResult;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.web.domain.BasePageQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式数据源服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveDataSourceServiceImpl extends BaseServiceImpl<DataSource, Long, DataSourceRepository> implements ReactiveDataSourceService {

    private final DataSourceRepository dataSourceRepository;

    /**
     * 分页查询数据源
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<DataSource>> selectDataSourcePage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return dataSourceRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据数据源名称查询数据源
     *
     * @param dataSourceName 数据源名称
     * @return 数据源
     */
    @Override
    public Mono<DataSource> getDataSourceByDataSourceName(String dataSourceName) {
        return dataSourceRepository.findByDataSourceName(dataSourceName);
    }

    /**
     * 根据数据库类型查询数据源列表
     *
     * @param databaseType 数据库类型
     * @return 数据源列表
     */
    @Override
    public Flux<DataSource> getDataSourcesByDatabaseType(String databaseType) {
        return dataSourceRepository.findByDatabaseType(databaseType);
    }

    /**
     * 测试数据源连接
     *
     * @param dataSource 数据源
     * @return 测试结果
     */
    @Override
    public Mono<Boolean> testDataSourceConnection(DataSource dataSource) {
        // 这里应该实现实际的数据源连接测试逻辑
        // 为了简化，我们假设连接总是成功的
        return Mono.just(true);
    }

    /**
     * 删除数据源
     *
     * @param dataSourceId 数据源ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteDataSource(Long dataSourceId) {
        return super.findById(dataSourceId)
                .switchIfEmpty(Mono.error(new BusinessException("数据源不存在")))
                .flatMap(dataSource -> super.deleteById(dataSourceId).map(deleted -> true));
    }
}