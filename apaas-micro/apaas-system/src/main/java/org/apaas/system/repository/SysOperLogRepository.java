package org.apaas.system.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.system.entity.SysOperLog;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
@Repository
public interface SysOperLogRepository extends ReactiveBaseRepository<SysOperLog, Long> {

    @Query("DELETE FROM sys_oper_log WHERE oper_id IN (:ids)")
    Mono<Integer> deleteByIds(@Param("ids") Long[] ids);
    
    @Query("DELETE FROM sys_oper_log")
    Mono<Integer> cleanOperLog();
}