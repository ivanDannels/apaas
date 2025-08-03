package org.apaas.design.platform.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.design.platform.entity.APIEntity;
import org.apaas.design.platform.repository.APIEntityRepository;
import org.apaas.design.platform.service.reactive.ReactiveAPIEntityService;
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
 * 响应式API实体服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveAPIEntityServiceImpl extends BaseServiceImpl<APIEntity, Long, APIEntityRepository> implements ReactiveAPIEntityService {

    private final APIEntityRepository apiEntityRepository;

    /**
     * 分页查询API实体
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<APIEntity>> selectAPIEntityPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return apiEntityRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据实体名称查询API实体
     *
     * @param entityName 实体名称
     * @return API实体
     */
    @Override
    public Mono<APIEntity> getAPIEntityByEntityName(String entityName) {
        return apiEntityRepository.findByEntityName(entityName);
    }

    /**
     * 根据模块名称查询API实体列表
     *
     * @param moduleName 模块名称
     * @return API实体列表
     */
    @Override
    public Flux<APIEntity> getAPIEntitiesByModuleName(String moduleName) {
        return apiEntityRepository.findByModuleName(moduleName);
    }

    /**
     * 部署API实体
     *
     * @param apiEntity API实体
     * @return 部署结果
     */
    @Override
    public Mono<Boolean> deployAPIEntity(APIEntity apiEntity) {
        apiEntity.setDeployed(true);
        apiEntity.setDeployTime(new java.util.Date());
        return super.save(apiEntity).map(saved -> true);
    }

    /**
     * 删除API实体
     *
     * @param apiEntityId API实体ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteAPIEntity(Long apiEntityId) {
        return super.findById(apiEntityId)
                .switchIfEmpty(Mono.error(new BusinessException("API实体不存在")))
                .flatMap(apiEntity -> {
                    // 检查API实体是否已部署
                    if (apiEntity.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的API实体不能删除"));
                    }
                    return super.deleteById(apiEntityId).map(deleted -> true);
                });
    }
}