package org.apaas.auth.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.dto.UserDTO;
import org.apaas.auth.domain.PageResult;
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
                    user.setCreateTime(LocalDateTime.now());
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