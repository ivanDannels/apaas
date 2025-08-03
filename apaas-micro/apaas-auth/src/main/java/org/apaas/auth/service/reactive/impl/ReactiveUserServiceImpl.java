package org.apaas.auth.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.auth.entity.User;
import org.apaas.auth.repository.reactive.ReactiveUserRepository;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户服务实现类
 */
@Service
public class ReactiveUserServiceImpl extends BaseServiceImpl<User, Long, ReactiveUserRepository> implements ReactiveUserService {

    private final PasswordEncoder passwordEncoder;

    public ReactiveUserServiceImpl(ReactiveUserRepository userRepository, RedisDomainEventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
        super(userRepository, eventPublisher);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Mono<User> addUser(User user) {
        // 检查用户名是否已存在
        return repository.save(user);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        // 检查是否为管理员
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    if ("admin".equals(user.getUsername())) {
                        return Mono.error(new BusinessException("不能删除管理员用户"));
                    }
                    return super.deleteById(id);
                });
    }

    @Override
    public Mono<Void> resetPassword(Long id, String newPassword) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    return super.save(user);
                })
                .then();
    }

    @Override
    public Mono<Void> changeStatus(Long id, Integer status) {
        return super.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    // 管理员不允许修改状态
                    if ("admin".equals(user.getUsername())) {
                        return Mono.error(new BusinessException("不能修改管理员用户状态"));
                    }
                    user.setStatus(status);
                    return super.save(user);
                })
                .then();
    }

    @Override
    public Flux<String> getUserPermissions(Long userId) {
        // 这里需要根据用户ID查询其权限，具体实现依赖于权限模型设计
        // 暂时返回空的Flux，实际开发中需要实现具体的权限查询逻辑
        return Flux.empty();
    }

    @Override
    public Mono<Void> recordLoginInfo(Long userId, String loginIp) {
        return super.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    user.setLoginIp(loginIp);
                    user.setLoginDate(new java.util.Date());
                    return super.save(user);
                })
                .then();
    }
}