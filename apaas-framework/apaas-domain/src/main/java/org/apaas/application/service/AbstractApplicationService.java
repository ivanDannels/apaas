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

import cn.idev.excel.FastExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.application.assembler.BaseAssembler;
import org.apaas.application.dto.BaseDTO;
import org.apaas.core.context.TenantContext;
import org.apaas.domain.entity.BaseEntity;
import org.apaas.domain.exception.BusinessException;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.service.DomainService;
import org.apaas.infrastructure.convert.QueryConverter;
import org.apaas.interfaces.event.listener.ExcelListener;
import org.apaas.utils.ClassUtils;
import org.apaas.utils.FileUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 抽象应用服务实现类
 * 提供应用服务的通用实现
 *
 * @author ivan
 * @param <T> 实体类型
 * @param <D> DTO类型
 * @param <ID> 实体标识类型
 * @param <S> 仓库类型
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractApplicationService<T extends BaseEntity<ID>, D extends BaseDTO<ID>, ID extends Serializable, A extends BaseAssembler<T, D, ID>, S extends DomainService<T, ID>> implements ApplicationService<D, ID> {
    
    protected final S domainService;

    protected S getDomainService() {
        return domainService;
    }

    protected A getAssemblerInstance() {
        Class<A> assemblerClass = ClassUtils.getGenericType(getClass(), 3);
        return Mappers.getMapper(assemblerClass);
    }

    @Override
    public Mono<D> save(D dto) {
        T entity = getAssemblerInstance().toEntity(dto);
        return domainService.save(entity).map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Mono<D> findById(ID id) {
        return domainService.findById(id).map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Flux<D> findAll() {
        return domainService.findAll().map(getAssemblerInstance()::toDTO);
    }

    @Override
    public Flux<D> findAll(Query query) {
        return domainService.findAll(query).map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Mono<Void> deleteById(ID id) {
        return domainService.deleteById(id);
    }
    
    @Override
    public Flux<D> saveAll(Iterable<D> dtoList) {
        return Flux.fromIterable(dtoList)
                .map(getAssemblerInstance()::toEntity)
                .collectList()
                .flatMapMany(domainService::saveAll)
                .map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Flux<D> saveBatch(Flux<D> dtoList) {
        return dtoList
                .map(getAssemblerInstance()::toEntity)
                .collectList()
                .flatMapMany(domainService::saveAll)
                .map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Flux<D> updateBatch(Flux<D> dtoList) {
        return dtoList
                .map(getAssemblerInstance()::toEntity)
                .collectList()
                .flatMapMany(domainService::saveAll)
                .map(getAssemblerInstance()::toDTO);
    }
    
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return domainService.deleteAllById(ids);
    }
    
    @Override
    public Mono<PageResult<D>> selectPage(Query query) {
        return domainService.selectPage(TenantContext.getTenantId(), query).map(pageResult -> {
            long total = pageResult.getTotal();
            long current = pageResult.getCurrent();
            long size = pageResult.getSize();
            List<D> records = pageResult.getRecords().stream().map(getAssemblerInstance()::toDTO).toList();
            return PageResult.of(current, size, total, records);
        });
    }
    
    @Override
    public Mono<List<D>> export(Query query) {
        return findAll(query).collectList();
    }
    
    @Override
    public Mono<Void> importData(FilePart file) {
        try {
            Path filePath = FileUtils.getTempPath().resolve(file.filename());
            ExcelListener<D> excelListener = new ExcelListener<>();
            FastExcel.read(FileUtils.toInputStream(filePath), getDtoClass(), excelListener).sheet().doRead();
            List<D> dataList = excelListener.getDataList();
            return saveAll(dataList).then();
        } catch (Exception e) {
            log.error("导入数据失败", e);
            return Mono.error(new BusinessException("导入数据失败"));
        }
    }
}