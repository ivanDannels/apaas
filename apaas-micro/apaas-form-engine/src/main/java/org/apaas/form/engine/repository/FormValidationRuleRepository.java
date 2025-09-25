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
package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormValidationRule;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 表单验证规则响应式仓库接口
 */
@Repository
public interface FormValidationRuleRepository extends ReactiveBaseRepository<FormValidationRule, Long> {
    
    /**
     * 根据字段ID查询验证规则列表
     *
     * @param fieldId 字段ID
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldId(Long fieldId);
    
    /**
     * 根据字段ID和规则类型查询验证规则列表
     *
     * @param fieldId 字段ID
     * @param type 规则类型
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldIdAndType(Long fieldId, Integer type);
    
    /**
     * 根据字段ID列表查询验证规则列表
     *
     * @param fieldIds 字段ID列表
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldIdIn(List<Long> fieldIds);
    
    Flux<FormValidationRule> findByFieldIdAndDeletedFalse(Long fieldId, PageRequest sort);
    
    Mono<Integer> countByFieldIdAndDeletedFalse(Long fieldId);
}