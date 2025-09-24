package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormValidationRule;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 表单验证规则响应式仓库接口
 */
@Repository
public interface FormValidationRuleRepository extends ReactiveBaseRepository<FormValidationRule, Long> {
    /**
     * 根据字段ID查询验证规则列表
     *
     * @param fieldId 字段ID
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldId(Long fieldId);

    /**
     * 根据字段ID和规则类型查询验证规则列表
     *
     * @param fieldId 字段ID
     * @param type 规则类型
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldIdAndType(Long fieldId, Integer type);

    /**
     * 根据字段ID列表查询验证规则列表
     *
     * @param fieldIds 字段ID列表
     * @return 验证规则列表
     */
    Flux<FormValidationRule> findByFieldIdIn(List<Long> fieldIds);

    Flux<FormValidationRule> findByFieldIdAndDeletedFalse(Long fieldId, PageRequest sort);

    Mono<Integer> countByFieldIdAndDeletedFalse(Long fieldId);
}