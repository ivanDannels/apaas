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

import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormFieldPermission;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单字段权限仓库接口
 */
@Repository
public interface FormFieldPermissionRepository extends ReactiveBaseRepository<FormFieldPermission, Long> {
    
    /**
     * 根据表单ID查询字段权限列表
     *
     * @param formId 表单ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFormId(Long formId);
    
    /**
     * 根据字段ID查询字段权限列表
     *
     * @param fieldId 字段ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFieldId(Long fieldId);
    
    /**
     * 根据角色ID查询字段权限列表
     *
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByRoleId(Long roleId);
    
    /**
     * 根据表单ID和角色ID查询字段权限列表
     *
     * @param formId 表单ID
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFormIdAndRoleId(Long formId, Long roleId);
    
    /**
     * 根据字段ID和角色ID查询字段权限
     *
     * @param fieldId 字段ID
     * @param roleId 角色ID
     * @return 字段权限
     */
    Mono<FormFieldPermission> findByFieldIdAndRoleId(Long fieldId, Long roleId);
}