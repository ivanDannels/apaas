package org.apaas.auth.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.dto.UserDTO;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.User;
import org.apaas.auth.repository.reactive.ReactiveUserRepository;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应式用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveUserServiceImpl implements ReactiveUserService {

    private final ReactiveUserRepository userRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    // 生成token逻辑
                    // 这里简化处理，实际应该使用JWT或其他token生成方式
                    return "token_" + username + "_" + System.currentTimeMillis();
                });
    }

    @Override
    public Mono<Boolean> register(User user) {
        // 检查用户名是否已存在
        return userRepository.findByUsername(user.getUsername())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(false);
                    }
                    
                    // 设置创建时间和密码加密
                    user.setCreatedTime(LocalDateTime.now());
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    
                    return userRepository.save(user)
                            .map(savedUser -> true)
                            .onErrorReturn(false);
                });
    }

    @Override
    public Mono<PageResult<User>> selectPage(UserDTO query) {
        // 构建查询条件
        Criteria criteria = Criteria.empty();
        
        if (query.getUsername() != null && !query.getUsername().isEmpty()) {
            criteria = criteria.and(Criteria.where("username").like("%" + query.getUsername() + "%"));
        }
        
        if (query.getNickname() != null && !query.getNickname().isEmpty()) {
            criteria = criteria.and(Criteria.where("nickname").like("%" + query.getNickname() + "%"));
        }
        
        if (query.getPhone() != null && !query.getPhone().isEmpty()) {
            criteria = criteria.and(Criteria.where("phone").like("%" + query.getPhone() + "%"));
        }
        
        if (query.getStatus() != null) {
            criteria = criteria.and(Criteria.where("status").is(query.getStatus()));
        }
        
        if (query.getDeptId() != null) {
            criteria = criteria.and(Criteria.where("dept_id").is(query.getDeptId()));
        }
        
        // 构建分页查询
        PageRequest pageRequest = PageRequest.of(query.getPageNum() - 1, query.getPageSize());
        Query queryObj = Query.query(criteria).with(pageRequest);
        
        // 执行查询并构建分页结果
        return Mono.zip(
            r2dbcEntityTemplate.count(queryObj, User.class),
            r2dbcEntityTemplate.select(queryObj, User.class).collectList()
        ).map(tuple -> PageResult.build(tuple.getT1(), tuple.getT2()));

    @Override
    public Mono<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Boolean> updateById(User user) {
        user.setUpdatedTime(LocalDateTime.now());
        return userRepository.save(user)
                .map(updatedUser -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> removeById(Long id) {
        return userRepository.deleteById(id)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> removeByIds(Long[] ids) {
        return userRepository.deleteAllById(Arrays.asList(ids))
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<User> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getName())
                .flatMap(username -> userRepository.findByUsername(username))
                .switchIfEmpty(Mono.error(new RuntimeException("未找到当前用户")));
    }

    @Override
    public Mono<Boolean> updatePassword(String oldPassword, String newPassword) {
        return getCurrentUser()
                .filter(user -> passwordEncoder.matches(oldPassword, user.getPassword()))
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setUpdatedTime(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .map(updatedUser -> true)
                .onErrorReturn(false);
    }