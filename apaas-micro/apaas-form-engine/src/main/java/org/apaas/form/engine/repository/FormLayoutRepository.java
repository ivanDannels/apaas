package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormLayout;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单布局响应式仓库接口
 */
@Repository
public interface FormLayoutRepository extends ReactiveBaseRepository<FormLayout, Long> {
    /**
     * 根据表单ID查询布局列表
     *
     * @param formId 表单ID
     * @return 布局列表
     */
    Flux<FormLayout> findByFormId(Long formId);

    /**
     * 根据表单ID和布局类型查询布局列表
     *
     * @param formId 表单ID
     * @param type 布局类型
     * @return 布局列表
     */
    Flux<FormLayout> findByFormIdAndType(Long formId, Integer type);

    /**
     * 根据表单ID和终端类型查询布局列表
     *
     * @param formId 表单ID
     * @param terminal 终端类型
     * @return 布局列表
     */
    Flux<FormLayout> findByFormIdAndTerminal(Long formId, Integer terminal);

    /**
     * 根据表单ID查询默认布局
     *
     * @param formId 表单ID
     * @return 默认布局
     */
    Mono<FormLayout> findByFormIdAndIsDefault(Long formId, Integer isDefault);

    Flux<FormLayout> findByFormIdAndDeletedFalse(Long formId, PageRequest sort);

    Mono<Integer> countByFormIdAndDeletedFalse(Long formId);
}