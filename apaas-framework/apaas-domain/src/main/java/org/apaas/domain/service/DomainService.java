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

import io.lettuce.core.Value;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.entity.BaseEntity;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

/**
 * 领域服务接口
 * @author ivan
 * @param <T> 领域对象类型
 * @param <ID> 领域对象标识类型
 */
public interface DomainService<T extends BaseEntity<ID>, ID extends Serializable> {
    
    /**
     * 保存
     * @param entity 领域对象
     * @return 处理结果
     */
    Mono<T> save(T entity);

    Mono<T> findById(ID id);

    Flux<T> findAll();

    Flux<T> findAll(Query query);

    Mono<Void> deleteById(ID id);

    Flux<T> saveAll(List<T> ts);

    Mono<Void> deleteAllById(Iterable<ID> ids);

    Mono<PageResult<T>> selectPage(Query query);
}