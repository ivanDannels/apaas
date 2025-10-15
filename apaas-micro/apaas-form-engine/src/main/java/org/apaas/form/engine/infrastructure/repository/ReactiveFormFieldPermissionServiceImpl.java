/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.form.engine.service.reactive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.form.engine.domain.model.FormFieldPermission;
import org.apaas.form.engine.repository.FormFieldPermissionRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldPermissionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段权限服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormFieldPermissionServiceImpl extends AbstractApplicationService<FormFieldPermission, Long, FormFieldPermissionRepository> implements ReactiveFormFieldPermissionService {
    
    public ReactiveFormFieldPermissionServiceImpl(FormFieldPermissionRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<FormFieldPermission> selectPage(Long formId, Pageable pageable) {
        return domainService.findByFormId(formId).skip((long) pageable.getPageNumber() * pageable.getPageSize()).take(pageable.getPageSize());
    }
    
    @Override
    public Flux<FormFieldPermission> selectByFormId(Long formId) {
        return domainService.findByFormId(formId);
    }
    
    @Override
    public Flux<FormFieldPermission> selectByFieldId(Long fieldId) {
        return domainService.findByFieldId(fieldId);
    }
    
    @Override
    public Flux<FormFieldPermission> selectByRoleId(Long roleId) {
        return domainService.findByRoleId(roleId);
    }
    
    @Override
    public Flux<FormFieldPermission> selectByFormIdAndRoleId(Long formId, Long roleId) {
        return domainService.findByFormIdAndRoleId(formId, roleId);
    }
    
    @Override
    public Mono<FormFieldPermission> selectByFieldIdAndRoleId(Long fieldId, Long roleId) {
        return domainService.findByFieldIdAndRoleId(fieldId, roleId);
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
        return fieldPermissions.map(permission -> {
            permission.setFormId(formId);
            return permission;
        }).flatMap(this::save);
    }
}