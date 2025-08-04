package org.apaas.form.engine.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.form.engine.entity.FormData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单数据服务接口
 */
public interface ReactiveFormDataService extends BaseService<FormData, Long> {
    /**
     * 根据表单编码查询表单数据
     *
     * @param formCode 表单编码
     * @return 表单数据列表
     */
    Flux<FormData> getFormDataByFormCode(String formCode);

    /**
     * 根据表单编码和版本查询表单数据
     *
     * @param formCode 表单编码
     * @param version 表单版本
     * @return 表单数据列表
     */
    Flux<FormData> getFormDataByFormCodeAndVersion(String formCode, String version);

    /**
     * 根据业务键查询表单数据
     *
     * @param businessKey 业务键
     * @return 表单数据
     */
    Mono<FormData> getFormDataByBusinessKey(String businessKey);

    /**
     * 保存表单数据（草稿状态）
     *
     * @param formData 表单数据
     * @return 保存结果
     */
    Mono<Long> saveFormData(FormData formData);

    /**
     * 提交表单数据
     *
     * @param formData 表单数据
     * @return 提交结果
     */
    Mono<Long> submitFormData(FormData formData);

    /**
     * 更新表单数据
     *
     * @param formData 表单数据
     * @return 更新结果
     */
    Mono<Boolean> updateFormData(FormData formData);

    /**
     * 删除表单数据
     *
     * @param ids 表单数据ID数组
     * @return 删除结果
     */
    Mono<Boolean> deleteFormData(Long[] ids);

    /**
     * 批量导入表单数据
     *
     * @param formCode 表单编码
     * @param dataJson 数据JSON
     * @return 导入结果
     */
    Mono<Boolean> importFormData(String formCode, String dataJson);

    /**
     * 获取表单数据统计
     *
     * @param formCode 表单编码
     * @return 统计结果
     */
    Mono<Object> getFormDataStatistics(String formCode);

    /**
     * 验证表单数据
     *
     * @param formData 表单数据
     * @return 验证结果
     */
    Mono<Boolean> validateFormData(FormData formData);
}