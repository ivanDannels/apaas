package org.apaas.core.event;

import reactor.core.publisher.Mono;

/**
 * 领域事件发布器接口
 * @param <T> 事件类型
 */
public interface DomainEventPublisher<T> {
    
    /**
     * 发布领域事件
     * @param event 领域事件
     * @return 发布结果
     */
    Mono<Void> publish(T event);
}