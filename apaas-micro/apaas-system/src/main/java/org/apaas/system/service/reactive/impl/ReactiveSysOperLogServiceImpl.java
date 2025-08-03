package org.apaas.system.service.reactive.impl;

import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.system.entity.SysOperLog;
import org.apaas.system.repository.SysOperLogRepository;
import org.apaas.system.service.reactive.ReactiveSysOperLogService;
import org.apaas.core.exception.BusinessException;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式操作日志服务实现类
 * @author ivan
 */
@Service
public class ReactiveSysOperLogServiceImpl extends BaseServiceImpl<SysOperLog, Long, SysOperLogRepository> implements ReactiveSysOperLogService {

    public ReactiveSysOperLogServiceImpl(SysOperLogRepository repository, RedisDomainEventPublisher<EntityChangedEvent<SysOperLog>> eventPublisher) {
        super(repository, eventPublisher);
    }

    /**
     * 根据操作人ID查询操作日志列表
     *
     * @param operUserId 操作人ID
     * @return 操作日志列表
     */
    @Override
    public Flux<SysOperLog> getSysOperLogsByOperUserId(Long operUserId) {
        return repository.findByOperUserId(operUserId);
    }

    /**
     * 根据业务类型查询操作日志列表
     *
     * @param businessType 业务类型
     * @return 操作日志列表
     */
    @Override
    public Flux<SysOperLog> getSysOperLogsByBusinessType(String businessType) {
        return repository.findByBusinessType(businessType);
    }

    /**
     * 根据模块名称查询操作日志列表
     *
     * @param moduleName 模块名称
     * @return 操作日志列表
     */
    @Override
    public Flux<SysOperLog> getSysOperLogsByModuleName(String moduleName) {
        return repository.findByModuleName(moduleName);
    }

    /**
     * 删除操作日志
     *
     * @param operLogId 操作日志ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteSysOperLog(Long operLogId) {
        return super.findById(operLogId)
                .switchIfEmpty(Mono.error(new BusinessException("操作日志不存在")))
                .flatMap(operLog -> super.deleteById(operLogId).map(deleted -> true));
    }
}