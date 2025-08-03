package org.apaas.design.platform.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.Model;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式模型服务接口
 */
public interface ReactiveModelService extends BaseService<Model, Long> {

    /**
     * 分页查询模型
     *
     * @param query 查询参数
     * @return 分页结果
     */
    Mono<PageResult<Model>> selectModelPage(BasePageQuery query);

    /**
     * 根据模型名称查询模型
     *
     * @param modelName 模型名称
     * @return 模型
     */
    Mono<Model> getModelByModelName(String modelName);

    /**
     * 根据模块名称查询模型列表
     *
     * @param moduleName 模块名称
     * @return 模型列表
     */
    Flux<Model> getModelsByModuleName(String moduleName);

    /**
     * 部署模型
     *
     * @param model 模型
     * @return 部署结果
     */
    Mono<Boolean> deployModel(Model model);

    /**
     * 删除模型
     *
     * @param modelId 模型ID
     * @return 删除结果
     */
    Mono<Boolean> deleteModel(Long modelId);
}