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
import org.apaas.form.engine.entity.FormInstance;
import reactor.core.publisher.Mono;

/**
 * 响应式表单实例服务接��?
 * @author ivan
 */
public interface ReactiveFormInstanceService extends ApplicationService<FormInstance, Long> {
    
    /**
     * 保存表单实例
     *
     * @param formInstance 表单实例
     * @return 保存的表单实例ID
     */
    Mono<Long> saveFormInstance(FormInstance formInstance);
    
    /**
     * 更新表单实例
     *
     * @param formInstance 表单实例
     * @return 更新结果
     */
    Mono<Boolean> updateFormInstance(FormInstance formInstance);
    
    /**
     * 删除表单实例
     *
     * @param ids 表单实例ID数组
     * @return 删除结果
     */
    Mono<Boolean> deleteFormInstances(Long[] ids);
}
