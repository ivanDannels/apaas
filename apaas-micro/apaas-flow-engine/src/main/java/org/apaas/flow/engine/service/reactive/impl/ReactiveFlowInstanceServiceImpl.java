package org.apaas.flow.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowDefinitionRepository;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowInstanceRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowInstanceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式流程实例服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFlowInstanceServiceImpl implements ReactiveFlowInstanceService {

    private final ReactiveFlowInstanceRepository flowInstanceRepository;
    private final ReactiveFlowDefinitionRepository flowDefinitionRepository;
    private final R2dbcEntityTemplate entityTemplate;

    @Override
    public Mono<Object> selectFlowInstancePage(FlowInstanceDTO query) {
        // 构建查询条件
        Criteria criteria = Criteria.empty();
        
        if (query.getFlowDefinitionId() != null) {
            criteria = criteria.and(Criteria.where("flow_definition_id").is(query.getFlowDefinitionId()));
        }
        
        if (StringUtils.hasText(query.getBusinessKey())) {
            criteria = criteria.and(Criteria.where("business_key").is(query.getBusinessKey()));
        }
        
        if (query.getStatus() != null) {
            criteria = criteria.and(Criteria.where("status").is(query.getStatus()));
        }
        
        if (query.getStarterId() != null) {
            criteria = criteria.and(Criteria.where("starter_id").is(query.getStarterId()));
        }
        
        // 创建分页和排序
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "create_time")
        );
        
        // 查询总数
        Mono<Long> countMono = entityTemplate.count(Query.query(criteria), FlowInstance.class);
        
        // 查询数据
        Flux<FlowInstance> dataFlux = entityTemplate.select(FlowInstance.class)
                .matching(Query.query(criteria).with(pageRequest))
                .all();
        
        // 组合结果
        return Mono.zip(countMono, dataFlux.collectList())
                .map(tuple -> {
                    Long total = tuple.getT1();
                    var records = tuple.getT2();
                    
                    return new Object() {
                        public final Long total = total;
                        public final Integer pageNum = query.getPageNum();
                        public final Integer pageSize = query.getPageSize();
                        public final Object records = records;
                    };
                });
    }

    @Override
    public Mono<Object> getFlowInstanceDetail(Long id) {
        return flowInstanceRepository.findById(id)
                .flatMap(instance -> {
                    // 获取流程定义信息
                    Mono<FlowDefinition> definitionMono = flowDefinitionRepository.findById(instance.getFlowDefinitionId());
                    
                    // 获取任务信息
                    Flux<Object> tasksFlux = getInstanceTasks(id).collectList();
                    
                    // 组合结果
                    return Mono.zip(Mono.just(instance), definitionMono, tasksFlux)
                            .map(tuple -> {
                                FlowInstance flowInstance = tuple.getT1();
                                FlowDefinition flowDefinition = tuple.getT2();
                                var tasks = tuple.getT3();
                                
                                return new Object() {
                                    public final FlowInstance instance = flowInstance;
                                    public final FlowDefinition definition = flowDefinition;
                                    public final Object tasks = tasks;
                                };
                            });
                });
    }

    @Override
    @Transactional
    public Mono<FlowInstance> startInstance(StartInstanceDTO startInstanceDTO) {
        return flowDefinitionRepository.findById(startInstanceDTO.getFlowDefinitionId())
                .flatMap(definition -> {
                    FlowInstance instance = new FlowInstance();
                    instance.setFlowDefinitionId(definition.getId());
                    instance.setFlowCode(definition.getCode());
                    instance.setFlowVersion(definition.getVersion());
                    instance.setFlowName(definition.getName());
                    instance.setBusinessKey(startInstanceDTO.getBusinessKey());
                    instance.setStarterId(startInstanceDTO.getStarterId());
                    instance.setStatus(1); // 运行中状态
                    instance.setCreateTime(LocalDateTime.now());
                    instance.setUpdateTime(LocalDateTime.now());
                    
                    return flowInstanceRepository.save(instance);
                });
    }

    @Override
    @Transactional
    public Mono<FlowInstance> terminateInstance(Long id) {
        return flowInstanceRepository.findById(id)
                .flatMap(instance -> {
                    instance.setStatus(3); // 已终止状态
                    instance.setEndTime(LocalDateTime.now());
                    instance.setUpdateTime(LocalDateTime.now());
                    return flowInstanceRepository.save(instance);
                });
    }

    @Override
    @Transactional
    public Mono<FlowInstance> suspendInstance(Long id) {
        return flowInstanceRepository.findById(id)
                .flatMap(instance -> {
                    instance.setStatus(4); // 已暂停状态
                    instance.setUpdateTime(LocalDateTime.now());
                    return flowInstanceRepository.save(instance);
                });
    }

    @Override
    @Transactional
    public Mono<FlowInstance> resumeInstance(Long id) {
        return flowInstanceRepository.findById(id)
                .flatMap(instance -> {
                    instance.setStatus(1); // 运行中状态
                    instance.setUpdateTime(LocalDateTime.now());
                    return flowInstanceRepository.save(instance);
                });
    }

    @Override
    public Flux<Object> getInstanceTasks(Long id) {
        // 这里应该查询流程任务表，返回任务列表
        // 由于没有具体的任务表结构，这里返回空列表
        return Flux.empty();
    }
}