package org.apaas.system.service.reactive.impl;

import org.apaas.system.entity.SysOperLog;
import org.apaas.system.repository.SysOperLogRepository;
import org.apaas.system.service.reactive.ReactiveSysOperLogService;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式操作日志服务实现类
 * @author ivan
 */
@Service
public class ReactiveSysOperLogServiceImpl extends BaseServiceImpl<SysOperLog, Long, SysOperLogRepository> implements ReactiveSysOperLogService {

    public ReactiveSysOperLogServiceImpl(SysOperLogRepository repository) {
        super(repository);
    }

}