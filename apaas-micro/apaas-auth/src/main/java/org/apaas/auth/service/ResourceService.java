package org.apaas.authorization.service;

import org.apaas.authorization.domain.dto.ResourceDTO;
import org.apaas.authorization.entity.Resource;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 资源服务接口
 */
public interface ResourceService {
    /**
     * 分页查询资源
     */
    Mono<org.springframework.data.domain.Page<Resource>> selectPage(ResourceDTO query, Pageable pageable);

    /**
     * 创建资源
     */
    Mono<Resource> create(Resource resource);

    /**
     * 更新资源
     */
    Mono<Resource> update(Resource resource);

    /**
     * 删除资源
     */
    Mono<Boolean> delete(Long id);

    /**
     * 更新资源状态
     */
    Mono<Boolean> changeStatus(Long id, Integer status);

    /**
     * 查询资源树形结构
     */
    Flux<Resource> selectTree();

    /**
     * 根据角色ID查询资源树形结构
     */
    Flux<Resource> selectTreeByRoleId(Long roleId);
}