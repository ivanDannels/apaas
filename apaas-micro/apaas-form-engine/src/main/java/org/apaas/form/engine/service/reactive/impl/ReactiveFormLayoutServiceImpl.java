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
import org.apaas.core.query.PageResult;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.form.engine.entity.FormLayout;
import org.apaas.form.engine.repository.FormLayoutRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormLayoutService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单布局服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormLayoutServiceImpl extends AbstractApplicationService<FormLayout, Long, FormLayoutRepository> implements ReactiveFormLayoutService {
    
    public ReactiveFormLayoutServiceImpl(FormLayoutRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<PageResult<FormLayout>> selectPage(Long formId, Integer pageNum, Integer pageSize) {
        return repository.findByFormIdAndDeletedFalse(formId, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "sort"))).collectList().zipWith(repository.countByFormIdAndDeletedFalse(formId)).map(tuple -> new PageResult<>(pageNum, pageSize, tuple.getT2(), tuple.getT1()));
    }
    
    @Override
    public Flux<FormLayout> selectByFormId(Long formId) {
        return repository.findByFormId(formId).sort((l1, l2) -> l1.getSort() != null && l2.getSort() != null ? l1.getSort().compareTo(l2.getSort()) : 0);
    }
    
    @Override
    public Flux<FormLayout> selectByFormIdAndType(Long formId, Integer type) {
        return repository.findByFormIdAndType(formId, type);
    }
    
    @Override
    public Flux<FormLayout> selectByFormIdAndTerminal(Long formId, Integer terminal) {
        return repository.findByFormIdAndTerminal(formId, terminal);
    }
    
    @Override
    public Mono<FormLayout> selectDefaultByFormId(Long formId) {
        return repository.findByFormIdAndIsDefault(formId, 1);
    }
    
    @Override
    public Mono<FormLayout> create(FormLayout formLayout) {
        return save(formLayout);
    }
    
    @Override
    public Mono<FormLayout> update(FormLayout formLayout) {
        return save(formLayout);
    }
    
    @Override
    public Mono<Void> delete(Long id) {
        return deleteById(id);
    }
    
    @Override
    public Mono<Boolean> setDefault(Long id) {
        return findById(id).flatMap(layout -> {
            // 先将所有布局设置为非默认
            return repository.findByFormId(layout.getFormId()).filter(l -> l.getIsDefault() != null && l.getIsDefault() == 1).flatMap(l -> {
                l.setIsDefault(0);
                return save(l);
            }).then(Mono.just(layout));
        }).flatMap(layout -> {
            // 将当前布局设置为默认
            layout.setIsDefault(1);
            return save(layout).thenReturn(true);
        }).defaultIfEmpty(false);
    }
}