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
package org.apaas.auth.domain.repository.reactive;

import org.apaas.auth.domain.entity.UserTenant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * @author ivan
 */
public interface ReactiveUserTenantRepository extends R2dbcRepository<UserTenant, Long> {
    
    /**
     * 根据用户ID查找用户租户关联记录
     *
     * @param userId 用户ID
     * @return 用户租户关联记录列表
     */
    Flux<UserTenant> findByUserId(Long userId);
    
    /**
     * 根据租户ID查找用户租户关联记录
     *
     * @param tenantId 租户ID
     * @return 用户租户关联记录列表
     */
    Flux<UserTenant> findByTenantId(Long tenantId);
    
    /**
     * 根据用户ID和租户ID删除用户租户关联记录
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     */
    void deleteByUserIdAndTenantId(Long userId, Long tenantId);
}
