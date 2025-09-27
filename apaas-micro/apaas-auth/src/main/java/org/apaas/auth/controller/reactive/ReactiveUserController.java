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
package org.apaas.auth.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.User;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.apaas.core.query.Condition;
import org.apaas.core.query.Operator;
import org.apaas.core.query.PageRequest;
import org.apaas.core.query.Query;
import org.apaas.domain.rest.ReactiveBaseController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式用户控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/users")
@Tag(name = "响应式用户管理", description = "响应式用户相关操作")
public class ReactiveUserController extends ReactiveBaseController<User, Long, ReactiveUserService> {
    
    public ReactiveUserController(ReactiveUserService service) {
        super(service);
    }
    
    /**
     * 用户登录
     */
    @Log(title = "用户登录", businessType = BusinessType.LOGIN, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "用户登录", description = "用户登录获取token")
    @Parameters({@Parameter(name = "username", description = "用户名", required = true), @Parameter(name = "password", description = "密码", required = true)})
    public Mono<User> login(@RequestParam String username, @RequestParam String password) {
        return service.login(username, password);
    }
    
    /**
     * 用户注册
     */
    @Log(title = "用户注册", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "用户注册", description = "创建新用户")
    public Mono<Boolean> register(@RequestBody User user) {
        return service.addUser(user).thenReturn(true);
    }
    
    /**
     * 分页查询用户
     */
    @Log(title = "分页查询用户", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询用户", description = "根据条件分页查询用户列表")
    @Parameters({@Parameter(name = "pageNum", description = "页码", required = true), @Parameter(name = "pageSize", description = "每页条数", required = true), @Parameter(name = "username", description = "用户名，模糊查询"), @Parameter(name = "nickname", description = "昵称，模糊查询"), @Parameter(name = "phone", description = "手机号码，模糊查询"), @Parameter(name = "status", description = "状态：0-启用，1-禁用"), @Parameter(name = "deptId", description = "部门ID")})
    public Mono<PageResult<User>> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String username, @RequestParam(required = false) String nickname, @RequestParam(required = false) String phone, @RequestParam(required = false) Integer status, @RequestParam(required = false) Long deptId) {
        
        Query query = Query.builder().addPageRequest(new PageRequest(pageNum, pageSize));
        
        // 添加查询条件
        if (username != null && !username.isEmpty()) {
            query.addCondition(Condition.of("username", Operator.Comparison.LIKE.name(), username));
        }
        if (nickname != null && !nickname.isEmpty()) {
            query.addCondition(Condition.of("nickname", Operator.Comparison.LIKE.name(), nickname));
        }
        if (phone != null && !phone.isEmpty()) {
            query.addCondition(Condition.of("phone", Operator.Comparison.LIKE.name(), phone));
        }
        if (status != null) {
            query.addCondition(Condition.eq("status", status));
        }
        if (deptId != null) {
            query.addCondition(Condition.eq("deptId", deptId));
        }
        
        return service.selectPage(query);
    }
    
    /**
     * 获取当前用户信息
     */
    @Log(title = "获取当前用户信息", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息")
    public Mono<User> getCurrentUser() {
        return service.getCurrentUser();
    }
    
    /**
     * 修改密码
     */
    @Log(title = "修改密码", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改密码", description = "修改当前用户密码")
    @Parameters({@Parameter(name = "oldPassword", description = "旧密码", required = true), @Parameter(name = "newPassword", description = "新密码", required = true)})
    public Mono<Boolean> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return service.updatePassword(oldPassword, newPassword);
    }
    
    /**
     * 获取用户权限
     */
    @Log(title = "获取用户权限", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/{userId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户权限", description = "根据用户ID获取权限列表")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Mono<List<String>> getUserPermissions(@PathVariable Long userId) {
        return service.getUserPermissions(userId).collectList();
    }
}