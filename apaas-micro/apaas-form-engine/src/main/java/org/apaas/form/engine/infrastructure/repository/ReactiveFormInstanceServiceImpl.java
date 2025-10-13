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
package org.apaas.form.engine.service.reactive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.form.engine.entity.FormInstance;
import org.apaas.form.engine.repository.FormInstanceRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式表单实例服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormInstanceServiceImpl extends AbstractApplicationService<FormInstance, Long, FormInstanceRepository> implements ReactiveFormInstanceService {
    
    public ReactiveFormInstanceServiceImpl(FormInstanceRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<Long> saveFormInstance(FormInstance formInstance) {
        // 设置创建时间、更新时间
        LocalDateTime now = LocalDateTime.now();
        formInstance.setCreatedTime(now);
        formInstance.setUpdatedTime(now);
        return save(formInstance).map(FormInstance::getId);
    }
    
    @Override
    public Mono<Boolean> updateFormInstance(FormInstance formInstance) {
        formInstance.setUpdatedTime(LocalDateTime.now());
        return save(formInstance).map(saved -> true);
    }
    
    @Override
    public Mono<Boolean> deleteFormInstances(Long[] ids) {
        return Flux.fromArray(ids).flatMap(this::deleteById).then(Mono.just(true));
    }
}