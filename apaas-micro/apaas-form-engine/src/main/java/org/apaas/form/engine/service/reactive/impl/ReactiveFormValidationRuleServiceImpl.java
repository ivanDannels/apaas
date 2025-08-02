package org.apaas.form.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.domain.EntityChangedEvent;
import org.apaas.core.web.domain.PageResult;
import org.apaas.core.event.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormValidationRule;
import org.apaas.form.engine.repository.FormValidationRuleRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormValidationRuleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式表单验证规则服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFormValidationRuleServiceImpl extends BaseServiceImpl<FormValidationRule, Long, FormValidationRuleRepository> implements ReactiveFormValidationRuleService {

    public ReactiveFormValidationRuleServiceImpl(FormValidationRuleRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormValidationRule>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Mono<PageResult<FormValidationRule>> selectPage(Long fieldId, Integer pageNum, Integer pageSize) {
        return repository.findByFieldIdAndDeletedFalse(fieldId, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "sort")))
                .collectList()
                .zipWith(repository.countByFieldIdAndDeletedFalse(fieldId))
                .map(tuple -> new PageResult<>(tuple.getT1(), pageNum, pageSize, tuple.getT2()));
    }

    @Override
    public Flux<FormValidationRule> selectByFieldId(Long fieldId) {
        return repository.findByFieldId(fieldId)
                .sort((r1, r2) -> r1.getSort() != null && r2.getSort() != null ? r1.getSort().compareTo(r2.getSort()) : 0);
    }

    @Override
    public Flux<FormValidationRule> selectByFieldIdAndType(Long fieldId, Integer type) {
        return repository.findByFieldIdAndType(fieldId, type);
    }

    @Override
    public Flux<FormValidationRule> selectByFieldIds(List<Long> fieldIds) {
        return repository.findByFieldIdIn(fieldIds);
    }

    @Override
    public Mono<FormValidationRule> create(FormValidationRule validationRule) {
        return save(validationRule);
    }

    @Override
    public Mono<FormValidationRule> update(FormValidationRule validationRule) {
        return save(validationRule);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Flux<FormValidationRule> batchCreate(Long fieldId, Flux<FormValidationRule> validationRules) {
        return validationRules
                .map(rule -> {
                    rule.setFieldId(fieldId);
                    return rule;
                })
                .flatMap(this::save);
    }
}