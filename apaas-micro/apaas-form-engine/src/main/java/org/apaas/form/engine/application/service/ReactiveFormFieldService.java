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
package org.apaas.form.engine.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.form.engine.domain.model.FormField;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段服务接口
 * @author ivan
 */
public interface ReactiveFormFieldService extends ApplicationService<FormField, Long> {
    
    /**
     * 分页查询表单字段
     *
     * @param formId 表单ID
     * @param pageable 分页参数
     * @return 分页结果
     */
    Flux<FormField> selectPage(Long formId, Pageable pageable);
    
    /**
     * 根据表单ID查询字段列表
     *
     * @param formId 表单ID
     * @return 字段列表
     */
    Flux<FormField> selectByFormId(Long formId);
    
    /**
     * 根据表单ID和字段类型查询字段列表
     *
     * @param formId 表单ID
     * @param type 字段类型
     * @return 字段列表
     */
    Flux<FormField> selectByFormIdAndType(Long formId, Integer type);
    
    /**
     * 根据表单ID和分组名称查询字段列表
     *
     * @param formId 表单ID
     * @param groupName 分组名称
     * @return 字段列表
     */
    Flux<FormField> selectByFormIdAndGroupName(Long formId, String groupName);
    
    /**
     * 创建表单字段
     *
     * @param formField 表单字段
     * @return 创建的表单字段
     */
    Mono<FormField> create(FormField formField);
    
    /**
     * 更新表单字段
     *
     * @param formField 表单字段
     * @return 更新后的表单字段
     */
    Mono<FormField> update(FormField formField);
    
    /**
     * 删除表单字段
     *
     * @param id 字段ID
     * @return 是否成功
     */
    Mono<Void> delete(Long id);
    
    /**
     * 批量创建表单字段
     *
     * @param formId 表单ID
     * @param formFields 表单字段列表
     * @return 创建的表单字段列表
     */
    Flux<FormField> batchCreate(Long formId, Flux<FormField> formFields);
}