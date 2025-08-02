package org.apaas.flow.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.repository.reactive.ReactiveFlowDefinitionRepository;
import org.apaas.flow.engine.service.reactive.ReactiveFlowDefinitionService;
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
import java.util.UUID;

/**
 * 响应式流程定义服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFlowDefinitionServiceImpl implements ReactiveFlowDefinitionService {

    private final ReactiveFlowDefinitionRepository flowDefinitionRepository;
    private final R2dbcEntityTemplate entityTemplate;

    @Override
    public Mono<Object> selectFlowDefinitionPage(FlowDefinitionDTO query) {
        // 构建查询条件
        Criteria criteria = Criteria.empty();
        
        if (StringUtils.hasText(query.getName())) {
            criteria = criteria.and(Criteria.where("name").like("%%" + query.getName() + "%%"));
        }
        
        if (StringUtils.hasText(query.getCode())) {
            criteria = criteria.and(Criteria.where("code").is(query.getCode()));
        }
        
        if (query.getStatus() != null) {
            criteria = criteria.and(Criteria.where("status").is(query.getStatus()));
        }
        
        // 创建分页和排序
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "create_time")
        );
        
        // 查询总数
        Mono<Long> countMono = entityTemplate.count(Query.query(criteria), FlowDefinition.class);
        
        // 查询数据
        Flux<FlowDefinition> dataFlux = entityTemplate.select(FlowDefinition.class)
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
    public Mono<FlowDefinition> getById(Long id) {
        return flowDefinitionRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<FlowDefinition> saveFlowDefinition(FlowDefinition flowDefinition) {
        // 设置初始状态和时间
        flowDefinition.setStatus(0); // 草稿状态
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());
        
        // 如果没有编码，生成一个唯一编码
        if (!StringUtils.hasText(flowDefinition.getCode())) {
            flowDefinition.setCode("FLOW_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        }
        
        // 设置版本为1.0
        flowDefinition.setVersion("1.0");
        
        return flowDefinitionRepository.save(flowDefinition);
    }

    @Override
    @Transactional
    public Mono<FlowDefinition> updateFlowDefinition(FlowDefinition flowDefinition) {
        return flowDefinitionRepository.findById(flowDefinition.getId())
                .flatMap(existing -> {
                    // 只更新允许修改的字段
                    existing.setName(flowDefinition.getName());
                    existing.setDescription(flowDefinition.getDescription());
                    existing.setContent(flowDefinition.getContent());
                    existing.setUpdateTime(LocalDateTime.now());
                    
                    return flowDefinitionRepository.save(existing);
                });
    }

    @Override
    @Transactional
    public Mono<Void> deleteFlowDefinitions(Flux<Long> ids) {
        return ids.flatMap(flowDefinitionRepository::deleteById).then();
    }

    @Override
    @Transactional
    public Mono<FlowDefinition> deployFlowDefinition(Long id) {
        return flowDefinitionRepository.findById(id)
                .flatMap(flowDefinition -> {
                    flowDefinition.setStatus(1); // 已发布状态
                    flowDefinition.setUpdateTime(LocalDateTime.now());
                    return flowDefinitionRepository.save(flowDefinition);
                });
    }

    @Override
    @Transactional
    public Mono<FlowDefinition> disableFlowDefinition(Long id) {
        return flowDefinitionRepository.findById(id)
                .flatMap(flowDefinition -> {
                    flowDefinition.setStatus(2); // 已停用状态
                    flowDefinition.setUpdateTime(LocalDateTime.now());
                    return flowDefinitionRepository.save(flowDefinition);
                });
    }

    @Override
    public Flux<FlowDefinition> getVersionsByCode(String code) {
        return flowDefinitionRepository.findByCodeOrderByVersionDesc(code);
    }
}