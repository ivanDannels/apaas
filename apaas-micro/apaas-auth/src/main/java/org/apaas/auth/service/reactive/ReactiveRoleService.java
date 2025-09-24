package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Role;
import org.apaas.domain.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色服务接口
 */
public interface ReactiveRoleService extends BaseService<Role, Long> {

    /**
     * 根据角色名获取角色
     *
     * @param roleName 角色名
     * @return 角色信息
     */
    Mono<Role> getRoleByRoleName(String roleName);

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return 添加结果
     */
    Mono<Role> addRole(Role role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return 更新结果
     */
    Mono<Role> updateRole(Role role);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 删除结果
     */
    Mono<Void> deleteRole(Long id);

    /**
     * 获取角色权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    Flux<String> getRolePermissions(Long roleId);

    /**
     * 为角色分配权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 分配结果
     */
    Mono<Void> assignPermissionsToRole(Long roleId, Flux<Long> permissionIds);

    Flux<Role> getUserRoles(Long userId);

    Mono<Boolean> assignRoles(Long userId, Long[] roleIds);

    Flux<Role> getAllRoles();

    Mono<Boolean> changeStatus(Long id, Integer status);
}