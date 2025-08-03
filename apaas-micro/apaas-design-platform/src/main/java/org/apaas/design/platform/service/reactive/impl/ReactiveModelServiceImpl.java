package org.apaas.design.platform.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.design.platform.entity.Model;
import org.apaas.design.platform.repository.ModelRepository;
import org.apaas.design.platform.service.reactive.ReactiveModelService;
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
 * 响应式模型服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveModelServiceImpl extends BaseServiceImpl<Model, Long, ModelRepository> implements ReactiveModelService {

    private final ModelRepository modelRepository;

    /**
     * 分页查询模型
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<Model>> selectModelPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return modelRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据模型名称查询模型
     *
     * @param modelName 模型名称
     * @return 模型
     */
    @Override
    public Mono<Model> getModelByModelName(String modelName) {
        return modelRepository.findByModelName(modelName);
    }

    /**
     * 根据模块名称查询模型列表
     *
     * @param moduleName 模块名称
     * @return 模型列表
     */
    @Override
    public Flux<Model> getModelsByModuleName(String moduleName) {
        return modelRepository.findByModuleName(moduleName);
    }

    /**
     * 部署模型
     *
     * @param model 模型
     * @return 部署结果
     */
    @Override
    public Mono<Boolean> deployModel(Model model) {
        model.setDeployed(true);
        model.setDeployTime(new java.util.Date());
        return super.save(model).map(saved -> true);
    }

    /**
     * 删除模型
     *
     * @param modelId 模型ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteModel(Long modelId) {
        return super.findById(modelId)
                .switchIfEmpty(Mono.error(new BusinessException("模型不存在")))
                .flatMap(model -> {
                    // 检查模型是否已部署
                    if (model.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的模型不能删除"));
                    }
                    return super.deleteById(modelId).map(deleted -> true);
                });
    }
}