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
package org.apaas.job.service.impl;

import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.job.entity.JobEntity;
import org.apaas.job.repository.JobRepository;
import org.apaas.job.service.JobService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class JobServiceImpl extends BaseServiceImpl<JobEntity, Long, JobRepository> implements JobService {
    
    public JobServiceImpl(JobRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<JobEntity> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Mono<JobEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public Mono<JobEntity> save(JobEntity entity) {
        entity.setUpdatedTime(LocalDateTime.now());
        return repository.save(entity);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<Void> enableJob(Long id) {
        return repository.findById(id).flatMap(entity -> {
            entity.setStatus(0); // 0-启用
            entity.setUpdatedTime(LocalDateTime.now());
            return repository.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> disableJob(Long id) {
        return repository.findById(id).flatMap(entity -> {
            entity.setStatus(1); // 1-禁用
            entity.setUpdatedTime(LocalDateTime.now());
            return repository.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> triggerJob(Long id) {
        return repository.findById(id).flatMap(entity -> {
            // 记录手动触发日志
            entity.setLastTriggerTime(LocalDateTime.now());
            entity.setUpdatedTime(LocalDateTime.now());
            return repository.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> pauseJob(Long id) {
        return repository.findById(id).flatMap(entity -> {
            entity.setStatus(2); // 2-暂停
            entity.setUpdatedTime(LocalDateTime.now());
            return repository.save(entity);
        }).then();
    }
    
    @Override
    public Mono<Void> resumeJob(Long id) {
        return repository.findById(id).flatMap(entity -> {
            entity.setStatus(0); // 0-启用
            entity.setUpdatedTime(LocalDateTime.now());
            return repository.save(entity);
        }).then();
    }
}