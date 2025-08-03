package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.Role;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式角色仓库接口
 * @author ivan
 */
public interface ReactiveRoleRepository extends ReactiveBaseRepository<Role, Long> {

    /**
     * 根据角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色信息
     */
    Mono<Role> findByCode(String code);

    /**
     * 根据状态查询角色
     *
     * @param status 状态
     * @return 角色列表
     */
    Flux<Role> findByStatus(Integer status);

    Mono<Role> findByRoleName(String roleName);

    Mono<Role> findByPage(Pageable pageable);
}