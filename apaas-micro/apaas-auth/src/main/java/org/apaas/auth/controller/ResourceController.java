package org.apaas.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.authorization.domain.dto.ResourceDTO;
import org.apaas.authorization.entity.Resource;
import org.apaas.authorization.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 资源控制器
 */
@RestController
@RequestMapping("/api/v1/resources")
@Tag(name = "资源管理", description = "资源管理相关接口")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 分页查询资源
     *
     * @param resourceDTO 资源查询DTO
     * @return 分页资源列表
     */
    @Operation(summary = "分页查询资源", description = "根据条件分页查询资源列表")
    @GetMapping(value = "/page", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAuthority('sys:resource:view')")
    public Mono<org.springframework.data.domain.Page<Resource>> page(ResourceDTO resourceDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sort"));
        return resourceService.selectPage(resourceDTO, pageable);
    }

    /**
     * 获取资源详情
     *
     * @param id 资源ID
     * @return 资源详情
     */
    @Operation(summary = "获取资源详情", description = "根据ID获取资源详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:resource:view')")
    public Mono<Resource> getById(@PathVariable Long id) {
        // 这里需要实现根据ID查询资源的逻辑
        return Mono.empty();
    }

    /**
     * 新增资源
     *
     * @param resource 资源实体
     * @return 新增结果
     */
    @Operation(summary = "新增资源", description = "添加新的资源")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:resource:add')")
    public Mono<Resource> save(@RequestBody Resource resource) {
        return resourceService.create(resource);
    }

    /**
     * 修改资源
     *
     * @param resource 资源实体
     * @return 修改结果
     */
    @Operation(summary = "修改资源", description = "修改现有资源")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    public Mono<Resource> update(@RequestBody Resource resource) {
        return resourceService.update(resource);
    }

    /**
     * 删除资源
     *
     * @param id 资源ID
     * @return 删除结果
     */
    @Operation(summary = "删除资源", description = "删除指定ID的资源")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:resource:delete')")
    public Mono<Boolean> delete(@PathVariable Long id) {
        return resourceService.delete(id);
    }

    /**
     * 变更资源状态
     *
     * @param id 资源ID
     * @param status 状态：0-启用，1-禁用
     * @return 变更结果
     */
    @Operation(summary = "变更资源状态", description = "启用或禁用指定资源")
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return resourceService.changeStatus(id, status);
    }

    /**
     * 查询资源树形结构
     *
     * @return 资源树形结构
     */
    @Operation(summary = "查询资源树形结构", description = "获取所有资源的树形结构")
    @GetMapping(value = "/tree", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAuthority('sys:resource:view')")
    public Flux<Resource> selectTree() {
        return resourceService.selectTree();
    }

    /**
     * 根据角色ID查询资源树形结构
     *
     * @param roleId 角色ID
     * @return 资源树形结构
     */
    @Operation(summary = "根据角色ID查询资源树形结构", description = "获取指定角色拥有的资源树形结构")
    @GetMapping(value = "/tree/{roleId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAuthority('sys:resource:view')")
    public Flux<Resource> selectTreeByRoleId(@PathVariable Long roleId) {
        return resourceService.selectTreeByRoleId(roleId);
    }
}