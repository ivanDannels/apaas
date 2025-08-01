package org.apaas.authorization.service.impl;

import org.apaas.authorization.domain.dto.ResourceDTO;
import org.apaas.authorization.entity.Resource;
import org.apaas.authorization.repository.ResourceRepository;
import org.apaas.authorization.service.ResourceService;
import org.apaas.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Mono<org.springframework.data.domain.Page<Resource>> selectPage(ResourceDTO query, Pageable pageable) {
        // 构建动态查询条件
        // 由于R2DBC不支持MyBatis Plus的QueryWrapper，需要使用其他方式实现动态查询
        // 这里简化处理，实际项目中可能需要使用R2dbcEntityTemplate或自定义查询
        return resourceRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mono<Resource> create(Resource resource) {
        resource.setStatus(0);
        resource.setDeleted(0);
        resource.setTenantId(SecurityUtils.getTenantId());
        resource.setCreateBy(SecurityUtils.getUsername());
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateBy(SecurityUtils.getUsername());
        resource.setUpdateTime(LocalDateTime.now());
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public Mono<Resource> update(Resource resource) {
        resource.setUpdateBy(SecurityUtils.getUsername());
        resource.setUpdateTime(LocalDateTime.now());
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public Mono<Boolean> delete(Long id) {
        // 删除子资源
        return deleteChildren(id)
                .then(resourceRepository.findById(id))
                .flatMap(resource -> {
                    resource.setDeleted(1);
                    resource.setUpdateBy(SecurityUtils.getUsername());
                    resource.setUpdateTime(LocalDateTime.now());
                    return resourceRepository.save(resource);
                })
                .thenReturn(true);
    }

    private Mono<Void> deleteChildren(Long parentId) {
        // 查询所有子资源并递归删除
        return resourceRepository.findAll()
                .filter(resource -> resource.getParentId() != null && resource.getParentId().equals(parentId))
                .flatMap(child -> delete(child.getId()))
                .then();
    }

    @Override
    @Transactional
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return resourceRepository.findById(id)
                .flatMap(resource -> {
                    resource.setStatus(status);
                    resource.setUpdateBy(SecurityUtils.getUsername());
                    resource.setUpdateTime(LocalDateTime.now());
                    return resourceRepository.save(resource);
                })
                .thenReturn(true);
    }

    @Override
    public Flux<Resource> selectTree() {
        // 查询所有资源
        return resourceRepository.findAll()
                .filter(resource -> resource.getDeleted() == 0 && resource.getTenantId().equals(SecurityUtils.getTenantId()))
                .sort((r1, r2) -> r1.getSort().compareTo(r2.getSort()))
                .collectList()
                .flatMapIterable(resources -> buildTree(resources, 0L));
    }

    @Override
    public Flux<Resource> selectTreeByRoleId(Long roleId) {
        // 查询角色拥有的资源
        return resourceRepository.findByRoleId(roleId)
                .collectList()
                .flatMapIterable(resources -> {
                    // 标记角色拥有的资源
                    for (Resource resource : resources) {
                        resource.setSelected(true);
                    }
                    // 构建树形结构
                    return buildTree(resources, 0L);
                });
    }

    private List<Resource> buildTree(List<Resource> resources, Long parentId) {
        List<Resource> tree = new ArrayList<>();
        for (Resource resource : resources) {
            if (resource.getParentId() != null && resource.getParentId().equals(parentId)) {
                // 递归构建子树
                List<Resource> children = buildTree(resources, resource.getId());
                resource.setChildren(children);
                tree.add(resource);
            }
        }
        return tree;
    }
}