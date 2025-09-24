package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormField;
import reactor.core.publisher.Flux;

/**
 * 响应式表单字段仓库接口
 */
public interface FormFieldRepository extends ReactiveBaseRepository<FormField, Long> {
    
    /**
     * 根据表单ID查询字段列表
     *
     * @param formId 表单ID
     * @return 字段列表
     */
    Flux<FormField> findByFormId(Long formId);
    
    /**
     * 根据表单ID和字段类型查询字段列表
     *
     * @param formId 表单ID
     * @param type 字段类型
     * @return 字段列表
     */
    Flux<FormField> findByFormIdAndType(Long formId, Integer type);
    
    /**
     * 根据表单ID和分组名称查询字段列表
     *
     * @param formId 表单ID
     * @param groupName 分组名称
     * @return 字段列表
     */
    Flux<FormField> findByFormIdAndGroupName(Long formId, String groupName);
}