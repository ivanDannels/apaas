package org.apaas.form.engine.service.reactive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.context.TenantContext;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.query.PageResult;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.form.engine.entity.FormData;
import org.apaas.form.engine.repository.FormDataRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormDataService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式表单数据服务实现类
 */
@Slf4j
@Service
public class ReactiveFormDataServiceImpl extends BaseServiceImpl<FormData, Long, FormDataRepository> implements ReactiveFormDataService {

    public ReactiveFormDataServiceImpl(FormDataRepository repository, RedisDomainEventPublisher<EntityChangedEvent<FormData>> eventPublisher) {
        super(repository, eventPublisher);
    }

    /**
     * 分页查询表单数据
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<FormData>> selectFormDataPage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    Flux<FormData> flux = repository.findAllByTenantId(tenantId, pageRequest);

                    return Mono.zip(
                            flux.collectList(),
                            repository.countByTenantId(tenantId).defaultIfEmpty(0L),
                            (list, count) -> new PageResult<>(query.getPageNum(), query.getPageSize(), count, list)
                    );
                });
    }

    /**
     * 根据表单编码查询表单数据
     *
     * @param formCode 表单编码
     * @return 表单数据列表
     */
    @Override
    public Flux<FormData> getFormDataByFormCode(String formCode) {
        return TenantContext.getTenantIdAsync()
                .flatMapMany(tenantId -> repository.findByFormCodeAndTenantId(formCode, tenantId));
    }

    /**
     * 根据表单编码和版本查询表单数据
     *
     * @param formCode 表单编码
     * @param version  表单版本
     * @return 表单数据列表
     */
    @Override
    public Flux<FormData> getFormDataByFormCodeAndVersion(String formCode, String version) {
        return TenantContext.getTenantIdAsync()
                .flatMapMany(tenantId -> repository.findByFormCodeAndVersionAndTenantId(formCode, version, tenantId));
    }

    /**
     * 根据业务键查询表单数据
     *
     * @param businessKey 业务键
     * @return 表单数据
     */
    @Override
    public Mono<FormData> getFormDataByBusinessKey(String businessKey) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> repository.findByBusinessKeyAndTenantId(businessKey, tenantId));
    }

    /**
     * 删除表单数据
     *
     * @param ids 表单数据ID数组
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteFormData(Long[] ids) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    return Flux.fromArray(ids)
                            .flatMap(repository::deleteById)
                            .then(Mono.just(true));
                });
    }

    /**
     * 批量导入表单数据
     *
     * @param formCode 表单编码
     * @param dataJson 数据JSON
     * @return 导入结果
     */
    @Override
    public Mono<Boolean> importFormData(String formCode, String dataJson) {
        // 这里需要实现具体的导入逻辑
        // 暂时返回true，实际开发中需要根据dataJson解析数据并保存
        return Mono.just(true);
    }

    /**
     * 获取表单数据统计
     *
     * @param formCode 表单编码
     * @return 统计结果
     */
    @Override
    public Mono<Object> getFormDataStatistics(String formCode) {
        // 这里需要实现具体的统计逻辑
        // 暂时返回空对象，实际开发中需要根据formCode查询统计数据
        return Mono.just(new Object());
    }

    /**
     * 验证表单数据
     *
     * @param formData 表单数据
     * @return 验证结果
     */
    @Override
    public Mono<Boolean> validateFormData(FormData formData) {
        // 这里需要实现具体的验证逻辑
        // 暂时返回true，实际开发中需要根据formData的字段进行验证
        return Mono.just(true);
    }

    /**
     * 更新表单数据
     *
     * @param formData 表单数据
     * @return 更新结果
     */
    @Override
    public Mono<Boolean> updateFormData(FormData formData) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    // 设置更新人、更新时间
                    formData.setUpdater(SecurityUtils.getUsername());
                    formData.setUpdatedTime(LocalDateTime.now());

                    return save(formData).map(saved -> true);
                });
    }

    /**
     * 提交表单数据
     *
     * @param formData 表单数据
     * @return 提交结果
     */
    @Override
    public Mono<Long> submitFormData(FormData formData) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    // 设置租户ID
                    formData.setTenantId(tenantId);
                    // 设置提交人、提交时间
                    formData.setUpdater(SecurityUtils.getUsername());
                    formData.setUpdatedTime(LocalDateTime.now());
                    // 设置状态为已提交
                    formData.setStatus(1);

                    return save(formData).map(FormData::getId);
                });
    }

    /**
     * 保存表单数据（草稿状态）
     *
     * @param formData 表单数据
     * @return 保存结果
     */
    @Override
    public Mono<Long> saveFormData(FormData formData) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    // 设置租户ID
                    formData.setTenantId(tenantId);
                    // 设置创建人、更新人
                    String username = SecurityUtils.getUsername();
                    formData.setCreator(username);
                    formData.setUpdater(username);
                    // 设置创建时间、更新时间
                    LocalDateTime now = LocalDateTime.now();
                    formData.setCreatedTime(now);
                    formData.setUpdatedTime(now);
                    // 初始状态为草稿
                    formData.setStatus(0);

                    return save(formData).map(FormData::getId);
                });
    }

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 保存后的实体
     */
    @Override
    public Mono<FormData> save(FormData entity) {
        return super.save(entity);
    }
}