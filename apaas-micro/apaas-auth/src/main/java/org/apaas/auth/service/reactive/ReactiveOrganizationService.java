package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Organization;
import org.apaas.domain.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveOrganizationService extends BaseService<Organization, Long> {

    /**
     * 获取组织机构树
     *
     * @param parentId 父级ID
     * @return 组织机构树
     */
    Flux<Organization> getOrganizationTree(Long parentId);

    /**
     * 根据用户ID获取组织机构列表
     *
     * @param userId 用户ID
     * @return 组织机构列表
     */
    Flux<Organization> getOrganizationsByUserId(Long userId);

    /**
     * 设置用户的主组织机构
     *
     * @param userId 用户ID
     * @param orgId  组织机构ID
     * @return 是否成功
     */
    Mono<Boolean> setMainOrganization(Long userId, Long orgId);
}