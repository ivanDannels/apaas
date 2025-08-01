package org.apaas.core.repository;

import org.apaas.core.domain.BaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 响应式基础仓库接口
 * @param <T> 实体类型
 * @param <ID> 主键类型
 */
@NoRepositoryBean
public interface ReactiveBaseRepository<T extends BaseEntity, ID extends Serializable> extends R2dbcRepository<T, ID> {
    
    /**
     * 根据租户ID和主键查找实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 实体对象
     */
    Mono<T> findByIdAndTenantId(ID id, Long tenantId);
    
    /**
     * 根据租户ID查找所有未删除的实体
     * @param tenantId 租户ID
     * @return 实体对象流
     */
    Flux<T> findAllByTenantIdAndDeletedFalse(Long tenantId);
    
    /**
     * 根据租户ID和主键删除实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 删除结果
     */
    Mono<Void> deleteByIdAndTenantId(ID id, Long tenantId);
}