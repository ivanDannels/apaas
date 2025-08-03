package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.UserTenant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ReactiveUserTenantRepository extends R2dbcRepository {

    /**
     * 根据用户ID查找用户租户关联记录
     *
     * @param userId 用户ID
     * @return 用户租户关联记录流
     */
    Flux<UserTenant> findByUserId(Long userId);

    /**
     * 根据租户ID查找用户租户关联记录
     *
     * @param tenantId 租户ID
     * @return 用户租户关联记录流
     */
    Flux<UserTenant> findByTenantId(Long tenantId);

    /**
     * 根据用户ID和租户ID删除用户租户关联记录
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     */
    void deleteByUserIdAndTenantId(Long userId, Long tenantId);
}