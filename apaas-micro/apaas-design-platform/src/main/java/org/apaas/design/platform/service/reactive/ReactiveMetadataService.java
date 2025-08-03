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

    /**
     * 分页查询元数据
     *
     * @param query 查询参数
     * @return 分页结果
     */
    Mono<PageResult<Metadata>> selectMetadataPage(BasePageQuery query);

    /**
     * 根据元数据名称查询元数据
     *
     * @param metadataName 元数据名称
     * @return 元数据
     */
    Mono<Metadata> getMetadataByMetadataName(String metadataName);

    /**
     * 根据模块名称查询元数据列表
     *
     * @param moduleName 模块名称
     * @return 元数据列表
     */
    Flux<Metadata> getMetadatasByModuleName(String moduleName);

    /**
     * 部署元数据
     *
     * @param metadata 元数据
     * @return 部署结果
     */
    Mono<Boolean> deployMetadata(Metadata metadata);

    /**
     * 删除元数据
     *
     * @param metadataId 元数据ID
     * @return 删除结果
     */
    Mono<Boolean> deleteMetadata(Long metadataId);
}