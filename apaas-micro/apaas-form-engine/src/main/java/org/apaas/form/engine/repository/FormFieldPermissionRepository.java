package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormFieldPermission;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 表单字段权限仓库接口
 */
@Repository
public interface FormFieldPermissionRepository extends ReactiveBaseRepository<FormFieldPermission, Long> {
    
    /**
     * 根据表单ID查询字段权限列表
     *
     * @param formId 表单ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFormId(Long formId);
    
    /**
     * 根据字段ID查询字段权限列表
     *
     * @param fieldId 字段ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFieldId(Long fieldId);
    
    /**
     * 根据角色ID查询字段权限列表
     *
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByRoleId(Long roleId);
    
    /**
     * 根据表单ID和角色ID查询字段权限列表
     *
     * @param formId 表单ID
     * @param roleId 角色ID
     * @return 字段权限列表
     */
    Flux<FormFieldPermission> findByFormIdAndRoleId(Long formId, Long roleId);
    
    /**
     * 根据字段ID和角色ID查询字段权限
     *
     * @param fieldId 字段ID
     * @param roleId 角色ID
     * @return 字段权限
     */
    Mono<FormFieldPermission> findByFieldIdAndRoleId(Long fieldId, Long roleId);
}