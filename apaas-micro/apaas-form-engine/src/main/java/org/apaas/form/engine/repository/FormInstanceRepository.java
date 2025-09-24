package org.apaas.form.engine.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.form.engine.entity.FormInstance;
import org.springframework.stereotype.Repository;

/**
 * 表单实例响应式仓库接口
 */
@Repository
public interface FormInstanceRepository extends ReactiveBaseRepository<FormInstance, Long> {
}