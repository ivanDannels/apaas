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
import org.apaas.form.engine.entity.FormDynamicRule;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单动态规则响应式仓库接口
 */
@Repository
public interface FormDynamicRuleRepository extends ReactiveBaseRepository<FormDynamicRule, Long> {
    
    /**
     * 根据表单ID查询动态规则列表
     *
     * @param formId 表单ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByFormId(Long formId);
    
    /**
     * 根据表单ID和规则类型查询动态规则列表
     *
     * @param formId 表单ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByFormIdAndType(Long formId, Integer type);
    
    /**
     * 根据目标字段ID查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByTargetFieldId(Long targetFieldId);
    
    /**
     * 根据目标字段ID和规则类型查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByTargetFieldIdAndType(Long targetFieldId, Integer type);
    
    Flux<FormDynamicRule> findByFormIdAndDeletedFalse(Long formId, PageRequest sort);
    
    Mono<Integer> countByFormIdAndDeletedFalse(Long formId);
}