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
import org.apaas.form.engine.entity.FormDefinition;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单定义响应式仓库接口
 */
@Repository
public interface FormDefinitionRepository extends ReactiveBaseRepository<FormDefinition, Long> {
    
    /**
     * 根据表单编码查询表单定义列表
     *
     * @param code 表单编码
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByCode(String code);
    
    /**
     * 根据表单编码和状态查询表单定义列表
     *
     * @param code 表单编码
     * @param status 表单状态
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByCodeAndStatus(String code, Integer status);
    
    /**
     * 根据表单类型查询表单定义列表
     *
     * @param type 表单类型
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByType(Integer type);
    
    /**
     * 根据表单状态查询表单定义列表
     *
     * @param status 表单状态
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByStatus(Integer status);
    
    /**
     * 根据表单编码更新默认版本状态
     *
     * @param code 表单编码
     * @param isDefault 是否默认版本
     * @return 更新结果
     */
    Mono<Void> updateIsDefaultByCode(String code, Boolean isDefault);
}