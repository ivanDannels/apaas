package org.apaas.system.repository;

import org.apaas.system.entity.SysUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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
    
    /**
     * 分页查询用户
     *
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户列表
     */
    Flux<SysUser> findUsersWithPagination(int offset, int limit);
}