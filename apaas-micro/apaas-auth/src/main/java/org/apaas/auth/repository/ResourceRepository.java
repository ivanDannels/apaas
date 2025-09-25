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
package org.apaas.auth.repository;

import org.apaas.auth.entity.Resource;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
@Repository
public interface ResourceRepository extends ReactiveBaseRepository<Resource, Long> {
    
    /**
     * 根据角色ID查询资源列表
     */
    @Query("SELECT r.* FROM sys_resource r INNER JOIN sys_role_resource rr ON r.id = rr.resource_id WHERE rr.role_id = :roleId")
    Flux<Resource> findByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询资源列表
     */
    @Query("SELECT r.* FROM sys_resource r INNER JOIN sys_role_resource rr ON r.id = rr.resource_id INNER JOIN sys_user_role ur ON rr.role_id = ur.role_id WHERE ur.user_id = :userId")
    Flux<Resource> findByUserId(Long userId);
    
    Flux<Resource> findByName(String name);
    
    Flux<Resource> findByType(Integer type);
}