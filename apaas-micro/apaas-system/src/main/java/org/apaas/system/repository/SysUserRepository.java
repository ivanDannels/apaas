package org.apaas.system.repository;

import org.apaas.system.entity.SysUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * 用户Repository接口
 */
@Repository
public interface SysUserRepository extends R2dbcRepository<SysUser, Long> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<SysUser> findByUsername(String username);
}