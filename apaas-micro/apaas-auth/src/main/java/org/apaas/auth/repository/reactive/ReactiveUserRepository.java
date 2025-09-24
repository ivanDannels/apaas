package org.apaas.auth.repository.reactive;

import org.apaas.auth.entity.User;
import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.domain.repository.ReactiveBaseRepository;
import reactor.core.publisher.Mono;

/**
 * 响应式用户仓库接口
 * @author ivan
 */
public interface ReactiveUserRepository extends ReactiveBaseRepository<User, Long> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<User> findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    Mono<User> findByEmail(String email);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    Mono<User> findByPhone(String phone);
}