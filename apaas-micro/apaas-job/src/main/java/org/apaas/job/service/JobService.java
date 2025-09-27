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
package org.apaas.job.service;

import org.apaas.domain.service.BaseService;
import org.apaas.job.entity.JobEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobService extends BaseService<JobEntity, Long> {
    
    Flux<JobEntity> findAll();
    Mono<JobEntity> findById(Long id);
    Mono<JobEntity> save(JobEntity entity);
    Mono<Void> deleteById(Long id);
    
    /**
     * 启用任务
     */
    Mono<Void> enableJob(Long id);
    
    /**
     * 禁用任务
     */
    Mono<Void> disableJob(Long id);
    
    /**
     * 手动触发任务
     */
    Mono<Void> triggerJob(Long id);
    
    /**
     * 暂停任务执行
     */
    Mono<Void> pauseJob(Long id);
    
    /**
     * 恢复任务执行
     */
    Mono<Void> resumeJob(Long id);
}