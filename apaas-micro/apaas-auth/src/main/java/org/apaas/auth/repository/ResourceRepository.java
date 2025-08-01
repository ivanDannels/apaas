package org.apaas.authorization.repository;

import org.apaas.authorization.entity.Resource;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ResourceRepository extends ReactiveCrudRepository<Resource, Long> {
    /**
     * 根据角色ID查询资源列表
     */
    @Query("SELECT r.* FROM sys_resource r INNER JOIN sys_role_resource rr ON r.id = rr.resource_id WHERE rr.role_id = :roleId")
    Flux<Resource> findByRoleId(Long roleId);

    /**
     * 根据用户ID查询资源列表
     */
    @Query("SELECT r.* FROM sys_resource r INNER JOIN sys_role_resource rr ON r.id = rr.resource_id INNER JOIN sys_user_role ur ON rr.role_id = ur.role_id WHERE ur.user_id = :userId")
    Flux<Resource> findByUserId(Long userId);
}