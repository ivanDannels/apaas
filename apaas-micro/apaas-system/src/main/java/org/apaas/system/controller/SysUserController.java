package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.entity.SysUser;
import org.apaas.system.service.reactive.ReactiveSysUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式用户管理控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/users")
@Tag(name = "响应式用户管理", description = "响应式用户CRUD操作")
@RequiredArgsConstructor
public class SysUserController {

    private final ReactiveSysUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询用户列表", description = "根据条件分页查询用户信息")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "username", description = "用户名，模糊查询"),
        @Parameter(name = "status", description = "用户状态: 0-禁用, 1-启用")
    })
    public Flux<SysUser> list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        UserQueryDTO query = new UserQueryDTO();
        query.setUsername(username);
        query.setStatus(status);
        return userService.getUserPage(pageable, query);
    }

    /**
     * 获取用户详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户详情", description = "根据ID查询用户信息")
    @Parameter(name = "id", description = "用户ID", required = true)
    public Mono<SysUser> getById(@PathVariable Long id) {
        return userService.getUserByUsername(id.toString())
                .switchIfEmpty(Mono.error(new RuntimeException("用户不存在")));
    }

    /**
     * 创建用户
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建用户", description = "新增用户信息")
    public Mono<Boolean> save(@RequestBody SysUser user) {
        return userService.addUser(user);
    }

    /**
     * 更新用户
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新用户", description = "修改用户信息")
    @Parameter(name = "id", description = "用户ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @Parameter(name = "id", description = "用户ID", required = true)
    public Mono<Boolean> remove(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 重置用户密码
     */
    @PostMapping(value = "/{id}/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "重置用户密码", description = "重置指定用户的密码")
    @Parameters({
        @Parameter(name = "id", description = "用户ID", required = true),
        @Parameter(name = "password", description = "新密码", required = true)
    })
    public Mono<Boolean> resetPassword(@PathVariable Long id, @RequestParam String password) {
        return userService.resetPassword(id, password);
    }

    /**
     * 修改用户状态
     */
    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改用户状态", description = "启用或禁用指定用户")
    @Parameters({
        @Parameter(name = "id", description = "用户ID", required = true),
        @Parameter(name = "status", description = "用户状态: 0-禁用, 1-启用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return userService.changeStatus(id, status);
    }
}