package org.apaas.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.auth.entity.Role;
import org.apaas.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 角色控制器
 */
@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "角色管理", description = "角色相关操作")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询角色
     */
    @GetMapping
    @Operation(summary = "分页查询角色", description = "根据条件分页查询角色列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "name", description = "角色名称，模糊查询"),
        @Parameter(name = "code", description = "角色编码，模糊查询"),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用")
    })
    public ResponseEntity<IPage<Role>> selectPage(RoleDTO query) {
        IPage<Role> page = roleService.selectPage(query);
        return ResponseEntity.ok(page);
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情", description = "根据ID获取角色详情")
    @Parameter(name = "id", description = "角色ID", required = true)
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return ResponseEntity.ok(role);
    }

    /**
     * 创建角色
     */
    @PostMapping
    @Operation(summary = "创建角色", description = "创建新角色")
    public ResponseEntity<Boolean> create(@RequestBody Role role) {
        boolean result = roleService.create(role);
        return ResponseEntity.ok(result);
    }

    /**
     * 更新角色
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新角色", description = "更新角色信息")
    @Parameter(name = "id", description = "角色ID", required = true)
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        boolean result = roleService.update(role);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色", description = "删除角色")
    @Parameter(name = "id", description = "角色ID", required = true)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = roleService.delete(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 更新角色状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新角色状态", description = "启用或禁用角色")
    @Parameters({
        @Parameter(name = "id", description = "角色ID", required = true),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用", required = true)
    })
    public ResponseEntity<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean result = roleService.changeStatus(id, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 关联角色资源
     */
    @PostMapping("/{id}/resources")
    @Operation(summary = "关联角色资源", description = "为角色分配资源")
    @Parameters({
        @Parameter(name = "id", description = "角色ID", required = true),
        @Parameter(name = "resourceIds", description = "资源ID列表", required = true)
    })
    public ResponseEntity<Boolean> associateRoleResource(@PathVariable Long id, @RequestBody List<Long> resourceIds) {
        boolean result = roleService.associateRoleResource(id, resourceIds);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取角色权限列表
     */
    @GetMapping("/{id}/permissions")
    @Operation(summary = "获取角色权限列表", description = "获取角色拥有的权限列表")
    @Parameter(name = "id", description = "角色ID", required = true)
    public ResponseEntity<List<String>> getPermissionsByRoleId(@PathVariable Long id) {
        List<String> permissions = roleService.getPermissionsByRoleId(id);
        return ResponseEntity.ok(permissions);
    }

    /**
     * 获取角色数据范围
     */
    @GetMapping("/{id}/data-scope")
    @Operation(summary = "获取角色数据范围", description = "获取角色的数据范围设置")
    @Parameter(name = "id", description = "角色ID", required = true)
    public ResponseEntity<String> getDataScopeByRoleId(@PathVariable Long id) {
        String dataScope = roleService.getDataScopeByRoleId(id);
        return ResponseEntity.ok(dataScope);
    }

    /**
     * 更新角色数据范围
     */
    @PutMapping("/{id}/data-scope")
    @Operation(summary = "更新角色数据范围", description = "更新角色的数据范围设置")
    @Parameters({
        @Parameter(name = "id", description = "角色ID", required = true),
        @Parameter(name = "dataScopeType", description = "数据范围类型", required = true),
        @Parameter(name = "customDeptIds", description = "自定义部门ID列表")
    })
    public ResponseEntity<Boolean> updateDataScope(
            @PathVariable Long id,
            @RequestParam String dataScopeType,
            @RequestParam(required = false) List<Long> customDeptIds) {
        boolean result = roleService.updateDataScope(id, dataScopeType, customDeptIds);
        return ResponseEntity.ok(result);
    }
}