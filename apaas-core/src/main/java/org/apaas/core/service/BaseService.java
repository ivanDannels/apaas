package org.apaas.core.service;

import org.apaas.core.domain.BaseEntity;
import org.apaas.core.repository.BaseEntityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {
    
    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 保存后的实体
     */
    Mono<T> save(T entity);
    
    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return 实体对象
     */
    Mono<T> findById(ID id);
    
    /**
     * 查找所有实体
     *
     * @return 实体对象列表
     */
    Flux<T> findAll();
    
    /**
     * 根据ID删除实体
     *
     * @param id 实体ID
     */
    Mono<Void> deleteById(ID id);
    
    /**
     * 获取Repository
     *
     * @return Repository对象
     */
    BaseEntityRepository<T, ID> getRepository();
}