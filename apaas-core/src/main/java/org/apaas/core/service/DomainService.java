package org.apaas.core.service;

import reactor.core.publisher.Mono;

/**
 * 领域服务接口
 * @param <T> 领域对象类型
 */
public interface DomainService<T> {
    
    /**
     * 执行领域逻辑
     * @param domainObject 领域对象
     * @return 处理结果
     */
    Mono<T> execute(T domainObject);
}