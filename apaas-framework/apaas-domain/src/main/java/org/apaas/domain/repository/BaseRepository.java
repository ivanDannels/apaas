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
package org.apaas.domain.repository;

import org.apaas.domain.entity.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.infrastructure.convert.PageConverter;
import org.apaas.infrastructure.convert.QueryConverter;
import org.apaas.utils.ClassUtils;
import org.springframework.data.domain.*;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 响应式基础仓库接口
 * @author ivan
 * @param <T> 实体类型
 * @param <ID> 主键类型
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> extends R2dbcRepository<T, ID>, ReactiveQueryByExampleExecutor<T> {
    
    /**
     * 根据租户ID和主键查找实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 实体对象
     */
    Mono<T> findByIdAndTenantId(ID id, Long tenantId);
    
    /**
     * 根据租户ID查找所有未删除的实体
     * @param tenantId 租户ID
     * @return 实体对象流
     */
    Flux<T> findAllByTenantIdAndDeletedFalse(Long tenantId);
    
    /**
     * 根据租户ID和主键删除实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 删除结果
     */
    Mono<Void> deleteByIdAndTenantId(ID id, Long tenantId);
    
    /**
     * 根据租户ID查找所有未删除的实体
     * @param tenantId 租户ID
     * @param pageable 分页参数
     * @return 分页实体对象流
     */
    Flux<Page<T>> findByTenantIdAndDeletedFalse(Long tenantId, Pageable pageable);

    /**
     * 根据租户ID查找所有未删除的实体
     * @param tenantId 租户ID
     * @param pageable 分页参数
     * @return 分页实体对象流
     */
    Flux<Page<T>> findByTenantIdAndDeletedFalseOrderByCreatedTimeDesc(Long tenantId, Pageable pageable);
    
    /**
     * 根据主键查找未删除的实体
     * @param id 主键
     * @return 实体对象
     */
    Mono<T> findByIdAndDeletedFalse(ID id);
    
    /**
     * 根据主键删除实体
     * @param id 主键
     * @return 删除结果
     */
    Mono<Void> deleteByIdAndDeletedFalse(ID id);
    
    /**
     * 根据租户ID和主键查找未删除的实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 实体对象
     */
    Mono<T> findByIdAndTenantIdAndDeletedFalse(ID id, Long tenantId);
    
    /**
     * 根据租户ID查找所有未删除的实体总数
     * @param tenantId 租户ID
     * @return 实体总数
     */
    Mono<Long> countByTenantIdAndDeletedFalse(Long tenantId);
    
    /**
     * 根据租户ID查找所有未删除的实体是否存在
     * @param id 主键
     * @param tenantId 租户ID
     * @return 响应式实体对象流
     */
    Mono<Boolean> existsByIdAndTenantIdAndDeletedFalse(ID id, Long tenantId);

    /**
     * 获取实体类型
     * @return 实体类型
     */
    default Class<T> getEntityClass() {
        return ClassUtils.getGenericType(getClass(), 0);
    }

}