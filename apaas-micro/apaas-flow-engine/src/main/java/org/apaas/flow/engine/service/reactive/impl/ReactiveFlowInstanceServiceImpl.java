package org.apaas.flow.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.flow.engine.domain.entity.FlowInstance;
import org.apaas.flow.engine.repository.FlowInstanceRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
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
 * 响应式流程实例服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveFlowInstanceServiceImpl extends BaseServiceImpl<FlowInstance, Long, FlowInstanceRepository> implements ReactiveFlowInstanceService {

    private final FlowInstanceRepository flowInstanceRepository;

    @Override
    public Mono<FlowInstance> getFlowInstanceById(String instanceId) {
        return flowInstanceRepository.findByInstanceId(instanceId);
    }

    @Override
    public Mono<PageResult<FlowInstance>> getFlowInstancePage(Pageable pageable) {
        return flowInstanceRepository.findByPage(pageable)
                .map(page -> new PageResult<FlowInstance>(page.getContent(), page.getTotalElements()));
    }

    @Override
    public Mono<FlowInstance> startFlowInstance(Long flowDefinitionId, Long starterId, java.util.Map<String, Object> variables) {
        // 这里需要实现启动流程实例的逻辑
        // 暂时返回空的Mono，实际开发中需要实现具体的启动逻辑
        return Mono.empty();
    }

    @Override
    public Mono<Void> suspendFlowInstance(String instanceId) {
        return flowInstanceRepository.findByInstanceId(instanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    if (flowInstance.getSuspended()) {
                        return Mono.error(new BusinessException("流程实例已挂起"));
                    }
                    flowInstance.setSuspended(true);
                    return flowInstanceRepository.save(flowInstance);
                })
                .then();
    }

    @Override
    public Mono<Void> activateFlowInstance(String instanceId) {
        return flowInstanceRepository.findByInstanceId(instanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    if (!flowInstance.getSuspended()) {
                        return Mono.error(new BusinessException("流程实例未挂起"));
                    }
                    flowInstance.setSuspended(false);
                    return flowInstanceRepository.save(flowInstance);
                })
                .then();
    }

    @Override
    public Mono<Void> terminateFlowInstance(String instanceId) {
        return flowInstanceRepository.findByInstanceId(instanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    // 这里需要实现终止流程实例的逻辑
                    // 暂时返回空的Mono，实际开发中需要实现具体的终止逻辑
                    return Mono.empty();
                })
                .then();
    }

    /**
     * 分页查询流程实例
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<FlowInstance>> selectFlowInstancePage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return flowInstanceRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据流程定义ID查询流程实例列表
     *
     * @param flowDefinitionId 流程定义ID
     * @return 流程实例列表
     */
    @Override
    public Flux<FlowInstance> getFlowInstancesByFlowDefinitionId(Long flowDefinitionId) {
        return flowInstanceRepository.findByFlowDefinitionId(flowDefinitionId);
    }

    /**
     * 根据业务键查询流程实例
     *
     * @param businessKey 业务键
     * @return 流程实例
     */
    @Override
    public Mono<FlowInstance> getFlowInstanceByBusinessKey(String businessKey) {
        return flowInstanceRepository.findByBusinessKey(businessKey);
    }

    /**
     * 启动流程实例
     *
     * @param flowInstance 流程实例
     * @return 启动结果
     */
    @Override
    public Mono<Long> startFlowInstance(FlowInstance flowInstance) {
        // 这里需要实现启动流程实例的逻辑
        // 暂时返回空的Mono，实际开发中需要实现具体的启动逻辑
        return Mono.just(0L);
    }

    /**
     * 挂起流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @return 挂起结果
     */
    @Override
    public Mono<Boolean> suspendFlowInstance(Long flowInstanceId) {
        return super.findById(flowInstanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    if (flowInstance.getSuspended()) {
                        return Mono.error(new BusinessException("流程实例已挂起"));
                    }
                    flowInstance.setSuspended(true);
                    return super.save(flowInstance).map(saved -> true);
                });
    }

    /**
     * 激活流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @return 激活结果
     */
    @Override
    public Mono<Boolean> activateFlowInstance(Long flowInstanceId) {
        return super.findById(flowInstanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    if (!flowInstance.getSuspended()) {
                        return Mono.error(new BusinessException("流程实例未挂起"));
                    }
                    flowInstance.setSuspended(false);
                    return super.save(flowInstance).map(saved -> true);
                });
    }

    /**
     * 终止流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @return 终止结果
     */
    @Override
    public Mono<Boolean> terminateFlowInstance(Long flowInstanceId) {
        return super.findById(flowInstanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    // 这里需要实现终止流程实例的逻辑
                    // 暂时返回true，实际开发中需要实现具体的终止逻辑
                    return Mono.just(true);
                });
    }

    /**
     * 删除流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteFlowInstance(Long flowInstanceId) {
        return super.findById(flowInstanceId)
                .switchIfEmpty(Mono.error(new BusinessException("流程实例不存在")))
                .flatMap(flowInstance -> {
                    return super.deleteById(flowInstanceId).map(deleted -> true);
                });
    }
}