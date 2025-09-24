package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.ApplicationTenant;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ReactiveApplicationTenantRepository extends R2dbcRepository {

    /**
     * 根据应用ID查找应用租户关联记录
     *
     * @param applicationId 应用ID
     * @return 应用租户关联记录流
     */
    Flux<ApplicationTenant> findByApplicationId(Long applicationId);

    /**
     * 根据租户ID查找应用租户关联记录
     *
     * @param tenantId 租户ID
     * @return 应用租户关联记录流
     */
    Flux<ApplicationTenant> findByTenantId(Long tenantId);

    /**
     * 根据应用ID和租户ID删除应用租户关联记录
     *
     * @param applicationId 应用ID
     * @param tenantId      租户ID
     */
    void deleteByApplicationIdAndTenantId(Long applicationId, Long tenantId);
}