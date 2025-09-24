package org.apaas.form.engine.service.reactive;

import org.apaas.domain.service.BaseService;
import org.apaas.form.engine.entity.FormField;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段服务接口
 */
public interface ReactiveFormFieldService extends BaseService<FormField, Long> {
    /**
     * 分页查询表单字段
     *
     * @param formId 表单ID
     * @param pageable 分页参数
     * @return 分页结果
     */
    Flux<FormField> selectPage(Long formId, Pageable pageable);

    /**
     * 根据表单ID查询字段列表
     *
     * @param formId 表单ID
     * @return 字段列表
     */
    Flux<FormField> selectByFormId(Long formId);

    /**
     * 根据表单ID和字段类型查询字段列表
     *
     * @param formId 表单ID
     * @param type 字段类型
     * @return 字段列表
     */
    Flux<FormField> selectByFormIdAndType(Long formId, Integer type);

    /**
     * 根据表单ID和分组名称查询字段列表
     *
     * @param formId 表单ID
     * @param groupName 分组名称
     * @return 字段列表
     */
    Flux<FormField> selectByFormIdAndGroupName(Long formId, String groupName);

    /**
     * 创建表单字段
     *
     * @param formField 表单字段
     * @return 创建的表单字段
     */
    Mono<FormField> create(FormField formField);

    /**
     * 更新表单字段
     *
     * @param formField 表单字段
     * @return 更新后的表单字段
     */
    Mono<FormField> update(FormField formField);

    /**
     * 删除表单字段
     *
     * @param id 字段ID
     * @return 无
     */
    Mono<Void> delete(Long id);

    /**
     * 批量创建表单字段
     *
     * @param formId 表单ID
     * @param formFields 表单字段列表
     * @return 创建的表单字段列表
     */
    Flux<FormField> batchCreate(Long formId, Flux<FormField> formFields);
}