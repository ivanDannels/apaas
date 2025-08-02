package org.apaas.form.engine.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.form.engine.entity.FormLayout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单布局服务接口
 */
public interface ReactiveFormLayoutService extends BaseService<FormLayout, Long> {
    /**
     * 分页查询表单布局
     *
     * @param formId 表单ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    Mono<org.apaas.core.domain.Page<FormLayout>> selectPage(Long formId, Integer pageNum, Integer pageSize);

    /**
     * 根据表单ID查询布局列表
     *
     * @param formId 表单ID
     * @return 布局列表
     */
    Flux<FormLayout> selectByFormId(Long formId);

    /**
     * 根据表单ID和布局类型查询布局列表
     *
     * @param formId 表单ID
     * @param type 布局类型
     * @return 布局列表
     */
    Flux<FormLayout> selectByFormIdAndType(Long formId, Integer type);

    /**
     * 根据表单ID和终端类型查询布局列表
     *
     * @param formId 表单ID
     * @param terminal 终端类型
     * @return 布局列表
     */
    Flux<FormLayout> selectByFormIdAndTerminal(Long formId, Integer terminal);

    /**
     * 根据表单ID查询默认布局
     *
     * @param formId 表单ID
     * @return 默认布局
     */
    Mono<FormLayout> selectDefaultByFormId(Long formId);

    /**
     * 创建表单布局
     *
     * @param formLayout 表单布局
     * @return 创建的表单布局
     */
    Mono<FormLayout> create(FormLayout formLayout);

    /**
     * 更新表单布局
     *
     * @param formLayout 表单布局
     * @return 更新的表单布局
     */
    Mono<FormLayout> update(FormLayout formLayout);

    /**
     * 删除表单布局
     *
     * @param id 布局ID
     * @return 操作结果
     */
    Mono<Void> delete(Long id);

    /**
     * 设置默认布局
     *
     * @param id 布局ID
     * @return 操作结果
     */
    Mono<Boolean> setDefault(Long id);
}