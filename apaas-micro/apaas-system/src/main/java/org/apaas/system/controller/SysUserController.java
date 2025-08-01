package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apaas.system.entity.SysUser;
import org.apaas.system.service.SysUserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户CRUD操作")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户列表", description = "分页查询用户信息")
    public Mono<List<SysUser>> list() {
        return Mono.just(sysUserService.list());
    }

    /**
     * 获取用户详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取用户详情", description = "根据ID查询用户信息")
    public Mono<SysUser> getById(@PathVariable Long id) {
        return Mono.just(sysUserService.getById(id));
    }

    /**
     * 创建用户
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建用户", description = "新增用户信息")
    public Mono<Boolean> save(@RequestBody SysUser user) {
        return Mono.just(sysUserService.save(user));
    }

    /**
     * 更新用户
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新用户", description = "修改用户信息")
    public Mono<Boolean> update(@RequestBody SysUser user) {
        return Mono.just(sysUserService.updateById(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    public Mono<Boolean> remove(@PathVariable Long id) {
        return Mono.just(sysUserService.removeById(id));
    }
}