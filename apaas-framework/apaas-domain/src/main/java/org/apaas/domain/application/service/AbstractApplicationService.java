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
package org.apaas.domain.application.service;

import org.apaas.core.context.TenantContext;
import org.apaas.domain.domain.entity.BaseEntity;
import org.apaas.domain.domain.exception.BusinessException;
import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.infrastructure.convert.PageConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象应用服务实现类
 * 提供应用服务的通用实现
 *
 * @author ivan
 * @param <T> 实体类型
 * @param <ID> 实体标识类型
 * @param <R> 仓库类型
 */
public abstract class AbstractApplicationService<T extends BaseEntity<ID>, ID extends Serializable, R extends ReactiveBaseRepository<T, ID>> implements ApplicationService<T, ID> {
    
    protected final R repository;
    
    protected AbstractApplicationService(R repository) {
        this.repository = repository;
    }
    
    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }
    
    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);
    }
    
    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Mono<Void> deleteById(ID id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Flux<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }
    
    @Override
    public Flux<T> saveBatch(Flux<T> entities) {
        return repository.saveAll(entities);
    }
    
    @Override
    public Flux<T> updateBatch(Flux<T> entities) {
        return repository.saveAll(entities);
    }
    
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return repository.deleteAllById(ids);
    }
    
    @Override
    public Mono<PageResult<T>> selectPage(Query query) {
        return repository.selectPage(TenantContext.getTenantId(), query);
    }
    
    @Override
    public Mono<byte[]> export(Query query) {
        // 默认实现，子类可以重写
        return Mono.error(new BusinessException("导出功能未实现"));
    }
    
    @Override
    public Mono<Void> importData(byte[] data) {
        // 默认实现，子类可以重写
        return Mono.error(new BusinessException("导入功能未实现"));
    }
    
    @Override
    public Repository<T, ID> getRepository() {
        return repository;
    }
}