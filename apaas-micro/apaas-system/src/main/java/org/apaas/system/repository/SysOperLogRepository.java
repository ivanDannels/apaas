package org.apaas.system.repository;

import org.apaas.system.domain.entity.SysOperLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@Repository
public interface SysOperLogRepository extends R2dbcRepository<SysOperLog, Long> {
    
    Flux<SysOperLog> findAllBy(Pageable pageable);
    
    @Query("DELETE FROM sys_oper_log WHERE oper_id IN (:ids)")
    Mono<Integer> deleteByIds(@Param("ids") Long[] ids);
    
    @Query("DELETE FROM sys_oper_log")
    Mono<Integer> cleanOperLog();
}