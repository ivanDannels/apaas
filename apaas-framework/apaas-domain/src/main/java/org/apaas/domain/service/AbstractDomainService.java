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
package org.apaas.domain.service;

import lombok.RequiredArgsConstructor;
import org.apaas.core.query.PageResult;
import org.apaas.domain.entity.BaseEntity;
import org.apaas.domain.exception.BusinessException;
import org.apaas.domain.repository.BaseRepository;
import org.apaas.domain.specification.Specification;
import org.apaas.infrastructure.convert.PageConverter;
import org.apaas.infrastructure.convert.QueryConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象领域服务基类
 * 处理跨实体的复杂业务逻辑，协调多个聚合根的操作
 *
 * @author ivan
 * @param <T> 聚合根类型
 * @param <ID> 聚合根标识类型
 * @param <R> 仓库类型
 */
@RequiredArgsConstructor
public abstract class AbstractDomainService<T extends BaseEntity<ID>, ID extends Serializable, R extends BaseRepository<T, ID>> implements DomainService<T, ID> {
    
    protected final R repository;
    protected final R2dbcEntityTemplate r2dbcEntityTemplate;
    
    /**
     * 根据规范检查聚合根是否满足业务规则
     *
     * @param aggregate 聚合根
     * @param specification 业务规范
     * @return 检查结果
     */
    protected Mono<Boolean> checkSpecification(T aggregate, Specification<T> specification) {
        return Mono.just(specification.isSatisfiedBy(aggregate));
    }
    
    /**
     * 验证聚合根并保存
     *
     * @param aggregate 聚合根
     * @param specification 业务规范
     * @return 保存后的聚合根
     */
    protected Mono<T> validateAndSave(T aggregate, Specification<T> specification) {
        return checkSpecification(aggregate, specification).flatMap(satisfied -> {
            if (!satisfied) {
                return Mono.error(new BusinessException("业务规则验证失败"));
            }
            return repository.save(aggregate);
        });
    }
    
    /**
     * 保存
     *
     * @param entity 领域对象
     * @return 处理结果
     */
    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }
    
    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);
    }
    
    /**
     * @return 查询结果
     */
    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }
    
    /**
     * @param query 查询条件
     * @return 查询结果
     */
    @Override
    public Flux<T> findAll(org.apaas.core.query.Query query) {
        Example<T> example = org.apaas.infrastructure.convert.QueryConverter.convertToExample(query, repository.getEntityClass());
        Sort sort = org.apaas.infrastructure.convert.QueryConverter.convertToSort(query, repository.getEntityClass());
        return repository.findAll(example, sort);
    }
    
    /**
     * @param id 主键ID
     * @return 删除结果
     */
    @Override
    public Mono<Void> deleteById(ID id) {
        return repository.deleteById(id);
    }
    
    /**
     * @param ts 领域对象列表
     * @return 查询结果
     */
    @Override
    public Flux<T> saveAll(List<T> ts) {
        return repository.saveAll(ts);
    }
    /**
     * @param ids 主键ID列表
     * @return 删除结果
     */
    @Override
    public Mono<Void> deleteAllById(Iterable<ID> ids) {
        return repository.deleteAllById(ids);
    }
    
    /**
     * @param query 查询条件
     * @return 查询结果
     */
    @Override
    public Mono<PageResult<T>> selectPage(org.apaas.core.query.Query query) {
        Pageable pageable = PageConverter.convertPageRequest(query);
        Sort sort = PageConverter.convertSort(query);
        // 构建基础查询条件
        Criteria criteria = QueryConverter.convertToCriteria(query, repository.getEntityClass());
        // 构建R2DBC查询
        Query r2dbcQuery = Query.query(criteria).with(pageable).sort(sort);
        // 执行分页查询
        Mono<List<T>> dataMono = r2dbcEntityTemplate.select(repository.getEntityClass()).matching(r2dbcQuery).all().collectList();
        // 计算总数
        Mono<Long> countMono = r2dbcEntityTemplate.select(repository.getEntityClass()).matching(Query.query(criteria)).count();
        return dataMono.zipWith(countMono).map(tuple -> PageResult.of(pageable.getPageNumber() + 1, pageable.getPageSize(), tuple.getT2(), tuple.getT1()));
    }
}