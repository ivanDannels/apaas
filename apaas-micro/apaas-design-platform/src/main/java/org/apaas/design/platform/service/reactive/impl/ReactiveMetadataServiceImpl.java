package org.apaas.design.platform.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.design.platform.entity.Metadata;
import org.apaas.design.platform.repository.MetadataRepository;
import org.apaas.design.platform.service.reactive.ReactiveMetadataService;
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
 * 响应式元数据服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveMetadataServiceImpl extends BaseServiceImpl<Metadata, Long, MetadataRepository> implements ReactiveMetadataService {

    private final MetadataRepository metadataRepository;

    /**
     * 分页查询元数据
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<Metadata>> selectMetadataPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return metadataRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据元数据名称查询元数据
     *
     * @param metadataName 元数据名称
     * @return 元数据
     */
    @Override
    public Mono<Metadata> getMetadataByMetadataName(String metadataName) {
        return metadataRepository.findByMetadataName(metadataName);
    }

    /**
     * 根据模块名称查询元数据列表
     *
     * @param moduleName 模块名称
     * @return 元数据列表
     */
    @Override
    public Flux<Metadata> getMetadatasByModuleName(String moduleName) {
        return metadataRepository.findByModuleName(moduleName);
    }

    /**
     * 部署元数据
     *
     * @param metadata 元数据
     * @return 部署结果
     */
    @Override
    public Mono<Boolean> deployMetadata(Metadata metadata) {
        metadata.setDeployed(true);
        metadata.setDeployTime(new java.util.Date());
        return super.save(metadata).map(saved -> true);
    }

    /**
     * 删除元数据
     *
     * @param metadataId 元数据ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteMetadata(Long metadataId) {
        return super.findById(metadataId)
                .switchIfEmpty(Mono.error(new BusinessException("元数据不存在")))
                .flatMap(metadata -> {
                    // 检查元数据是否已部署
                    if (metadata.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的元数据不能删除"));
                    }
                    return super.deleteById(metadataId).map(deleted -> true);
                });
    }
}