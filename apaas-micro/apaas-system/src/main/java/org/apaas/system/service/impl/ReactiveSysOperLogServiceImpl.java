package org.apaas.system.service.impl;

import org.apaas.system.entity.SysOperLog;
import org.apaas.system.repository.SysOperLogRepository;
import org.apaas.system.service.ReactiveSysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.apaas.core.web.domain.BasePageQuery;
import reactor.core.publisher.Flux;

@Service
public class ReactiveSysOperLogServiceImpl implements ReactiveSysOperLogService {
    
    @Autowired
    private SysOperLogRepository sysOperLogRepository;
    
    @Override
    public Mono<PageResult<SysOperLog>> selectOperLogPage(PageRequest pageRequest) {
        return sysOperLogRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(sysOperLogRepository.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageRequest, tuple.getT2()));
    }
    
    @Override
    public void exportOperLog(ServerWebExchange exchange, BasePageQuery query) {
        // TODO: 实现导出逻辑
    }
    
    @Override
    public Mono<Boolean> deleteOperLogByIds(Long[] operIds) {
        return sysOperLogRepository.deleteByIds(operIds)
                .map(count -> count > 0);
    }
    
    @Override
    public Mono<SysOperLog> getById(Long operId) {
        return sysOperLogRepository.findById(operId);
    }
    
    @Override
    public Mono<Boolean> cleanOperLog() {
        return sysOperLogRepository.cleanOperLog()
                .map(count -> count > 0);
    }
}