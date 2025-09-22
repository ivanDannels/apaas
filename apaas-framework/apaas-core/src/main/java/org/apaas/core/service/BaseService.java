package org.apaas.core.service;

import org.apaas.core.domain.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;

/**
 * @author ivan
 */
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
     * 批量保存实体
     *
     * @param entities 实体对象列表
     * @return 保存后的实体列表
     */
    Flux<T> saveAll(Iterable<T> entities);
    
    /**
     * 批量保存实体(响应式)
     *
     * @param entities 实体对象流
     * @return 保存后的实体流
     */
    Flux<T> saveBatch(Flux<T> entities);
    
    /**
     * 批量更新实体
     *
     * @param entities 实体对象流
     * @return 更新后的实体流
     */
    Flux<T> updateBatch(Flux<T> entities);
    
    /**
     * 批量删除实体
     *
     * @param ids 实体ID列表
     * @return 删除结果
     */
    Mono<Void> deleteAllById(Iterable<ID> ids);
    
    /**
     * 批量删除实体(响应式)
     *
     * @param ids 实体ID流
     * @return 删除结果
     */
    Mono<Void> deleteByIds(Flux<ID> ids);
    
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Mono<PageResult<T>> selectPage(Query query);
    
    /**
     * 导出数据
     *
     * @param query 查询条件
     * @return 导出的字节数据
     */
    Mono<byte[]> export(Query query);
    
    /**
     * 导入数据
     *
     * @param data 导入的字节数据
     * @return 导入结果
     */
    Mono<Void> importData(byte[] data);
    
    /**
     * 获取Repository
     *
     * @return Repository对象
     */
    Repository<T, ID> getRepository();
}