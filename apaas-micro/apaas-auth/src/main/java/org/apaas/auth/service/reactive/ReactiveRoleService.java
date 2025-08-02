package org.apaas.auth.service.reactive;

import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.auth.domain.PageResult;
import org.apaas.auth.entity.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色服务接口
 */
public interface ReactiveRoleService {

    /**
     * 分页查询角色
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<PageResult<Role>> selectPage(RoleDTO query);

    /**
     * 根据ID获取角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    Mono<Role> getById(Long id);

    /**
     * 创建角色
     *
     * @param role 角色信息
     * @return 结果
     */
    Mono<Boolean> create(Role role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return 结果
     */
    Mono<Boolean> update(Role role);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 结果
     */
    Mono<Boolean> delete(Long id);

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 状态
     * @return 结果
     */
    Mono<Boolean> changeStatus(Long id, Integer status);

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    Flux<Role> getAllRoles();

    /**
     * 获取用户角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    Flux<Role> getUserRoles(Long userId);

    /**
     * 分配用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID数组
     * @return 结果
     */
    Mono<Boolean> assignRoles(Long userId, Long[] roleIds);
}