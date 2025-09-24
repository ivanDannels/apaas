package org.apaas.form.engine.service.reactive;

import org.apaas.domain.service.BaseService;
import org.apaas.form.engine.entity.FormInstance;
import reactor.core.publisher.Mono;

/**
 * 响应式表单实例服务接口
 * @author ivan
 */
public interface ReactiveFormInstanceService extends BaseService<FormInstance, Long> {
    /**
     * 保存表单实例
     *
     * @param formInstance 表单实例
     * @return 保存的表单实例ID
     */
    Mono<Long> saveFormInstance(FormInstance formInstance);

    /**
     * 更新表单实例
     *
     * @param formInstance 表单实例
     * @return 更新结果
     */
    Mono<Boolean> updateFormInstance(FormInstance formInstance);

    /**
     * 删除表单实例
     *
     * @param ids 表单实例ID数组
     * @return 删除结果
     */
    Mono<Boolean> deleteFormInstances(Long[] ids);
}