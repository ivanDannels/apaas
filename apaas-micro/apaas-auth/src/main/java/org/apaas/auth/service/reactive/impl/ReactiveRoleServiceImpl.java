package org.apaas.auth.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.auth.domain.dto.RoleDTO;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.Role;
import org.apaas.auth.entity.UserRole;
import org.apaas.auth.repository.reactive.ReactiveRoleRepository;
import org.apaas.auth.repository.reactive.ReactiveUserRoleRepository;
import org.apaas.auth.service.reactive.ReactiveRoleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应式角色服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveRoleServiceImpl implements ReactiveRoleService {

    private final ReactiveRoleRepository roleRepository;
    private final ReactiveUserRoleRepository userRoleRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<PageResult<Role>> selectPage(RoleDTO query) {
        // 构建查询条件
        Criteria criteria = Criteria.empty();
        
        if (query.getName() != null && !query.getName().isEmpty()) {
            criteria = criteria.and(Criteria.where("name").like("%" + query.getName() + "%"));
        }
        
        if (query.getCode() != null && !query.getCode().isEmpty()) {
            criteria = criteria.and(Criteria.where("code").like("%" + query.getCode() + "%"));
        }
        
        if (query.getStatus() != null) {
            criteria = criteria.and(Criteria.where("status").is(query.getStatus()));
        }
        
        // 创建分页请求
        PageRequest pageRequest = PageRequest.of(query.getPageNum() - 1, query.getPageSize());
        
        // 查询总数
        Mono<Long> countMono = r2dbcEntityTemplate.count(Query.query(criteria), Role.class);
        
        // 查询数据
        Flux<Role> roleFlux = r2dbcEntityTemplate.select(Query.query(criteria).with(pageRequest), Role.class);
        
        // 组合结果
        return countMono.flatMap(total -> 
            roleFlux.collectList().map(list -> {
                PageResult<Role> pageResult = new PageResult<>();
                pageResult.setTotal(total);
                pageResult.setList(list);
                pageResult.setPageNum(query.getPageNum());
                pageResult.setPageSize(query.getPageSize());
                return pageResult;
            })
        );
    }

    @Override
    public Mono<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Mono<Boolean> create(Role role) {
        // 检查角色编码是否已存在
        return roleRepository.findByCode(role.getCode())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(false);
                    }
                    
                    // 设置创建时间
                    role.setCreatedTime(LocalDateTime.now());
                    
                    return roleRepository.save(role)
                            .map(savedRole -> true)
                            .onErrorReturn(false);
                });
    }

    @Override
    public Mono<Boolean> update(Role role) {
        return roleRepository.findById(role.getId())
                .flatMap(existingRole -> {
                    // 检查角色编码是否已被其他角色使用
                    if (!existingRole.getCode().equals(role.getCode())) {
                        return roleRepository.findByCode(role.getCode())
                                .hasElement()
                                .flatMap(exists -> {
                                    if (exists) {
                                        return Mono.just(false);
                                    }
                                    
                                    role.setUpdatedTime(LocalDateTime.now());
                                    return roleRepository.save(role)
                                            .map(updatedRole -> true)
                                            .onErrorReturn(false);
                                });
                    }
                    
                    role.setUpdatedTime(LocalDateTime.now());
                    return roleRepository.save(role)
                            .map(updatedRole -> true)
                            .onErrorReturn(false);
                })
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        // 检查角色是否已分配给用户
        return userRoleRepository.findByRoleId(id)
                .hasElements()
                .flatMap(hasUsers -> {
                    if (hasUsers) {
                        return Mono.just(false);
                    }
                    
                    return roleRepository.deleteById(id)
                            .then(Mono.just(true))
                            .onErrorReturn(false);
                });
    }

    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return roleRepository.findById(id)
                .flatMap(role -> {
                    role.setStatus(status);
                    role.setUpdatedTime(LocalDateTime.now());
                    return roleRepository.save(role);
                })
                .map(updatedRole -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Flux<Role> getAllRoles() {
        return roleRepository.findByStatus(0); // 0-启用
    }

    @Override
    public Flux<Role> getUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()));
    }

    @Override
    public Mono<Boolean> assignRoles(Long userId, Long[] roleIds) {
        // 删除用户现有角色
        return userRoleRepository.deleteByUserId(userId)
                .then(Mono.defer(() -> {
                    if (roleIds == null || roleIds.length == 0) {
                        return Mono.just(true);
                    }
                    
                    // 添加新角色
                    List<Mono<UserRole>> saveMonos = new ArrayList<>();
                    for (Long roleId : roleIds) {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(userId);
                        userRole.setRoleId(roleId);
                        userRole.setCreatedTime(LocalDateTime.now());
                        saveMonos.add(userRoleRepository.save(userRole));
                    }
                    
                    return Flux.concat(saveMonos)
                            .then(Mono.just(true))
                            .onErrorReturn(false);
                }))
                .as(transactionalOperator::transactional); // 使用事务
    }
}