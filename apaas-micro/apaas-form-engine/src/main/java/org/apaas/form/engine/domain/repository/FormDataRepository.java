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
import org.apaas.form.engine.entity.FormData;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单数据响应式仓库接口
 * @author ivan
 */
@Repository
public interface FormDataRepository extends ReactiveBaseRepository<FormData, Long> {
    
    /**
     * 根据表单定义ID查询表单数据列表
     *
     * @param formDefinitionId 表单定义ID
     * @return 表单数据列表
     */
    Flux<FormData> findByFormDefinitionId(Long formDefinitionId);
    
    /**
     * 根据表单编码查询表单数据列表
     *
     * @param formCode 表单编码
     * @return 表单数据列表
     */
    Flux<FormData> findByFormCode(String formCode);
    
    /**
     * 根据表单编码和版本查询表单数据列表
     *
     * @param formCode 表单编码
     * @param version 表单版本
     * @return 表单数据列表
     */
    Flux<FormData> findByFormCodeAndVersion(String formCode, String version);
    
    /**
     * 根据业务键查询表单数据
     *
     * @param businessKey 业务键
     * @return 表单数据
     */
    Mono<FormData> findByBusinessKey(String businessKey);
    
    /**
     * 根据流程实例ID查询表单数据
     *
     * @param flowInstanceId 流程实例ID
     * @return 表单数据
     */
    Mono<FormData> findByFlowInstanceId(Long flowInstanceId);
    
    /**
     * 根据表单定义ID和状态查询表单数据列表
     *
     * @param formDefinitionId 表单定义ID
     * @param status 数据状态
     * @return 表单数据列表
     */
    Flux<FormData> findByFormDefinitionIdAndStatus(Long formDefinitionId, Integer status);
    
    /**
     * 根据提交人ID查询表单数据列表
     *
     * @param submitUserId 提交人ID
     * @return 表单数据列表
     */
    Flux<FormData> findBySubmitUserId(Long submitUserId);
}