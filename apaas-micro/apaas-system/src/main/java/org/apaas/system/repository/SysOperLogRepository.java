package org.apaas.system.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.system.entity.SysOperLog;
import org.springframework.stereotype.Repository;

/**
 * @author ivan
 */
@Repository
public interface SysOperLogRepository extends ReactiveBaseRepository<SysOperLog, Long> {

}