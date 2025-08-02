package org.apaas.form.engine.service.reactive;

import org.apaas.core.domain.Page;
import org.apaas.core.service.BaseService;
import org.apaas.form.engine.entity.FormDynamicRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单动态规则服务接口
 */
public interface ReactiveFormDynamicRuleService extends BaseService<FormDynamicRule, Long> {
    /**
     * 分页查询表单动态规则
     *
     * @param formId 表单ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    Mono<Page<FormDynamicRule>> selectPage(Long formId, Integer pageNum, Integer pageSize);

    /**
     * 根据表单ID查询动态规则列表
     *
     * @param formId 表单ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByFormId(Long formId);

    /**
     * 根据表单ID和规则类型查询动态规则列表
     *
     * @param formId 表单ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByFormIdAndType(Long formId, Integer type);

    /**
     * 根据目标字段ID查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByTargetFieldId(Long targetFieldId);

    /**
     * 根据目标字段ID和规则类型查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> selectByTargetFieldIdAndType(Long targetFieldId, Integer type);

    /**
     * 创建表单动态规则
     *
     * @param dynamicRule 表单动态规则
     * @return 创建的表单动态规则
     */
    Mono<FormDynamicRule> create(FormDynamicRule dynamicRule);

    /**
     * 更新表单动态规则
     *
     * @param dynamicRule 表单动态规则
     * @return 更新的表单动态规则
     */
    Mono<FormDynamicRule> update(FormDynamicRule dynamicRule);

    /**
     * 删除表单动态规则
     *
     * @param id 规则ID
     * @return 操作结果
     */
    Mono<Void> delete(Long id);

    /**
     * 批量创建表单动态规则
     *
     * @param formId 表单ID
     * @param dynamicRules 表单动态规则列表
     * @return 创建的表单动态规则列表
     */
    Flux<FormDynamicRule> batchCreate(Long formId, Flux<FormDynamicRule> dynamicRules);
}