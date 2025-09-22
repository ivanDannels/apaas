package org.apaas.form.engine.service.reactive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.common.utils.SecurityUtils;
import org.apaas.core.context.TenantContext;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormInstance;
import org.apaas.form.engine.repository.FormInstanceRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式表单实例服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormInstanceServiceImpl extends BaseServiceImpl<FormInstance, Long, FormInstanceRepository> implements ReactiveFormInstanceService {

    public ReactiveFormInstanceServiceImpl(FormInstanceRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Long> saveFormInstance(FormInstance formInstance) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    // 设置租户ID
                    formInstance.setTenantId(tenantId);
                    // 设置创建人、更新人
                    String username = SecurityUtils.getUsername();
                    formInstance.setCreator(username);
                    formInstance.setUpdater(username);
                    // 设置创建时间、更新时间
                    LocalDateTime now = LocalDateTime.now();
                    formInstance.setCreatedTime(now);
                    formInstance.setUpdatedTime(now);
                    
                    return save(formInstance).map(FormInstance::getId);
                });
    }

    @Override
    public Mono<Boolean> updateFormInstance(FormInstance formInstance) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    // 设置更新人、更新时间
                    formInstance.setUpdater(SecurityUtils.getUsername());
                    formInstance.setUpdatedTime(LocalDateTime.now());
                    
                    return save(formInstance).map(saved -> true);
                });
    }

    @Override
    public Mono<Boolean> deleteFormInstances(Long[] ids) {
        return TenantContext.getTenantIdAsync()
                .flatMap(tenantId -> {
                    return Flux.fromArray(ids)
                            .flatMap(this::deleteById)
                            .then(Mono.just(true));
                });
    }
}