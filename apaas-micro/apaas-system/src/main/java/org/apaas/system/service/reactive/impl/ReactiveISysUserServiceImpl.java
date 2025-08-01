package org.apaas.system.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.entity.SysUser;
import org.apaas.system.repository.SysUserRepository;
import org.apaas.system.service.reactive.ReactiveISysUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveISysUserServiceImpl implements ReactiveISysUserService {

    private final SysUserRepository sysUserRepository;

    @Override
    public Mono<SysUser> getUserByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public Flux<SysUser> getUserPage(Pageable pageable, UserQueryDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有用户
        return sysUserRepository.findAll();
    }

    @Override
    public Mono<Boolean> addUser(SysUser user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return sysUserRepository.save(user)
                .map(savedUser -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> updateUser(SysUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return sysUserRepository.save(user)
                .map(updatedUser -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        return sysUserRepository.deleteById(userId)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> resetPassword(Long userId, String password) {
        return sysUserRepository.findById(userId)
                .flatMap(user -> {
                    user.setPassword(password);
                    user.setUpdateTime(LocalDateTime.now());
                    return sysUserRepository.save(user);
                })
                .map(updatedUser -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> changeStatus(Long userId, Integer status) {
        return sysUserRepository.findById(userId)
                .flatMap(user -> {
                    user.setStatus(status);
                    user.setUpdateTime(LocalDateTime.now());
                    return sysUserRepository.save(user);
                })
                .map(updatedUser -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<String[]> getUserPermissions(Long userId) {
        // 这里需要根据实际需求实现权限查询逻辑
        // 暂时返回空数组
        return Mono.just(new String[0]);
    }

    @Override
    public Mono<Boolean> recordLoginInfo(String username, String ip) {
        return sysUserRepository.findByUsername(username)
                .flatMap(user -> {
                    // 这里需要根据实际需求实现登录信息记录逻辑
                    // 暂时直接返回true
                    return Mono.just(true);
                })
                .onErrorReturn(false);
    }
}