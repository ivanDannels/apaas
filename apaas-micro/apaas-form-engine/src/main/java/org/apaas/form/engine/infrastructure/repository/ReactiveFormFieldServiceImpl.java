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
package org.apaas.form.engine.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.form.engine.domain.model.FormField;
import org.apaas.form.engine.domain.repository.FormFieldRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormFieldServiceImpl extends AbstractApplicationService<FormField, Long, FormFieldRepository> implements ReactiveFormFieldService {
    
    public ReactiveFormFieldServiceImpl(FormFieldRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<FormField> selectPage(Long formId, Pageable pageable) {
        return repository.findByFormId(formId).sort((f1, f2) -> f1.getSort() != null && f2.getSort() != null ? f1.getSort().compareTo(f2.getSort()) : 0).skip((long) pageable.getPageNumber() * pageable.getPageSize()).take(pageable.getPageSize());
    }
    
    @Override
    public Flux<FormField> selectByFormId(Long formId) {
        return repository.findByFormId(formId).sort((f1, f2) -> f1.getSort() != null && f2.getSort() != null ? f1.getSort().compareTo(f2.getSort()) : 0);
    }
    
    @Override
    public Flux<FormField> selectByFormIdAndType(Long formId, Integer type) {
        return repository.findByFormIdAndType(formId, type);
    }
    
    @Override
    public Flux<FormField> selectByFormIdAndGroupName(Long formId, String groupName) {
        return repository.findByFormIdAndGroupName(formId, groupName);
    }
    
    public Mono<FormField> create(FormField formField) {
        return save(formField);
    }
    
    public Mono<FormField> update(FormField formField) {
        return save(formField);
    }
    
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }
    
    public Flux<FormField> batchCreate(Long formId, Flux<FormField> formFields) {
        return formFields.map(field -> {
            field.setFormId(formId);
            return field;
        }).flatMap(this::save);
    }
}
