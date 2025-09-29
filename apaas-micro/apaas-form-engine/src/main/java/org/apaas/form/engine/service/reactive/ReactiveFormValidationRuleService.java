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
package org.apaas.form.engine.service.reactive;

import org.apaas.core.query.PageResult;
import org.apaas.domain.application.service.ApplicationService;
import org.apaas.form.engine.entity.FormValidationRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式表单验证规则服务接口
 */
public interface ReactiveFormValidationRuleService extends ApplicationService<FormValidationRule, Long> {
    
    /**
     * 分页查询表单验证规则
     *
     * @param fieldId 字段ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    Mono<PageResult<FormValidationRule>> selectPage(Long fieldId, Integer pageNum, Integer pageSize);
    
    /**
     * 根据字段ID查询验证规则列表
     *
     * @param fieldId 字段ID
     * @return 验证规则列表
     */
    Flux<FormValidationRule> selectByFieldId(Long fieldId);
    
    /**
     * 根据字段ID和规则类型查询验证规则列表
     *
     * @param fieldId 字段ID
     * @param type 规则类型
     * @return 验证规则列表
     */
    Flux<FormValidationRule> selectByFieldIdAndType(Long fieldId, Integer type);
    
    /**
     * 根据字段ID列表查询验证规则列表
     *
     * @param fieldIds 字段ID列表
     * @return 验证规则列表
     */
    Flux<FormValidationRule> selectByFieldIds(List<Long> fieldIds);
    
    /**
     * 创建表单验证规则
     *
     * @param validationRule 表单验证规则
     * @return 创建的表单验证规则
     */
    Mono<FormValidationRule> create(FormValidationRule validationRule);
    
    /**
     * 更新表单验证规则
     *
     * @param validationRule 表单验证规则
     * @return 更新的表单验证规则
     */
    Mono<FormValidationRule> update(FormValidationRule validationRule);
    
    /**
     * 删除表单验证规则
     *
     * @param id 规则ID
     * @return 操作结果
     */
    Mono<Void> delete(Long id);
    
    /**
     * 批量创建表单验证规则
     *
     * @param fieldId 字段ID
     * @param validationRules 表单验证规则列表
     * @return 创建的表单验证规则列表
     */
    Flux<FormValidationRule> batchCreate(Long fieldId, Flux<FormValidationRule> validationRules);
}