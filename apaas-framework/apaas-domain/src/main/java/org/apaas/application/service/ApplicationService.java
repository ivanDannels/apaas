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

import org.apaas.application.dto.BaseDTO;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 应用服务接口
 * 处理应用层逻辑，协调领域服务和基础设施层
 *
 * @author ivan
 * @param <D> DTO类型
 * @param <ID> 实体标识类型
 */
public interface ApplicationService<D extends BaseDTO<ID>, ID extends Serializable> {
    
    /**
     * 保存实体
     *
     * @param dto DTO对象
     * @return 保存后的DTO
     */
    Mono<D> save(D dto);
    
    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return DTO对象
     */
    Mono<D> findById(ID id);
    
    /**
     * 查找所有实体
     *
     * @return DTO对象列表
     */
    Flux<D> findAll();
    
    /**
     * 根据ID删除实体
     *
     * @param id 实体ID
     */
    Mono<Void> deleteById(ID id);
    
    /**
     * 批量保存实体
     *
     * @param dtos DTO对象列表
     * @return 保存后的DTO列表
     */
    Flux<D> saveAll(Iterable<D> dtos);
    
    /**
     * 批量保存实体(响应式)
     *
     * @param dtos DTO对象流
     * @return 保存后的DTO流
     */
    Flux<D> saveBatch(Flux<D> dtos);
    
    /**
     * 批量更新实体
     *
     * @param dtos DTO对象流
     * @return 更新后的DTO流
     */
    Flux<D> updateBatch(Flux<D> dtos);
    
    /**
     * 批量删除实体
     *
     * @param ids 实体ID列表
     * @return 删除结果
     */
    Mono<Void> deleteAllById(Iterable<ID> ids);
    
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<PageResult<D>> selectPage(Query query);
    
    /**
     * 导出数据
     *
     * @param query 查询条件
     * @return 导出的字节数据
     */
    Mono<byte[]> export(Query query);
    
    /**
     * 导入数据
     *
     * @param data 导入的字节数据
     * @return 导入结果
     */
    Mono<Void> importData(byte[] data);
    
    /**
     * 获取Repository
     *
     * @return Repository对象
     */
    Repository<?, ID> getRepository();
}