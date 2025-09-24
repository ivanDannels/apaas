package org.apaas.auth.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.domain.rest.ReactiveBaseController;
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
public class ReactiveRoleController extends ReactiveBaseController<Role, Long, ReactiveRoleService> {

    public ReactiveRoleController(ReactiveRoleService service) {
        super(service);
    }

    /**
     * 获取角色详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取角色详情", description = "根据ID获取角色详情")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Role> getById(@PathVariable Long id) {
        return super.get(id);
    }

    /**
     * 创建角色
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建角色", description = "创建新角色")
    public Mono<Role> create(@RequestBody Role role) {
        return super.add(role);
    }

    /**
     * 更新角色
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新角色", description = "更新角色信息")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Role> update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return super.update(role);
    }

    /**
     * 删除角色
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除角色", description = "删除角色")
    @Parameter(name = "id", description = "角色ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return super.delete(id);
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
        return service.changeStatus(id, status);
    }

    /**
     * 获取所有角色
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取所有角色", description = "获取所有可用角色")
    public Flux<Role> getAllRoles() {
        return service.getAllRoles();
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
     * 批量删除角色
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除角色", description = "批量删除角色")
    public Mono<Void> deleteBatch(@RequestBody Long[] ids) {
        return service.deleteByIds(Flux.fromArray(ids)).then(Mono.empty());
    }
    
    /**
     * 批量新增角色
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增角色", description = "批量新增角色")
    public Flux<Role> addBatch(@RequestBody Flux<Role> roles) {
        return service.saveBatch(roles);
    }
    
    /**
     * 批量更新角色
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新角色", description = "批量更新角色")
    public Flux<Role> updateBatch(@RequestBody Flux<Role> roles) {
        return service.updateBatch(roles);
    }
    
    /**
     * 导出角色
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出角色", description = "导出角色")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入角色
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入角色", description = "导入角色")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
    }
}