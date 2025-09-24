package org.apaas.auth.service.reactive.impl;

import org.apaas.auth.entity.Organization;
import org.apaas.auth.repository.reactive.ReactiveOrganizationRepository;
import org.apaas.auth.service.reactive.ReactiveOrganizationService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveOrganizationServiceImpl extends BaseServiceImpl<Organization, Long, ReactiveOrganizationRepository> implements ReactiveOrganizationService {


    public ReactiveOrganizationServiceImpl(ReactiveOrganizationRepository repository) {
        super(repository);
    }

    @Override
    public Flux<Organization> getOrganizationTree(Long parentId) {
        // 实现获取组织机构树的逻辑
        return repository.findById(parentId).flux();
    }

    @Override
    public Flux<Organization> getOrganizationsByUserId(Long userId) {
        // 实现根据用户ID获取组织机构列表的逻辑
        return repository.findById(userId).flux();
    }

    @Override
    public Mono<Boolean> setMainOrganization(Long userId, Long orgId) {
        // 实现设置用户主组织机构的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如更新用户组织机构关联表中的主组织机构标识
            return true;
        });
    }
}