package org.apaas.system.service;

import org.apaas.core.query.PageResult;
import org.apaas.system.entity.SysOperLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * @author ivan
 */
public interface ReactiveSysOperLogService {

    Mono<PageResult<SysOperLog>> selectOperLogPage(PageRequest pageRequest);
    
    void exportOperLog(ServerWebExchange exchange, BasePageQuery query);
    
    Mono<Boolean> deleteOperLogByIds(Long[] operIds);
    
    Mono<SysOperLog> getById(Long operId);
    
    Mono<Boolean> cleanOperLog();
}