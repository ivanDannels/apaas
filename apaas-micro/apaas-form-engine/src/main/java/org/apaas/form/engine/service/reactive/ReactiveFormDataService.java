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

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.form.engine.entity.FormData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单数据服务接口
 */
public interface ReactiveFormDataService extends ApplicationService<FormData, Long> {
    
    /**
     * 根据表单编码查询表单数据
     *
     * @param formCode 表单编码
     * @return 表单数据列表
     */
    Flux<FormData> getFormDataByFormCode(String formCode);
    
    /**
     * 根据表单编码和版本查询表单数据
     *
     * @param formCode 表单编码
     * @param version 表单版本
     * @return 表单数据列表
     */
    Flux<FormData> getFormDataByFormCodeAndVersion(String formCode, String version);
    
    /**
     * 根据业务键查询表单数据
     *
     * @param businessKey 业务键
     * @return 表单数据
     */
    Mono<FormData> getFormDataByBusinessKey(String businessKey);
    
    /**
     * 保存表单数据（草稿状态）
     *
     * @param formData 表单数据
     * @return 保存结果
     */
    Mono<Long> saveFormData(FormData formData);
    
    /**
     * 提交表单数据
     *
     * @param formData 表单数据
     * @return 提交结果
     */
    Mono<Long> submitFormData(FormData formData);
    
    /**
     * 更新表单数据
     *
     * @param formData 表单数据
     * @return 更新结果
     */
    Mono<Boolean> updateFormData(FormData formData);
    
    /**
     * 删除表单数据
     *
     * @param ids 表单数据ID数组
     * @return 删除结果
     */
    Mono<Boolean> deleteFormData(Long[] ids);
    
    /**
     * 批量导入表单数据
     *
     * @param formCode 表单编码
     * @param dataJson 数据JSON
     * @return 导入结果
     */
    Mono<Boolean> importFormData(String formCode, String dataJson);
    
    /**
     * 获取表单数据统计
     *
     * @param formCode 表单编码
     * @return 统计结果
     */
    Mono<Object> getFormDataStatistics(String formCode);
    
    /**
     * 验证表单数据
     *
     * @param formData 表单数据
     * @return 验证结果
     */
    Mono<Boolean> validateFormData(FormData formData);
}