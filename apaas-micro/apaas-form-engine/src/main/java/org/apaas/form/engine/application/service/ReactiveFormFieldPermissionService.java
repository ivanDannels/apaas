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
import org.apaas.form.engine.domain.model.FormFieldPermission;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段权限服务接口
 * @author ivan
 */
public interface ReactiveFormFieldPermissionService extends ApplicationService<FormFieldPermission, Long> {
    
    /**
     * 分页查询表单字段权限
     *
     * @param formId 表单ID
     * @param pageable 分页参数
     * @return 分页结果
     */
    Flux<FormFieldPermission> selectPage(Long formId, Pageable pageable);
    
    /**
     * 根据表单ID查询字段权限列表
     *
     * @param formId 表单ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> selectByFormId(Long formId);
    
    /**
     * 根据字段ID查询字段权限列表
     *
     * @param fieldId 字段ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> selectByFieldId(Long fieldId);
    
    /**
     * 根据角色ID查询字段权限列表
     *
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> selectByRoleId(Long roleId);
    
    /**
     * 根据表单ID和角色ID查询字段权限列表
     *
     * @param formId 表单ID
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> selectByFormIdAndRoleId(Long formId, Long roleId);
    
    /**
     * 根据字段ID和角色ID查询字段权限
     *
     * @param fieldId 字段ID
     * @param roleId 角色ID
     * @return 字段权限
     */
    Mono<FormFieldPermission> selectByFieldIdAndRoleId(Long fieldId, Long roleId);
    
    /**
     * 创建表单字段权限
     *
     * @param fieldPermission 表单字段权限
     * @return 是否成功
     */
    Mono<FormFieldPermission> create(FormFieldPermission fieldPermission);
    
    /**
     * 更新表单字段权限
     *
     * @param fieldPermission 表单字段权限
     * @return 是否成功
     */
    Mono<FormFieldPermission> update(FormFieldPermission fieldPermission);
    
    /**
     * 删除表单字段权限
     *
     * @param id 权限ID
     * @return 是否成功
     */
    Mono<Void> delete(Long id);
    
    /**
     * 批量创建表单字段权限
     *
     * @param formId 表单ID
     * @param fieldPermissions 表单字段权限列表
     * @return 是否成功
     */
    Flux<FormFieldPermission> batchCreate(Long formId, Flux<FormFieldPermission> fieldPermissions);
}
