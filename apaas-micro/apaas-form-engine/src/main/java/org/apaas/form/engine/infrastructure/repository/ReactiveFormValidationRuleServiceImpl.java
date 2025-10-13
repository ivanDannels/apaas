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
import org.apaas.application.service.AbstractApplicationService;
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
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormValidationRuleServiceImpl extends AbstractApplicationService<FormValidationRule, Long, FormValidationRuleRepository> implements ReactiveFormValidationRuleService {
    
    public ReactiveFormValidationRuleServiceImpl(FormValidationRuleRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<PageResult<FormValidationRule>> selectPage(Long fieldId, Integer pageNum, Integer pageSize) {
        return repository.findByFieldIdAndDeletedFalse(fieldId, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "sort"))).collectList().zipWith(repository.countByFieldIdAndDeletedFalse(fieldId)).map(tuple -> new PageResult<>(pageNum, pageSize, tuple.getT2(), tuple.getT1()));
    }
    
    @Override
    public Flux<FormValidationRule> selectByFieldId(Long fieldId) {
        return repository.findByFieldId(fieldId).sort((r1, r2) -> r1.getSort() != null && r2.getSort() != null ? r1.getSort().compareTo(r2.getSort()) : 0);
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
        return validationRules.map(rule -> {
            rule.setFieldId(fieldId);
            return rule;
        }).flatMap(this::save);
    }
}