package org.apaas.auth.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.Role;
import org.apaas.auth.service.reactive.ReactiveRoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/roles")
@Tag(name = "响应式角色管理", description = "响应式角色相关操作")
@RequiredArgsConstructor
public class ReactiveRoleController {

    private final ReactiveRoleService roleService;

    /**
     * 分页查询角色
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询角色", description = "根据条件分页查询角色列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "name", description = "角色名称，模糊查询"),
        @Parameter(name = "code", description = "角色编码，模糊查询"),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用")
    })
    public Mono<PageResult<Role>> selectPage(RoleDTO query) {
        return roleService.selectPage(query);
    }

    /**
     * 获取角色详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取角色详情", description = "根据ID获取角色详情")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Role> getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    /**
     * 创建角色
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建角色", description = "创建新角色")
    public Mono<Boolean> create(@RequestBody Role role) {
        return roleService.create(role);
    }

    /**
     * 更新角色
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新角色", description = "更新角色信息")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.update(role);
    }

    /**
     * 删除角色
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除角色", description = "删除角色")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Boolean> delete(@PathVariable Long id) {
        return roleService.delete(id);
    }

    /**
     * 更新角色状态
     */
    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新角色状态", description = "启用或禁用角色")
    @Parameters({
        @Parameter(name = "id", description = "角色ID", required = true),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return roleService.changeStatus(id, status);
    }

    /**
     * 获取所有角色
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取所有角色", description = "获取所有可用角色")
    public Flux<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * 获取用户角色
     */
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户角色", description = "获取指定用户的角色列表")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Flux<Role> getUserRoles(@PathVariable Long userId) {
        return roleService.getUserRoles(userId);
    }

    /**
     * 分配用户角色
     */
    @PostMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分配用户角色", description = "为用户分配角色")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public Mono<Boolean> assignRoles(@PathVariable Long userId, @RequestBody Long[] roleIds) {
        return roleService.assignRoles(userId, roleIds);
    }
}