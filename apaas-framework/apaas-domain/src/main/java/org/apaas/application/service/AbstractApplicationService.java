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
package org.apaas.application.service;

import lombok.extern.slf4j.Slf4j;
import org.apaas.application.assembler.BaseAssembler;
import org.apaas.application.dto.BaseDTO;
import org.apaas.core.context.TenantContext;
import org.apaas.domain.entity.BaseEntity;
import org.apaas.domain.exception.BusinessException;
import org.apaas.domain.repository.BaseRepository;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
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
 * @param <D> DTO类型
 * @param <ID> 实体标识类型
 * @param <R> 仓库类型
 */
@Slf4j
public abstract class AbstractApplicationService<T extends BaseEntity<ID>, D extends BaseDTO<ID>, ID extends Serializable, R extends BaseRepository<T, ID>> implements ApplicationService<D, ID> {
    
    protected final R repository;
    protected final BaseAssembler<T, D, ID> assembler;
    
    protected AbstractApplicationService(R repository, BaseAssembler<T, D, ID> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    
    @Override
    public Mono<D> save(D dto) {
        T entity = assembler.toEntity(dto);
        return repository.save(entity)
                .map(assembler::toDTO);
    }
    
    @Override
    public Mono<D> findById(ID id) {
        return repository.findById(id)
                .map(assembler::toDTO);
    }
    
    @Override
    public Flux<D> findAll() {
        return repository.findAll()
                .map(assembler::toDTO);
    }
    
    @Override
    public Mono<Void> deleteById(ID id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Flux<D> saveAll(Iterable<D> dtoList) {
        return Flux.fromIterable(dtoList)
                .map(assembler::toEntity)
                .collectList()
                .flatMapMany(repository::saveAll)
                .map(assembler::toDTO);
    }
    
    @Override
    public Flux<D> saveBatch(Flux<D> dtoList) {
        return dtoList
                .map(assembler::toEntity)
                .collectList()
                .flatMapMany(repository::saveAll)
                .map(assembler::toDTO);
    }
    
    @Override
    public Flux<D> updateBatch(Flux<D> dtoList) {
        return dtoList
                .map(assembler::toEntity)
                .collectList()
                .flatMapMany(repository::saveAll)
                .map(assembler::toDTO);
    }
    
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return repository.deleteAllById(ids);
    }
    
    @Override
    public Mono<PageResult<D>> selectPage(Query query) {
        return repository.selectPage(TenantContext.getTenantId(), query).map(pageResult -> {
            long total = pageResult.getTotal();
            long current = pageResult.getCurrent();
            long size = pageResult.getSize();
            List<D> records = pageResult.getRecords().stream().map(assembler::toDTO).toList();
            return PageResult.of(current, size, total, records);
        });
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
    public Repository<?, ID> getRepository() {
        return repository;
    }
}