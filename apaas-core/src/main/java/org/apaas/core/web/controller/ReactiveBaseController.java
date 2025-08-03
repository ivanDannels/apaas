package org.apaas.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.apaas.core.domain.BaseEntity;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 响应式控制器基类
 * @author ivan
 * @param <T> 实体类型
 * @param <ID> 主键类型
 * @param <S> 服务类型
 */
@RequiredArgsConstructor
public abstract class ReactiveBaseController<T extends BaseEntity, ID extends Serializable, S extends BaseService<T, ID>> {

    protected final S service;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Mono<PageResult<T>> page(@RequestBody Query query) {
        return service.selectPage(query);
    }

    /**
     * 根据ID查询详情
     *
     * @param id 实体ID
     * @return 实体对象
     */
    @GetMapping("/{id}")
    public Mono<T> get(@PathVariable ID id) {
        return service.findById(id);
    }

    /**
     * 新增实体
     *
     * @param entity 实体对象
     * @return 保存结果
     */
    @PostMapping
    public Mono<T> add(@RequestBody T entity) {
        return service.save(entity);
    }

    /**
     * 批量新增实体
     *
     * @param entities 实体对象列表
     * @return 保存结果
     */
    @PostMapping("/batch")
    public Flux<T> addBatch(@RequestBody Flux<T> entities) {
        return service.saveBatch(entities);
    }

    /**
     * 更新实体
     *
     * @param entity 实体对象
     * @return 更新结果
     */
    @PutMapping
    public Mono<T> update(@RequestBody T entity) {
        return service.save(entity);
    }

    /**
     * 批量更新实体
     *
     * @param entities 实体对象列表
     * @return 更新结果
     */
    @PutMapping("/batch")
    public Flux<T> updateBatch(@RequestBody Flux<T> entities) {
        return service.updateBatch(entities);
    }

    /**
     * 删除实体
     *
     * @param id 实体ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable ID id) {
        return service.deleteById(id);
    }

    /**
     * 批量删除实体
     *
     * @param ids 实体ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch/{ids}")
    public Mono<Void> deleteBatch(@PathVariable ID[] ids) {
        return service.deleteByIds(Flux.fromArray(ids));
    }

    /**
     * 导出实体
     *
     * @param query 查询条件
     * @return 导出结果
     */
    @PostMapping("/export")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }

    /**
     * 导入实体
     *
     * @param data 导入数据
     * @return 导入结果
     */
    @PostMapping("/import")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
    }
}