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

import org.apaas.domain.service.BaseService;
import org.apaas.form.engine.entity.FormDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单定义服务接口
 * @author ivan
 */
public interface ReactiveFormDefinitionService extends BaseService<FormDefinition, Long> {
    
    /**
     * 保存表单定义
     *
     * @param formDefinition 表单定义
     * @return 保存的表单定义ID
     */
    Mono<Long> saveFormDefinition(FormDefinition formDefinition);
    
    /**
     * 更新表单定义
     *
     * @param formDefinition 表单定义
     * @return 更新结果
     */
    Mono<Boolean> updateFormDefinition(FormDefinition formDefinition);
    
    /**
     * 删除表单定义
     *
     * @param ids 表单定义ID数组
     * @return 删除结果
     */
    Mono<Boolean> deleteFormDefinitions(Long[] ids);
    
    /**
     * 发布表单定义
     *
     * @param id 表单定义ID
     * @return 发布结果
     */
    Mono<Boolean> publishFormDefinition(Long id);
    
    /**
     * 停用表单定义
     *
     * @param id 表单定义ID
     * @return 停用结果
     */
    Mono<Boolean> disableFormDefinition(Long id);
    
    /**
     * 根据表单编码获取所有版本
     *
     * @param code 表单编码
     * @return 表单定义列表
     */
    Flux<FormDefinition> getVersionsByCode(String code);
    
    /**
     * 复制表单定义
     *
     * @param id 表单定义ID
     * @param newName 新表单名称
     * @return 新表单定义ID
     */
    Mono<Long> copyFormDefinition(Long id, String newName);
    
    /**
     * 导出表单定义
     *
     * @param id 表单定义ID
     * @return 导出的数据
     */
    Mono<byte[]> exportFormDefinition(Long id);
    
    /**
     * 导入表单定义
     *
     * @param data 导入的数据
     * @return 导入的表单定义ID
     */
    Mono<Long> importFormDefinition(byte[] data);
}