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
import org.apaas.domain.service.BaseService;
import org.apaas.form.engine.entity.FormDynamicRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单动态规则服务接口
 */
public interface ReactiveFormDynamicRuleService extends BaseService<FormDynamicRule, Long> {
    
    /**
     * 分页查询表单动态规则
     *
     * @param formId 表单ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    Mono<PageResult<FormDynamicRule>> selectPage(Long formId, Integer pageNum, Integer pageSize);
    
    /**
     * 根据表单ID查询动态规则列表
     *
     * @param formId 表单ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByFormId(Long formId);
    
    /**
     * 根据表单ID和规则类型查询动态规则列表
     *
     * @param formId 表单ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByFormIdAndType(Long formId, Integer type);
    
    /**
     * 根据目标字段ID查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByTargetFieldId(Long targetFieldId);
    
    /**
     * 根据目标字段ID和规则类型查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByTargetFieldIdAndType(Long targetFieldId, Integer type);
    
    /**
     * 创建表单动态规则
     *
     * @param dynamicRule 表单动态规则
     * @return 创建的表单动态规则
     */
    Mono<FormDynamicRule> create(FormDynamicRule dynamicRule);
    
    /**
     * 更新表单动态规则
     *
     * @param dynamicRule 表单动态规则
     * @return 更新的表单动态规则
     */
    Mono<FormDynamicRule> update(FormDynamicRule dynamicRule);
    
    /**
     * 删除表单动态规则
     *
     * @param id 规则ID
     * @return 操作结果
     */
    Mono<Void> delete(Long id);
    
    /**
     * 批量创建表单动态规则
     *
     * @param formId 表单ID
     * @param dynamicRules 表单动态规则列表
     * @return 创建的表单动态规则列表
     */
    Flux<FormDynamicRule> batchCreate(Long formId, Flux<FormDynamicRule> dynamicRules);
}