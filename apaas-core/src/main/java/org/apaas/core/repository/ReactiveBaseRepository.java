package org.apaas.core.repository;

import org.apaas.core.domain.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.query.convert.PageConverter;
import org.springframework.data.domain.*;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 响应式基础仓库接口
 * @author ivan
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

    /**
     * 根据租户ID查找所有实体
     * @param tenantId 租户ID
     * @param pageable 分页参数
     * @return 实体对象流
     */
    Flux<T> findAllByTenantId(Long tenantId, Pageable pageable);

    Flux<T> findAllByTenantIdOrderByCreatedTimeDesc(Long tenantId, Pageable pageable);

    /**
     * 根据主键查找实体
     * @param id 主键
     * @return 实体对象
     */
    Mono<T> findByIdAndDeletedFalse(ID id);

    /**
     * 根据主键删除实体
     * @param id 主键
     * @return 删除结果
     */
    Mono<Void> deleteByIdAndDeletedFalse(ID id);

    /**
     * 根据租户ID和主键查找实体
     * @param id 主键
     * @param tenantId 租户ID
     * @return 实体对象
     */
    default Mono<T> findByIdAndTenantIdAndDeletedFalse(ID id, Long tenantId) {
        return findByIdAndTenantId(id, tenantId).filter(e -> e.getDeleted() != null && e.getDeleted() == 0);
    }

    /**
     * 根据租户ID查找实体总数
     * @param tenantId 租户ID
     * @return 实体总数
     */
    Mono<Long> countByTenantIdAndDeletedFalse(Long tenantId);

    /**
     * 根据租户ID查找所有实体
     * @param tenantId 租户ID
     * @param pageable 分页参数
     * @return 响应式实体对象流
     */
    default Flux<T> findAllByTenantIdAndDeletedFalse(Long tenantId, Pageable pageable) {
        return findAllByTenantIdOrderByCreatedTimeDesc(tenantId, pageable).filter(entity -> entity.getDeleted() != null && entity.getDeleted() == 0);
    }

    /**
     * 根据租户ID分页查找所有实体
     * @param tenantId 租户ID
     * @param query 分页查询参数
     * @return 分页结果
     */
    default Mono<PageResult<T>> selectPage(Long tenantId, Query query) {
        Pageable pageable = PageConverter.convertPageRequest(query);
        return findAllByTenantIdAndDeletedFalse(tenantId, pageable).collectList()
                .zipWith(countByTenantIdAndDeletedFalse(tenantId))
                .map(tuple -> PageResult.of(pageable.getPageNumber(), pageable.getPageSize(), tuple.getT2(), tuple.getT1()));
    }

    /**
     * 根据租户ID查找所有实体
     * @param tenantId 租户ID
     * @return 响应式实体对象流
     */
    Mono<Boolean> existsByIdAndTenantId(ID id, Long tenantId);

}