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
package org.apaas.auth.domain.service.reactive;

import org.apaas.auth.domain.entity.Organization;
import org.apaas.application.service.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
public interface ReactiveOrganizationService extends ApplicationService<Organization, Long> {
    
    /**
     * 获取组织机构树
     *
     * @param parentId 父级ID
     * @return 组织机构树
     */
    Flux<Organization> getOrganizationTree(Long parentId);
    
    /**
     * 根据用户ID获取组织机构列表
     *
     * @param userId 用户ID
     * @return 组织机构列表
     */
    Flux<Organization> getOrganizationsByUserId(Long userId);
    
    /**
     * 设置用户的主组织机构
     *
     * @param userId 用户ID
     * @param orgId  组织机构ID
     * @return 是否成功
     */
    Mono<Boolean> setMainOrganization(Long userId, Long orgId);
}
