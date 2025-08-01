package org.apaas.system.repository;

import org.apaas.system.entity.SysConfig;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * 参数配置Repository接口
 */
@Repository
public interface SysConfigRepository extends R2dbcRepository<SysConfig, Long> {
    /**
     * 根据参数编码查询参数配置
     *
     * @param code 参数编码
     * @return 参数配置信息
     */
    Mono<SysConfig> findByCode(String code);
}