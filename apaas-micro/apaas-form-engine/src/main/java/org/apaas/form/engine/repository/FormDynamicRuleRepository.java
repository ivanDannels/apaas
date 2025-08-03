package org.apaas.form.engine.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormDynamicRule;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单动态规则响应式仓库接口
 */
@Repository
public interface FormDynamicRuleRepository extends ReactiveBaseRepository<FormDynamicRule, Long> {
    /**
     * 根据表单ID查询动态规则列表
     *
     * @param formId 表单ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByFormId(Long formId);

    /**
     * 根据表单ID和规则类型查询动态规则列表
     *
     * @param formId 表单ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByFormIdAndType(Long formId, Integer type);

    /**
     * 根据目标字段ID查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByTargetFieldId(Long targetFieldId);

    /**
     * 根据目标字段ID和规则类型查询动态规则列表
     *
     * @param targetFieldId 目标字段ID
     * @param type 规则类型
     * @return 动态规则列表
     */
    Flux<FormDynamicRule> findByTargetFieldIdAndType(Long targetFieldId, Integer type);

    Flux<FormDynamicRule> findByFormIdAndDeletedFalse(Long formId, PageRequest sort);

    Mono<Integer> countByFormIdAndDeletedFalse(Long formId);
}