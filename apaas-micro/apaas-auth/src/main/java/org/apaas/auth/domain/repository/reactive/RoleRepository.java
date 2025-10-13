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

import org.apaas.auth.domain.entity.Role;
import org.apaas.domain.repository.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色仓库接口
 * @author ivan
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    
    /**
     * 根据角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色信息
     */
    Mono<Role> findByCode(String code);
    
    /**
     * 根据状态查询角色
     *
     * @param status 状态
     * @return 角色列表
     */
    Flux<Role> findByStatus(Integer status);
    
    Mono<Role> findByRoleName(String roleName);
    
    Mono<Role> findByPage(Pageable pageable);
}
