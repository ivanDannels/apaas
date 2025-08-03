package org.apaas.flow.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.flow.engine.domain.entity.FlowDefinition;
import org.apaas.flow.engine.repository.FlowDefinitionRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowDefinitionService;
import org.apaas.common.exception.BusinessException;
import org.apaas.common.web.domain.PageResult;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.event.RedisDomainEventPublisher;
import org.apaas.core.web.domain.BasePageQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式流程定义服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveFlowDefinitionServiceImpl extends BaseServiceImpl<FlowDefinition, Long, FlowDefinitionRepository> implements ReactiveFlowDefinitionService {

    private final RedisDomainEventPublisher eventPublisher;

    @Override
    public Mono<FlowDefinition> getFlowDefinitionByKey(String flowKey) {
        return repository.findByFlowKey(flowKey);
    }

    @Override
    public Mono<PageResult<FlowDefinition>> getFlowDefinitionPage(Pageable pageable) {
        return repository.findByPage(pageable)
                .map(page -> new PageResult<FlowDefinition>(page.getContent(), page.getTotalElements()));
    }

    @Override
    public Mono<FlowDefinition> addFlowDefinition(FlowDefinition flowDefinition) {
        // 检查流程定义键是否已存在
        return repository.existsByFlowKey(flowDefinition.getFlowKey())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException("流程定义键已存在"));
                    }
                    return super.save(flowDefinition);
                });
    }

    @Override
    public Mono<FlowDefinition> updateFlowDefinition(FlowDefinition flowDefinition) {
        return super.findById(flowDefinition.getId())
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(existingFlowDefinition -> {
                    // 检查流程定义键是否修改
                    if (!existingFlowDefinition.getFlowKey().equals(flowDefinition.getFlowKey())) {
                        return repository.existsByFlowKey(flowDefinition.getFlowKey())
                                .flatMap(exists -> {
                                    if (exists) {
                                        return Mono.error(new BusinessException("流程定义键已存在"));
                                    }
                                    return super.save(flowDefinition);
                                });
                    }
                    return super.save(flowDefinition);
                });
    }

    @Override
    public Mono<Void> deleteFlowDefinition(Long id) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    // 检查流程定义是否已部署
                    if (flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的流程定义不能删除"));
                    }
                    return super.deleteById(id);
                });
    }

    @Override
    public Mono<Void> deployFlowDefinition(Long id) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    if (flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("流程定义已部署"));
                    }
                    flowDefinition.setDeployed(true);
                    flowDefinition.setDeployTime(new java.util.Date());
                    return super.save(flowDefinition);
                })
                .then();
    }

    @Override
    public Mono<Void> suspendFlowDefinition(Long id) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    if (!flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("流程定义未部署"));
                    }
                    if (flowDefinition.getSuspended()) {
                        return Mono.error(new BusinessException("流程定义已挂起"));
                    }
                    flowDefinition.setSuspended(true);
                    return super.save(flowDefinition);
                })
                .then();
    }

    @Override
    public Mono<Void> activateFlowDefinition(Long id) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    if (!flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("流程定义未部署"));
                    }
                    if (!flowDefinition.getSuspended()) {
                        return Mono.error(new BusinessException("流程定义未挂起"));
                    }
                    flowDefinition.setSuspended(false);
                    return super.save(flowDefinition);
                })
                .then();
    }

    /**
     * 分页查询流程定义
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<FlowDefinition>> selectFlowDefinitionPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return repository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据流程编码查询流程定义
     *
     * @param flowCode 流程编码
     * @return 流程定义
     */
    @Override
    public Mono<FlowDefinition> getFlowDefinitionByFlowCode(String flowCode) {
        return repository.findByFlowCode(flowCode);
    }

    /**
     * 根据流程名称查询流程定义列表
     *
     * @param flowName 流程名称
     * @return 流程定义列表
     */
    @Override
    public Flux<FlowDefinition> getFlowDefinitionsByFlowName(String flowName) {
        return repository.findByFlowName(flowName);
    }

    /**
     * 部署流程定义
     *
     * @param flowDefinition 流程定义
     * @return 部署结果
     */
    @Override
    public Mono<Boolean> deployFlowDefinition(FlowDefinition flowDefinition) {
        flowDefinition.setDeployed(true);
        flowDefinition.setDeployTime(new java.util.Date());
        return super.save(flowDefinition).map(saved -> true);
    }

    /**
     * 挂起流程定义
     *
     * @param flowDefinitionId 流程定义ID
     * @return 挂起结果
     */
    @Override
    public Mono<Boolean> suspendFlowDefinition(Long flowDefinitionId) {
        return super.findById(flowDefinitionId)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    if (!flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("流程定义未部署"));
                    }
                    if (flowDefinition.getSuspended()) {
                        return Mono.error(new BusinessException("流程定义已挂起"));
                    }
                    flowDefinition.setSuspended(true);
                    return super.save(flowDefinition).map(saved -> true);
                });
    }

    /**
     * 激活流程定义
     *
     * @param flowDefinitionId 流程定义ID
     * @return 激活结果
     */
    @Override
    public Mono<Boolean> activateFlowDefinition(Long flowDefinitionId) {
        return super.findById(flowDefinitionId)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    if (!flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("流程定义未部署"));
                    }
                    if (!flowDefinition.getSuspended()) {
                        return Mono.error(new BusinessException("流程定义未挂起"));
                    }
                    flowDefinition.setSuspended(false);
                    return super.save(flowDefinition).map(saved -> true);
                });
    }

    /**
     * 删除流程定义
     *
     * @param flowDefinitionId 流程定义ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteFlowDefinition(Long flowDefinitionId) {
        return super.findById(flowDefinitionId)
                .switchIfEmpty(Mono.error(new BusinessException("流程定义不存在")))
                .flatMap(flowDefinition -> {
                    // 检查流程定义是否已部署
                    if (flowDefinition.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的流程定义不能删除"));
                    }
                    return super.deleteById(flowDefinitionId).map(deleted -> true);
                });
    }
}