package org.apaas.form.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormField;
import org.apaas.form.engine.repository.FormFieldRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFormFieldServiceImpl extends BaseServiceImpl<FormField, Long, FormFieldRepository> implements ReactiveFormFieldService {

    public ReactiveFormFieldServiceImpl(FormFieldRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormField>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Flux<FormField> selectPage(Long formId, Pageable pageable) {
        return repository.findByFormId(formId)
                .sort((f1, f2) -> f1.getSort() != null && f2.getSort() != null ? f1.getSort().compareTo(f2.getSort()) : 0)
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .take(pageable.getPageSize());
    }

    @Override
    public Flux<FormField> selectByFormId(Long formId) {
        return repository.findByFormId(formId)
                .sort((f1, f2) -> f1.getSort() != null && f2.getSort() != null ? f1.getSort().compareTo(f2.getSort()) : 0);
    }

    @Override
    public Flux<FormField> selectByFormIdAndType(Long formId, Integer type) {
        return repository.findByFormIdAndType(formId, type);
    }

    @Override
    public Flux<FormField> selectByFormIdAndGroupName(Long formId, String groupName) {
        return repository.findByFormIdAndGroupName(formId, groupName);
    }

    @Override
    public Mono<FormField> create(FormField formField) {
        return save(formField);
    }

    @Override
    public Mono<FormField> update(FormField formField) {
        return save(formField);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Flux<FormField> batchCreate(Long formId, Flux<FormField> formFields) {
        return formFields
                .map(field -> {
                    field.setFormId(formId);
                    return field;
                })
                .flatMap(this::save);
    }
}