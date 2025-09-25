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
package org.apaas.auth.service.reactive.impl;

import org.apaas.auth.entity.Organization;
import org.apaas.auth.repository.reactive.ReactiveOrganizationRepository;
import org.apaas.auth.service.reactive.ReactiveOrganizationService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
@Service
public class ReactiveOrganizationServiceImpl extends BaseServiceImpl<Organization, Long, ReactiveOrganizationRepository> implements ReactiveOrganizationService {
    
    public ReactiveOrganizationServiceImpl(ReactiveOrganizationRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<Organization> getOrganizationTree(Long parentId) {
        // 实现获取组织机构树的逻辑
        return repository.findById(parentId).flux();
    }
    
    @Override
    public Flux<Organization> getOrganizationsByUserId(Long userId) {
        // 实现根据用户ID获取组织机构列表的逻辑
        return repository.findById(userId).flux();
    }
    
    @Override
    public Mono<Boolean> setMainOrganization(Long userId, Long orgId) {
        // 实现设置用户主组织机构的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如更新用户组织机构关联表中的主组织机构标识
            return true;
        });
    }
}