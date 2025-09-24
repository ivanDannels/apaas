package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.UserRole;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户角色关联仓库接口
 * @author ivan
 */
public interface ReactiveUserRoleRepository extends R2dbcRepository {

    /**
     * 根据用户ID查询用户角色关联
     *
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    Flux<UserRole> findByUserId(Long userId);

    /**
     * 根据角色ID查询用户角色关联
     *
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    Flux<UserRole> findByRoleId(Long roleId);

    /**
     * 根据用户ID删除用户角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    Mono<Void> deleteByUserId(Long userId);

    /**
     * 根据角色ID删除用户角色关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    Mono<Void> deleteByRoleId(Long roleId);
}