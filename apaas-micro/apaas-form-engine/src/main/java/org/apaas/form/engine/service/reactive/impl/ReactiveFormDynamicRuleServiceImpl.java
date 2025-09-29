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
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormDynamicRuleServiceImpl extends AbstractApplicationService<FormDynamicRule, Long, FormDynamicRuleRepository> implements ReactiveFormDynamicRuleService {
    
    public ReactiveFormDynamicRuleServiceImpl(FormDynamicRuleRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<PageResult<FormDynamicRule>> selectPage(Long formId, Integer pageNum, Integer pageSize) {
        return repository.findByFormIdAndDeletedFalse(formId, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "sort"))).collectList().zipWith(repository.countByFormIdAndDeletedFalse(formId)).map(tuple -> new PageResult<>(pageNum, pageSize, tuple.getT2(), tuple.getT1()));
    }
    
    @Override
    public Flux<FormDynamicRule> selectByFormId(Long formId) {
        return repository.findByFormId(formId).sort((r1, r2) -> r1.getSort() != null && r2.getSort() != null ? r1.getSort().compareTo(r2.getSort()) : 0);
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
        return dynamicRules.map(rule -> {
            rule.setFormId(formId);
            return rule;
        }).flatMap(this::save);
    }
}