package org.apaas.form.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.domain.EntityChangedEvent;
import org.apaas.core.domain.Page;
import org.apaas.core.event.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormDynamicRule;
import org.apaas.form.engine.repository.FormDynamicRuleRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormDynamicRuleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单动态规则服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFormDynamicRuleServiceImpl extends BaseServiceImpl<FormDynamicRule, Long, FormDynamicRuleRepository> implements ReactiveFormDynamicRuleService {

    public ReactiveFormDynamicRuleServiceImpl(FormDynamicRuleRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormDynamicRule>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<Page<FormDynamicRule>> selectPage(Long formId, Integer pageNum, Integer pageSize) {
        return repository.findByFormIdAndDeletedFalse(formId, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "sort")))
                .collectList()
                .zipWith(repository.countByFormIdAndDeletedFalse(formId))
                .map(tuple -> new Page<>(tuple.getT1(), pageNum, pageSize, tuple.getT2()));
    }

    @Override
    public Flux<FormDynamicRule> selectByFormId(Long formId) {
        return repository.findByFormId(formId)
                .sort((r1, r2) -> r1.getSort() != null && r2.getSort() != null ? r1.getSort().compareTo(r2.getSort()) : 0);
    }

    @Override
    public Flux<FormDynamicRule> selectByFormIdAndType(Long formId, Integer type) {
        return repository.findByFormIdAndType(formId, type);
    }

    @Override
    public Flux<FormDynamicRule> selectByTargetFieldId(Long targetFieldId) {
        return repository.findByTargetFieldId(targetFieldId);
    }

    @Override
    public Flux<FormDynamicRule> selectByTargetFieldIdAndType(Long targetFieldId, Integer type) {
        return repository.findByTargetFieldIdAndType(targetFieldId, type);
    }

    @Override
    public Mono<FormDynamicRule> create(FormDynamicRule dynamicRule) {
        return save(dynamicRule);
    }

    @Override
    public Mono<FormDynamicRule> update(FormDynamicRule dynamicRule) {
        return save(dynamicRule);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Flux<FormDynamicRule> batchCreate(Long formId, Flux<FormDynamicRule> dynamicRules) {
        return dynamicRules
                .map(rule -> {
                    rule.setFormId(formId);
                    return rule;
                })
                .flatMap(this::save);
    }
}