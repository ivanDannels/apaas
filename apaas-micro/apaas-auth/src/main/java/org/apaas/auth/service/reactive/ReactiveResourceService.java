package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Resource;
import org.apaas.domain.service.BaseService;

import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式资源服务接口
 */
public interface ReactiveResourceService extends BaseService<Resource, Long> {

    /**
     * 根据资源名称查询资源
     *
     * @param name 资源名称
     * @return 资源
     */
    Mono<Resource> getResourceByName(String name);

    /**
     * 根据资源类型查询资源列表
     *
     * @param type 资源类型
     * @return 资源列表
     */
    Flux<Resource> getResourcesByType(Integer type);

    /**
     * 根据角色ID查询资源列表
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    Flux<Resource> getResourcesByRoleId(Long roleId);

    /**
     * 根据用户ID查询资源列表
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    Flux<Resource> getResourcesByUserId(Long userId);

    /**
     * 获取资源树
     *
     * @param parentId 父级ID
     * @return 资源树
     */
    Flux<Resource> getResourceTree(Long parentId);
}