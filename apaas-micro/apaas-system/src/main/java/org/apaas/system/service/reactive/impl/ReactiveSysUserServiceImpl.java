package org.apaas.system.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apaas.core.exception.BusinessException;
import org.apaas.system.domain.dto.UserQueryDTO;
import org.apaas.system.entity.SysUser;
import org.apaas.system.repository.SysUserRepository;
import org.apaas.system.service.reactive.ReactiveSysUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ReactiveSysUserServiceImpl implements ReactiveSysUserService {

    private final SysUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<SysUser> getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Mono.error(new BusinessException("用户名不能为空"));
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public Flux<SysUser> getUserPage(Pageable pageable, UserQueryDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        return userRepository.findUsersWithPagination(offset, limit);
    }

    @Override
    public Mono<Boolean> addUser(SysUser user) {
        // 检查用户名是否存在
        return getUserByUsername(user.getUsername())
                .flatMap(existUser -> Mono.error(new BusinessException("用户名已存在")))
                .then(Mono.defer(() -> {
                    // 加密密码
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    // 设置默认状态
                    if (user.getStatus() == null) {
                        user.setStatus(1);
                    }

                    // 保存用户信息
                    return userRepository.save(user)
                            .map(savedUser -> true)
                            .onErrorReturn(false);
                }));
    }

    @Override
    public Mono<Boolean> updateUser(SysUser user) {
        // 检查用户是否存在
        return userRepository.findById(user.getId())
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(existUser -> {
                    // 如果修改了用户名，检查是否重复
                    if (!existUser.getUsername().equals(user.getUsername())) {
                        return getUserByUsername(user.getUsername())
                                .flatMap(sameNameUser -> {
                                    if (!sameNameUser.getId().equals(user.getId())) {
                                        return Mono.error(new BusinessException("用户名已存在"));
                                    }
                                    return Mono.just(existUser);
                                });
                    }
                    return Mono.just(existUser);
                })
                .then(Mono.defer(() -> {
                    // 不更新密码
                    user.setPassword(null);

                    // 更新用户信息
                    return userRepository.save(user)
                            .map(updatedUser -> true)
                            .onErrorReturn(false);
                }));
    }

    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        // 检查用户是否存在
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    // 检查是否为管理员
                    if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                        return Mono.error(new BusinessException("不能删除管理员用户"));
                    }
                    return Mono.just(user);
                })
                .then(Mono.defer(() -> {
                    // 删除用户
                    return userRepository.deleteById(userId)
                            .then(Mono.just(true))
                            .onErrorReturn(false);
                }));
    }

    @Override
    public Mono<Boolean> resetPassword(Long userId, String password) {
        // 检查用户是否存在
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    // 更新密码
                    user.setPassword(passwordEncoder.encode(password));
                    user.setUpdatedTime(LocalDateTime.now());

                    return userRepository.save(user)
                            .map(updatedUser -> true)
                            .onErrorReturn(false);
                });
    }

    @Override
    public Mono<Boolean> changeStatus(Long userId, Integer status) {
        // 检查用户是否存在
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException("用户不存在")))
                .flatMap(user -> {
                    // 检查是否为管理员
                    if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                        return Mono.error(new BusinessException("不能修改管理员用户状态"));
                    }

                    // 更新状态
                    user.setStatus(status);
                    user.setUpdatedTime(LocalDateTime.now());

                    return userRepository.save(user)
                            .map(updatedUser -> true)
                            .onErrorReturn(false);
                });
    }

    @Override
    public Mono<String[]> getUserPermissions(Long userId) {
        // 这里需要调用菜单服务获取权限，暂时返回空数组
        return Mono.just(new String[0]);
    }

    @Override
    public Mono<Boolean> recordLoginInfo(String username, String ip) {
        return getUserByUsername(username)
                .flatMap(user -> {
                    // 更新登录信息
                    user.setLoginIp(ip);
                    user.setLoginDate(LocalDateTime.now());
                    user.setUpdatedTime(LocalDateTime.now());

                    return userRepository.save(user)
                            .map(updatedUser -> true)
                            .onErrorReturn(false);
                });
    }
}