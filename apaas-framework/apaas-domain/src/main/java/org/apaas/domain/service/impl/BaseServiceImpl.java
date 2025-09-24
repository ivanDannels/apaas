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
package org.apaas.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.core.context.TenantContext;
import org.apaas.domain.entity.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.domain.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * @author ivan
 */
@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable, R extends ReactiveBaseRepository<T, ID>> implements BaseService<T, ID> {
    
    protected final R repository;
    
    @Override
    public Mono<T> save(T entity) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            entity.setTenantId(tenantId);
        }
        return repository.save(entity);
    }
    
    @Override
    public Flux<T> saveAll(Iterable<T> entities) {
        // 为每个实体设置租户ID
        entities.forEach(entity -> {
            Long tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                entity.setTenantId(tenantId);
            }
        });
        
        return repository.saveAll(entities);
    }
    
    @Override
    public Flux<T> saveBatch(Flux<T> entities) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        final Long finalTenantId = tenantId;
        
        return entities.doOnNext(entity -> entity.setTenantId(finalTenantId)).flatMap(repository::save);
    }
    
    @Override
    public Flux<T> updateBatch(Flux<T> entities) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        final Long finalTenantId = tenantId;
        
        return entities.doOnNext(entity -> entity.setTenantId(finalTenantId)).flatMap(repository::save);
    }
    
    @Override
    public Mono<T> findById(ID id) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.findByIdAndTenantId(id, tenantId);
    }
    
    @Override
    public Flux<T> findAll() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.findAllByTenantIdAndDeletedFalse(tenantId, null);
    }
    
    @Override
    public Mono<Void> deleteById(ID id) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.findByIdAndTenantId(id, tenantId).flatMap(repository::delete);
    }
    
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return repository.deleteAllById(ids);
    }
    
    @Override
    public Mono<PageResult<T>> selectPage(Query query) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            tenantId = 0L;
        }
        return repository.selectPage(tenantId, query);
    }
    
    @Override
    public Mono<byte[]> export(Query query) {
        // 默认实现，子类可以覆盖
        return Mono.empty();
    }
    
    @Override
    public Mono<Void> importData(byte[] data) {
        // 默认实现，子类可以覆盖
        return Mono.empty();
    }
    
    @Override
    public R getRepository() {
        return repository;
    }
}