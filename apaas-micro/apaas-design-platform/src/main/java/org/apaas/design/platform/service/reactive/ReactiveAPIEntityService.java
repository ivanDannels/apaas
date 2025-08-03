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

    /**
     * 分页查询API实体
     *
     * @param query 查询参数
     * @return 分页结果
     */
    Mono<PageResult<APIEntity>> selectAPIEntityPage(BasePageQuery query);

    /**
     * 根据实体名称查询API实体
     *
     * @param entityName 实体名称
     * @return API实体
     */
    Mono<APIEntity> getAPIEntityByEntityName(String entityName);

    /**
     * 根据模块名称查询API实体列表
     *
     * @param moduleName 模块名称
     * @return API实体列表
     */
    Flux<APIEntity> getAPIEntitiesByModuleName(String moduleName);

    /**
     * 部署API实体
     *
     * @param apiEntity API实体
     * @return 部署结果
     */
    Mono<Boolean> deployAPIEntity(APIEntity apiEntity);

    /**
     * 删除API实体
     *
     * @param apiEntityId API实体ID
     * @return 删除结果
     */
    Mono<Boolean> deleteAPIEntity(Long apiEntityId);
}