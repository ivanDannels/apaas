package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormDefinition;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单定义响应式仓库接口
 */
@Repository
public interface FormDefinitionRepository extends ReactiveBaseRepository<FormDefinition, Long> {
    /**
     * 根据表单编码查询表单定义列表
     *
     * @param code 表单编码
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByCode(String code);
    
    /**
     * 根据表单编码和状态查询表单定义列表
     *
     * @param code 表单编码
     * @param status 表单状态
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByCodeAndStatus(String code, Integer status);
    
    /**
     * 根据表单类型查询表单定义列表
     *
     * @param type 表单类型
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByType(Integer type);
    
    /**
     * 根据表单状态查询表单定义列表
     *
     * @param status 表单状态
     * @return 表单定义列表
     */
    Flux<FormDefinition> findByStatus(Integer status);
    
    /**
     * 根据表单编码更新默认版本状态
     *
     * @param code 表单编码
     * @param isDefault 是否默认版本
     * @return 更新结果
     */
    Mono<Void> updateIsDefaultByCode(String code, Boolean isDefault);
}