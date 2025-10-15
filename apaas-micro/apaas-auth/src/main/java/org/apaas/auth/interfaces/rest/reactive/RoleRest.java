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
package org.apaas.auth.interfaces.rest.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.auth.application.dto.RoleDTO;
import org.apaas.auth.domain.entity.Role;
import org.apaas.auth.domain.service.reactive.ReactiveRoleService;
import org.apaas.core.query.Query;
import org.apaas.interfaces.rest.BaseRest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/roles")
@Tag(name = "响应式角色管理", description = "响应式角色相关操作")
public class RoleRest extends BaseRest<RoleDTO, Long, ReactiveRoleService> {
    
    public RoleRest(ReactiveRoleService service) {
        super(service);
    }
    
    /**
     * 获取用户角色
     */
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户角色", description = "获取指定用户的角色列表")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Flux<Role> getUserRoles(@PathVariable Long userId) {
        return service.getUserRoles(userId);
    }
    
    /**
     * 分配用户角色
     */
    @PostMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分配用户角色", description = "为用户分配角色")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Mono<Boolean> assignRoles(@PathVariable Long userId, @RequestBody Long[] roleIds) {
        return service.assignRoles(userId, roleIds);
    }
    
    /**
     * 批量新增角色
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增角色", description = "批量新增角色")
    public Flux<Role> addBatchRoles(@RequestBody Flux<Role> roles) {
        return service.saveBatch(roles);
    }
    
    /**
     * 批量更新角色
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新角色", description = "批量更新角色")
    public Flux<Role> updateBatchRoles(@RequestBody Flux<Role> roles) {
        return service.updateBatch(roles);
    }
    
    /**
     * 导出角色
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出角色", description = "导出角色")
    public Mono<byte[]> exportRoles(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入角色
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入角色", description = "导入角色")
    public Mono<Void> importRoles(@RequestBody byte[] data) {
        return service.importData(data);
    }
}