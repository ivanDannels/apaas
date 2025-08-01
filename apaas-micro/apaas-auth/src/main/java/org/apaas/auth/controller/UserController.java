package org.apaas.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.authorization.domain.dto.UserDTO;
import org.apaas.authorization.entity.User;
import org.apaas.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户相关操作")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录获取token")
    @Parameters({
        @Parameter(name = "username", description = "用户名", required = true),
        @Parameter(name = "password", description = "密码", required = true)
    })
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return token != null ? ResponseEntity.ok(token) : ResponseEntity.status(401).body("用户名或密码错误");
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "创建新用户")
    public ResponseEntity<Boolean> register(@RequestBody User user) {
        boolean result = userService.register(user);
        return ResponseEntity.ok(result);
    }

    /**
     * 分页查询用户
     */
    @GetMapping
    @Operation(summary = "分页查询用户", description = "根据条件分页查询用户列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "username", description = "用户名，模糊查询"),
        @Parameter(name = "nickname", description = "昵称，模糊查询"),
        @Parameter(name = "phone", description = "手机号码，模糊查询"),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用"),
        @Parameter(name = "deptId", description = "部门ID")
    })
    public ResponseEntity<IPage<User>> selectPage(UserDTO query) {
        IPage<User> page = userService.selectPage(query);
        return ResponseEntity.ok(page);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详情")
    @Parameter(name = "id", description = "用户ID", required = true)
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "更新用户信息")
    @Parameter(name = "id", description = "用户ID", required = true)
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean result = userService.updateById(user);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "删除用户")
    @Parameter(name = "id", description = "用户ID", required = true)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setDeleted(1);
        boolean result = userService.updateById(user);
        return ResponseEntity.ok(result);
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/reset-password")
    @Operation(summary = "重置密码", description = "重置用户密码")
    @Parameters({
        @Parameter(name = "id", description = "用户ID", required = true),
        @Parameter(name = "newPassword", description = "新密码", required = true)
    })
    public ResponseEntity<Boolean> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        boolean result = userService.resetPassword(id, newPassword);
        return ResponseEntity.ok(result);
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新用户状态", description = "启用或禁用用户")
    @Parameters({
        @Parameter(name = "id", description = "用户ID", required = true),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用", required = true)
    })
    public ResponseEntity<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean result = userService.changeStatus(id, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 关联用户角色
     */
    @PostMapping("/{id}/roles")
    @Operation(summary = "关联用户角色", description = "为用户分配角色")
    @Parameters({
        @Parameter(name = "id", description = "用户ID", required = true),
        @Parameter(name = "roleIds", description = "角色ID列表", required = true)
    })
    public ResponseEntity<Boolean> associateUserRole(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        boolean result = userService.associateUserRole(id, roleIds);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取用户角色列表
     */
    @GetMapping("/{id}/roles")
    @Operation(summary = "获取用户角色列表", description = "获取用户关联的角色列表")
    @Parameter(name = "id", description = "用户ID", required = true)
    public ResponseEntity<List<String>> getRolesByUserId(@PathVariable Long id) {
        List<String> roles = userService.getRolesByUserId(id);
        return ResponseEntity.ok(roles);
    }

    /**
     * 获取用户权限列表
     */
    @GetMapping("/{id}/permissions")
    @Operation(summary = "获取用户权限列表", description = "获取用户拥有的权限列表")
    @Parameter(name = "id", description = "用户ID", required = true)
    public ResponseEntity<List<String>> getPermissionsByUserId(@PathVariable Long id) {
        List<String> permissions = userService.getPermissionsByUserId(id);
        return ResponseEntity.ok(permissions);
    }
}