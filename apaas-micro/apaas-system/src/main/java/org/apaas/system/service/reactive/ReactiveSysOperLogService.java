package org.apaas.system.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.system.entity.SysOperLog;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式操作日志服务接口
 * @author ivan
 */
public interface ReactiveSysOperLogService extends BaseService<SysOperLog, Long> {

    /**
     * 根据操作人ID查询操作日志列表
     *
     * @param operUserId 操作人ID
     * @return 操作日志列表
     */
    Flux<SysOperLog> getSysOperLogsByOperUserId(Long operUserId);

    /**
     * 根据业务类型查询操作日志列表
     *
     * @param businessType 业务类型
     * @return 操作日志列表
     */
    Flux<SysOperLog> getSysOperLogsByBusinessType(String businessType);

    /**
     * 根据模块名称查询操作日志列表
     *
     * @param moduleName 模块名称
     * @return 操作日志列表
     */
    Flux<SysOperLog> getSysOperLogsByModuleName(String moduleName);

    /**
     * 删除操作日志
     *
     * @param operLogId 操作日志ID
     * @return 删除结果
     */
    Mono<Boolean> deleteSysOperLog(Long operLogId);
}