package org.apaas.form.engine.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormFieldPermission;
import org.apaas.form.engine.repository.FormFieldPermissionRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldPermissionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段权限服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveFormFieldPermissionServiceImpl extends BaseServiceImpl<FormFieldPermission, Long, FormFieldPermissionRepository> implements ReactiveFormFieldPermissionService {

    public ReactiveFormFieldPermissionServiceImpl(FormFieldPermissionRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormFieldPermission>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Flux<FormFieldPermission> selectPage(Long formId, Pageable pageable) {
        return repository.findByFormId(formId)
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .take(pageable.getPageSize());
    }

    @Override
    public Flux<FormFieldPermission> selectByFormId(Long formId) {
        return repository.findByFormId(formId);
    }

    @Override
    public Flux<FormFieldPermission> selectByFieldId(Long fieldId) {
        return repository.findByFieldId(fieldId);
    }

    @Override
    public Flux<FormFieldPermission> selectByRoleId(Long roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public Flux<FormFieldPermission> selectByFormIdAndRoleId(Long formId, Long roleId) {
        return repository.findByFormIdAndRoleId(formId, roleId);
    }

    @Override
    public Mono<FormFieldPermission> selectByFieldIdAndRoleId(Long fieldId, Long roleId) {
        return repository.findByFieldIdAndRoleId(fieldId, roleId);
    }

    @Override
    public Mono<FormFieldPermission> create(FormFieldPermission fieldPermission) {
        return save(fieldPermission);
    }

    @Override
    public Mono<FormFieldPermission> update(FormFieldPermission fieldPermission) {
        return save(fieldPermission);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Flux<FormFieldPermission> batchCreate(Long formId, Flux<FormFieldPermission> fieldPermissions) {
        return fieldPermissions
                .map(permission -> {
                    permission.setFormId(formId);
                    return permission;
                })
                .flatMap(this::save);
    }
}