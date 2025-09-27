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
package org.apaas.domain.service.domain;

import org.apaas.domain.entity.AggregateRoot;
import org.apaas.domain.exception.BusinessException;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.domain.specification.Specification;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 抽象领域服务基类
 * 处理跨实体的复杂业务逻辑，协调多个聚合根的操作
 *
 * @author ivan
 * @param <T> 聚合根类型
 * @param <ID> 聚合根标识类型
 * @param <R> 仓库类型
 */
public abstract class AbstractDomainService<T extends AggregateRoot<ID>, ID extends Serializable, R extends ReactiveBaseRepository<T, ID>> 
        implements DomainService<T> {
    
    protected final R repository;
    
    protected AbstractDomainService(R repository) {
        this.repository = repository;
    }
    
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
        return checkSpecification(aggregate, specification)
                .flatMap(satisfied -> {
                    if (!satisfied) {
                        return Mono.error(new BusinessException("业务规则验证失败"));
                    }
                    return repository.save(aggregate);
                });
    }
    
    /**
     * 执行领域逻辑
     *
     * @param domainObject 领域对象
     * @return 处理结果
     */
    @Override
    public Mono<T> execute(T domainObject) {
        // 默认实现，子类可以重写
        return repository.save(domainObject);
    }
}